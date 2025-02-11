/*
 * @(#)AnnotationProxyMaker.java	1.4 07/01/18
 * 
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *  
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *  
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *  
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package com.sun.tools.javac.model;

import com.sun.tools.javac.util.*;
import java.lang.annotation.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import sun.reflect.annotation.*;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import com.sun.tools.javac.code.*;
import com.sun.tools.javac.code.Symbol.*;
import com.sun.tools.javac.code.Type.ArrayType;


/**
 * A generator of dynamic proxy implementations of
 * java.lang.annotation.Annotation.
 *
 * <p> The "dynamic proxy return form" of an annotation element value is
 * the form used by sun.reflect.annotation.AnnotationInvocationHandler.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 * you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */

public class AnnotationProxyMaker {

    private final Attribute.Compound anno;
    private final Class<? extends Annotation> annoType;


    private AnnotationProxyMaker(Attribute.Compound anno,
				 Class<? extends Annotation> annoType) {
	this.anno = anno;
	this.annoType = annoType;
    }


    /**
     * Returns a dynamic proxy for an annotation mirror.
     */
    public static <A extends Annotation> A generateAnnotation(
	    Attribute.Compound anno, Class<A> annoType) {
        AnnotationProxyMaker apm = new AnnotationProxyMaker(anno, annoType);
	return annoType.cast(apm.generateAnnotation());
    }


    /**
     * Returns a dynamic proxy for an annotation mirror.
     */
    private Annotation generateAnnotation() {
	return AnnotationParser.annotationForMap(annoType,
						 getAllReflectedValues());
    }

    /**
     * Returns a map from element names to their values in "dynamic
     * proxy return form".  Includes all elements, whether explicit or
     * defaulted.
     */
    private Map<String, Object> getAllReflectedValues() {
	Map<String, Object> res = new LinkedHashMap<String, Object>();

	for (Map.Entry<MethodSymbol, Attribute> entry :
						  getAllValues().entrySet()) {
	    MethodSymbol meth = entry.getKey();
	    Object value = generateValue(meth, entry.getValue());
	    if (value != null) {
		res.put(meth.name.toString(), value);
	    } else {
		// Ignore this element.  May (properly) lead to
		// IncompleteAnnotationException somewhere down the line.
	    }
	}
	return res;
    }

    /**
     * Returns a map from element symbols to their values.
     * Includes all elements, whether explicit or defaulted.
     */
    private Map<MethodSymbol, Attribute> getAllValues() {
	Map<MethodSymbol, Attribute> res =
	    new LinkedHashMap<MethodSymbol, Attribute>();

	// First find the default values.
	ClassSymbol sym = (ClassSymbol) anno.type.tsym;
	for (Scope.Entry e = sym.members().elems; e != null; e = e.sibling) {
	    if (e.sym.kind == Kinds.MTH) {
		MethodSymbol m = (MethodSymbol) e.sym;
		Attribute def = m.getDefaultValue();
		if (def != null)
		    res.put(m, def);
	    }
	}
	// Next find the explicit values, possibly overriding defaults.
	for (Pair<MethodSymbol, Attribute> p : anno.values)
	    res.put(p.fst, p.snd);
	return res;
    }

    /**
     * Converts an element value to its "dynamic proxy return form".
     * Returns an exception proxy on some errors, but may return null if
     * a useful exception cannot or should not be generated at this point.
     */
    private Object generateValue(MethodSymbol meth, Attribute attr) {
	ValueVisitor vv = new ValueVisitor(meth);
	return vv.getValue(attr);
    }


    private class ValueVisitor implements Attribute.Visitor {

	private MethodSymbol meth;	// annotation element being visited
	private Class<?> returnClass;	// return type of annotation element
	private Object value;		// value in "dynamic proxy return form"

	ValueVisitor(MethodSymbol meth) {
	    this.meth = meth;
	}

	Object getValue(Attribute attr) {
	    Method method;		// runtime method of annotation element
	    try {
		method = annoType.getMethod(meth.name.toString());
	    } catch (NoSuchMethodException e) {
		return null;
	    }
	    returnClass = method.getReturnType();
	    attr.accept(this);
	    if (!(value instanceof ExceptionProxy) &&
		!AnnotationType.invocationHandlerReturnType(returnClass)
							.isInstance(value)) {
		typeMismatch(method, attr);
	    }
	    return value;
	}

	
	public void visitConstant(Attribute.Constant c) {
	    value = c.getValue();
	}

