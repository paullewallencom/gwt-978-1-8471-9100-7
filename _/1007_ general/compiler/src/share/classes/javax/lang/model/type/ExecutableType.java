/*
 * @(#)ExecutableType.java	1.6 07/01/18
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


import java.util.List;

import javax.lang.model.element.ExecutableElement;


/**
 * Represents the type of an executable.  An <i>executable</i>
 * is a method, constructor, or initializer.
 *
 * <p> The executable is
 * represented as when viewed as a method (or constructor or
 * initializer) of some reference type.
 * If that reference type is parameterized, then its actual
 * type arguments are substituted into any types returned by the methods of
 * this interface.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version 1.6 07/01/18
 * @see ExecutableElement
 * @since 1.6
 */
public interface ExecutableType extends TypeMirror {

    /**
     * Returns the type variables declared by the formal type parameters
     * of this executable.
     *
     * @return the type variables declared by the formal type parameters,
     *		or an empty list if there are none
     */
    List<? extends TypeVariable> getTypeVariables();

    /**
     * Returns the return type of this executable.
     * Returns a {@link NoType} with kind {@link TypeKind#VOID VOID}
     * if this executable is not a method, or is a method that does not
     * return a value.
     *
     * @return the return type of this executable
     */
    TypeMirror getReturnType();

    /**
     * Returns the types of this executable's formal parameters.
     *
     * @return the types of this executable's formal parameters,
     *		or an empty list if there are none
     */
    List<? extends TypeMirror> getParameterTypes();

    /**
     * Returns the exceptions and other throwables listed in this
     * executable's {@code throws} clause.
     *
     * @return the exceptions and other throwables listed in this
     *		executable's {@code throws} clause,
     *		or an empty list if there are none.
     */
    List<? extends TypeMirror> getThrownTypes();
}
