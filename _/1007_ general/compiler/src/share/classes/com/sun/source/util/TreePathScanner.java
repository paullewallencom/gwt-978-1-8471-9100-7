/*
 * @(#)TreePathScanner.java	1.4 07/01/18
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

package com.sun.source.util;

import com.sun.source.tree.*;

/** 
 * A TreeVisitor that visits all the child tree nodes, and provides
 * support for maintaining a path for the parent nodes.
 * To visit nodes of a particular type, just override the 
 * corresponding visitorXYZ method.
 * Inside your method, call super.visitXYZ to visit descendant
 * nodes.
 * 
 * @author Jonathan Gibbons
 * @since 1.6
 */
public class TreePathScanner<R, P> extends TreeScanner<R, P> {
    
    /**
     * Scan a tree from a position identified by a TreePath.
     */
    public R scan(TreePath path, P p) {
	this.path = path;
	try {
	    return path.getLeaf().accept(this, p);
	} finally {
	    this.path = null;
	}
    }
    
    /**
     * Scan a single node. 
     * The current path is updated for the duration of the scan.
     */	
    @Override
    public R scan(Tree tree, P p) {
	if (tree == null)
	    return null;
	
        TreePath prev = path;
        path = new TreePath(path, tree);      
	try {
	    return tree.accept(this, p);
	} finally {
	    path = prev;
	}
    }
    
    /**
     * Get the current path for the node, as built up by the currently
     * active set of scan calls.
     */
    public TreePath getCurrentPath() {
	return path;
    }
    
    private TreePath path;
}