	public void visitClass(Attribute.Class c) {
	    value = new MirroredTypeExceptionProxy(c.type);
	}

	public void visitArray(Attribute.Array a) {
	    Name elemName = ((ArrayType) a.type).elemtype.tsym.name;

	    if (elemName == elemName.table.java_lang_Class) {	// Class[]
		// Construct a proxy for a MirroredTypesException
		List<TypeMirror> elems = List.nil();
		for (Attribute value : a.values) {
		    Type elem = ((Attribute.Class) value).type;
		    elems.add(elem);
		}
		value = new MirroredTypesExceptionProxy(elems);

	    } else {
		int len = a.values.length;
		Class<?> returnClassSaved = returnClass;
		returnClass = returnClass.getComponentType();
		try {
		    Object res = Array.newInstance(returnClass, len);
		    for (int i = 0; i < len; i++) {
			a.values[i].accept(this);
			if (value == null || value instanceof ExceptionProxy) {
			    return;
			}
			try {
			    Array.set(res, i, value);
			} catch (IllegalArgumentException e) {
			    value = null;	// indicates a type mismatch
			    return;
			}
		    }
		    value = res;
		} finally {
		    returnClass = returnClassSaved;
		}
	    }
	}

        @SuppressWarnings("unchecked")
	public void visitEnum(Attribute.Enum e) {
            if (returnClass.isEnum()) {
		String constName = e.value.toString();
		try {
		    value = Enum.valueOf((Class) returnClass, constName);
		} catch (IllegalArgumentException ex) {
		    value = new EnumConstantNotPresentExceptionProxy(
					(Class) returnClass, constName);
		}
	    } else {
		value = null;	// indicates a type mismatch
	    }
	}

	public void visitCompound(Attribute.Compound c) {
	    try {
		Class<? extends Annotation> nested =
		    returnClass.asSubclass(Annotation.class);
		value = generateAnnotation(c, nested);
	    } catch (ClassCastException ex) {
		value = null;	// indicates a type mismatch
	    }
	}

	public void visitError(Attribute.Error e) {
	    value = null;	// indicates a type mismatch
	}


	/**
	 * Sets "value" to an ExceptionProxy indicating a type mismatch.
	 */
	private void typeMismatch(final Method method, final Attribute attr) {
	    value = new ExceptionProxy() {
                static final long serialVersionUID = 269;
		public String toString() {
		    return "<error>";	// eg:  @Anno(value=<error>)
		}
		protected RuntimeException generateException() {
		    return new AnnotationTypeMismatchException(method,
				attr.type.toString());
		}
	    };
	}
    }


    /**
     * ExceptionProxy for MirroredTypeException.
     * The toString, hashCode, and equals methods foward to the underlying
     * type.
     */
    private static class MirroredTypeExceptionProxy extends ExceptionProxy {
        static final long serialVersionUID = 269;

	private transient final TypeMirror type;
	private final String typeString;

	MirroredTypeExceptionProxy(TypeMirror t) {
	    type = t;
	    typeString = t.toString();
	}

	public String toString() {
	    return typeString;
	}

	public int hashCode() {
	    return (type != null ? type : typeString).hashCode();
	}

	public boolean equals(Object obj) {
	    return type != null &&
		   obj instanceof MirroredTypeExceptionProxy &&
		   type.equals(((MirroredTypeExceptionProxy) obj).type);
	}

	protected RuntimeException generateException() {
	    return new MirroredTypeException(type);
	}
    }


    /**
     * ExceptionProxy for MirroredTypesException.
     * The toString, hashCode, and equals methods foward to the underlying
     * types.
     */
    private static class MirroredTypesExceptionProxy extends ExceptionProxy {
        static final long serialVersionUID = 269;

	private transient final List<TypeMirror> types;
	private final String typeStrings;

	MirroredTypesExceptionProxy(List<TypeMirror> ts) {
	    types = ts;
	    typeStrings = ts.toString();
	}

	public String toString() {
	    return typeStrings;
	}

	public int hashCode() {
	    return (types != null ? types : typeStrings).hashCode();
	}

	public boolean equals(Object obj) {
	    return types != null &&
		   obj instanceof MirroredTypesExceptionProxy &&
		   types.equals(
		      ((MirroredTypesExceptionProxy) obj).types);
	}

	protected RuntimeException generateException() {
	    return new MirroredTypesException(types);
	}
    }
}
