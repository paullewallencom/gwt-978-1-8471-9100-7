/*
 * @(#)Scope.java	1.3 07/01/18
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

package com.sun.source.tree;

import com.sun.source.tree.Tree;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

/**
 * Interface for determining locally available program elements, such as
 * local variables and imports. 
 * Upon creation, a Scope is associated with a given program position; 
 * for example, a {@linkplain Tree tree node}. This position may be used to
 * infer an enclosing method and/or class.
 * 
 * <p>A Scope does not itself contain the details of the elements corresponding 
 * to the parameters, methods and fields of the methods and classes containing
 * its position. However, these elements can be determined from the enclosing
 * elements.
 *
 * <p>Scopes may be contained in an enclosing scope. The outermost scope contains
 * those elements available via "star import" declarations; the scope within that
 * contains the top level elements of the compilation unit, including any named
 * imports.
 *
 * @since 1.6
 */
public interface Scope {
    /**
     * Returns the enclosing scope.
     */
    public Scope getEnclosingScope();
    
    /**
     * Returns the innermost type element containing the position of this scope
     */
    public TypeElement getEnclosingClass();
    
    /**
     * Returns the innermost executable element containing the position of this scope.
     */
    public ExecutableElement getEnclosingMethod();
    
    /**
     * Returns the elements directly contained in this scope.
     */
    public Iterable<? extends Element> getLocalElements();
}
