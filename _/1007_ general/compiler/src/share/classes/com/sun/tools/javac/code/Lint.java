/*
 * @(#)Lint.java	1.11 07/01/18
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

package com.sun.tools.javac.code;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import com.sun.tools.javac.code.Symbol.*;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Options;
import com.sun.tools.javac.util.Pair;
import com.sun.tools.javac.util.Version;
import static com.sun.tools.javac.code.Flags.*;


/**
 * A class for handling -Xlint suboptions and @SuppresssWarnings.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
@Version("@(#)Lint.java	1.11 07/01/18")
public class Lint
{
    /** The context key for the root Lint object. */
    protected static final Context.Key<Lint> lintKey = new Context.Key<Lint>();

    /** Get the root Lint instance. */
    public static Lint instance(Context context) {
	Lint instance = context.get(lintKey);
	if (instance == null)
	    instance = new Lint(context);
	return instance;
    }

    /**
     * Returns the result of combining the values in this object with 
     * the given annotation.
     */
    public Lint augment(Attribute.Compound attr) {
	return augmentor.augment(this, attr);
    }


    /**
     * Returns the result of combining the values in this object with 
     * the given annotations.
     */
    public Lint augment(List<Attribute.Compound> attrs) {
	return augmentor.augment(this, attrs);
    }

    /**
     * Returns the result of combining the values in this object with 
     * the given annotations and flags.
     */
    public Lint augment(List<Attribute.Compound> attrs, long flags) {
	Lint l = augmentor.augment(this, attrs);
	if ((flags & DEPRECATED) != 0) {
	    if (l == this)
		l = new Lint(this);
	    l.values.remove(LintCategory.DEPRECATION);
	    l.suppressedValues.add(LintCategory.DEPRECATION);
	}
	return l;
    }


    private final AugmentVisitor augmentor;

    private final EnumSet<LintCategory> values;
    private final EnumSet<LintCategory> suppressedValues;

    private static Map<String, LintCategory> map = new HashMap<String,LintCategory>();


    protected Lint(Context context) {
	// initialize values according to the lint options
	Options options = Options.instance(context);
	values = EnumSet.noneOf(LintCategory.class);
	for (Map.Entry<String, LintCategory> e: map.entrySet()) {
	    if (options.lint(e.getKey()))
		values.add(e.getValue());
	}

	suppressedValues = EnumSet.noneOf(LintCategory.class);

	context.put(lintKey, this);
	augmentor = new AugmentVisitor(context);
    }

    protected Lint(Lint other) {
	this.augmentor = other.augmentor;
	this.values = other.values.clone();
	this.suppressedValues = other.suppressedValues.clone();
    }

    public String toString() {
	return "Lint:[values" + values + " suppressedValues" + suppressedValues + "]";
    }

    /**
     * Categories of warnings that can be generated by the compiler.
     */
    public enum LintCategory {  
        /**
	 * Warn about use of unnecessary casts.
	 */
	CAST("cast"),

        /**
	 * Warn about use of deprecated items.
	 */
	DEPRECATION("deprecation"),

	/**
	 * Warn about items which are documented with an {@code @deprecated} JavaDoc
	 * comment, but which do not have {@code @Deprecated} annotation.
	 */
	DEP_ANN("dep-ann"),

	/**
	 * Warn about division by constant integer 0.
	 */
	DIVZERO("divzero"),

	/**
	 * Warn about empty statement after if.
	 */
	EMPTY("empty"),

	/**
	 * Warn about falling through from one case of a switch statement to the next.
	 */
	FALLTHROUGH("fallthrough"), 

	/**
	 * Warn about finally clauses that do not terminate normally.
	 */
	FINALLY("finally"),

	/**
	 * Warn about issues regarding method overrides.
	 */
	OVERRIDES("overrides"),

	/**
	 * Warn about invalid path elements on the command line.
	 * Such warnings cannot be suppressed with the SuppressWarnings
	 * annotation.
	 */
	PATH("path"), 

	/**
	 * Warn about Serializable classes that do not provide a serial version ID.
	 */
	SERIAL("serial"),

	/**
	 * Warn about unchecked operations on raw types.
	 */
	UNCHECKED("unchecked"); 

        LintCategory(String option) {
	    this.option = option;
	    map.put(option, this);
        }

	static LintCategory get(String option) {
	    return map.get(option);
        }

	private final String option;
    };

    /**
     * Checks if a warning category is enabled. A warning category may be enabled
     * on the command line, or by default, and can be temporarily disabled with
     * the SuppressWarnings annotation.
     */
    public boolean isEnabled(LintCategory lc) {
	return values.contains(lc);
    }

    /**
     * Checks is a warning category has been specifically suppressed, by means
     * of the SuppressWarnings annotation, or, in the case of the deprecated
     * category, whether it has been implicitly suppressed by virtue of the 
     * current entity being itself deprecated.
     */
    public boolean isSuppressed(LintCategory lc) {
	return suppressedValues.contains(lc);
    }

    protected static class AugmentVisitor implements Attribute.Visitor {
	private final Context context;
	private Symtab syms;
	private Lint parent;
	private Lint lint;

	AugmentVisitor(Context context) {
	    // to break an ugly sequence of initialization dependencies,  
	    // we defer the initialization of syms until it is needed
	    this.context = context;
	}
	
	Lint augment(Lint parent, Attribute.Compound attr) {
	    initSyms();
	    this.parent = parent;
	    lint = null;
	    attr.accept(this);
	    return (lint == null ? parent : lint);
	}
	
	Lint augment(Lint parent, List<Attribute.Compound> attrs) {
	    initSyms();
	    this.parent = parent;
	    lint = null;
	    for (Attribute.Compound a: attrs) {
		a.accept(this);
	    }
	    return (lint == null ? parent : lint);
	}

	private void initSyms() {
	    if (syms == null)
		syms = Symtab.instance(context);
	}
	
	private void suppress(LintCategory lc) {
	    if (lint == null) 
		lint = new Lint(parent);
	    lint.suppressedValues.add(lc);
	    lint.values.remove(lc);
	}
	
	public void visitConstant(Attribute.Constant value) {
	    if (value.type.tsym == syms.stringType.tsym) {
		LintCategory lc = LintCategory.get((String) (value.value));
		if (lc != null) 
		    suppress(lc);
	    }
	}
	
	public void visitClass(Attribute.Class clazz) { 
	}
	
	// If we find a @SuppressWarnings annotation, then we continue
	// walking the tree, in order to suppress the individual warnings
	// specified in the @SuppressWarnings annotation.
	public void visitCompound(Attribute.Compound compound) {
	    if (compound.type.tsym == syms.suppressWarningsType.tsym) {
		for (List<Pair<MethodSymbol,Attribute>> v = compound.values;
		     v.nonEmpty(); v = v.tail) {
		    Pair<MethodSymbol,Attribute> value = v.head;
		    if (value.fst.name.toString().equals("value")) 
			value.snd.accept(this);
		}
		
	    }
	}
	
	public void visitArray(Attribute.Array array) {
	    for (Attribute value : array.values) 
		value.accept(this);
	}
	
	public void visitEnum(Attribute.Enum e) {
	}
	
	public void visitError(Attribute.Error e) {
	}
    };
}
