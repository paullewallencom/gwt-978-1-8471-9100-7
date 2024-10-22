/*
 * @(#)TypeVariable.java	1.6 07/01/18
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

package javax.lang.model.type;


import javax.lang.model.element.Element;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.util.Types;


/**
 * Represents a type variable.
 * A type variable may be explicitly declared by a
 * {@linkplain TypeParameterElement type parameter} of a
 * type, method, or constructor.
 * A type variable may also be declared implicitly, as by
 * the capture conversion of a wildcard type argument
 * (see chapter 5 of <i>The Java Language Specification, Third
 * Edition</i>).
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version 1.6 07/01/18
 * @see TypeParameterElement
 * @since 1.6
 */
public interface TypeVariable extends ReferenceType {

    /**
     * Returns the element corresponding to this type variable.
     *
     * @return the element corresponding to this type variable
     */
    Element asElement();

    /**
     * Returns the upper bound of this type variable.
     *
     * <p> If this type variable was declared with no explicit
     * upper bounds, the result is {@code java.lang.Object}.
     * If it was declared with multiple upper bounds,
     * the result is an intersection type (modeled as a
     * {@link DeclaredType}).
     * Individual bounds can be found by examining the result's
     * {@linkplain Types#directSupertypes(TypeMirror) supertypes}.
     *
     * @return the upper bound of this type variable
     */
    TypeMirror getUpperBound();

    /**
     * Returns the lower bound of this type variable.  While a type
     * parameter cannot include an explicit lower bound declaration,
     * capture conversion can produce a type variable with a
     * non-trivial lower bound.  Type variables otherwise have a
     * lower bound of {@link NullType}.
     *
     * @return the lower bound of this type variable
     */
    TypeMirror getLowerBound();
}
