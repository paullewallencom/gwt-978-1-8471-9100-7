/*
 * @(#)IfTree.java	1.4 07/01/18
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

/**
 * A tree node for an 'if' statement.
 *
 * For example:
 * <pre>
 *   if ( <em>condition</em> ) 
 *      <em>thenStatement</em>
 * 
 *   if ( <em>condition</em> ) 
 *       <em>thenStatement</em> 
 *   else
 *       <em>elseStatement</em> 
 * </pre>
 *
 * @see "The Java Language Specification, 3rd ed, section 14.9"
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface IfTree extends StatementTree {
    ExpressionTree getCondition();
    StatementTree getThenStatement();
    /**
     * @return null if this if statement has no else branch.
     */
    StatementTree getElseStatement();
}
