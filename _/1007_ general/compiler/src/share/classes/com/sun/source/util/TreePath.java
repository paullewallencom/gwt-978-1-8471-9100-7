/*
 * @(#)TreePath.java	1.5 07/01/18
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
import java.util.Iterator;

/**
 * A path of tree nodes, typically used to represent the sequence of ancestor
 * nodes of a tree node up to the top level CompilationUnitTree node.
 *
 * @author Jonathan Gibbons
 * @since 1.6
 */
public class TreePath implements Iterable<Tree> {
    /**
     * Gets a tree path for a tree node within a compilation unit.
     * @return null if the node is not found
     */
    public static TreePath getPath(CompilationUnitTree unit, Tree target) {
	return getPath(new TreePath(unit), target);
    }
    
    /**
     * Gets a tree path for a tree node within a subtree identified by a TreePath object.
     * @return null if the node is not found
     */
    public static TreePath getPath(TreePath path, Tree target) {
	path.getClass();
	target.getClass();
	
	class Result extends Error {
	    static final long serialVersionUID = -5942088234594905625L;
	    TreePath path;
	    Result(TreePath path) {
		this.path = path;
	    }
	}
	class PathFinder extends TreePathScanner<TreePath,Tree> {
	    public TreePath scan(Tree tree, Tree target) {
		if (tree == target)
		    throw new Result(new TreePath(getCurrentPath(), target));
		return super.scan(tree, target);
	    }
	}
	
	try {
	    new PathFinder().scan(path, target);
	} catch (Result result) {
	    return result.path;
	}
        return null;
    }
    
    /**
     * Creates a TreePath for a root node.
     */
    public TreePath(CompilationUnitTree t) {
        this(null, t);
    }
    
    /**
     * Creates a TreePath for a child node.
     */
    public TreePath(TreePath p, Tree t) {
        if (t.getKind() == Tree.Kind.COMPILATION_UNIT) {
            compilationUnit = (CompilationUnitTree) t;
            parent = null; 
        }
        else {
            compilationUnit = p.compilationUnit;
            parent = p;
        }
        leaf = t;
    }
    /**
     * Get the compilation unit associated with this path.
     */
    public CompilationUnitTree getCompilationUnit() {
	return compilationUnit;
    }
    
    /**
     * Get the leaf node for this path.
     */
    public Tree getLeaf() {
	return leaf;
    }
    
    /**
     * Get the path for the enclosing node, or null if there is no enclosing node.
     */
    public TreePath getParentPath() {
	return parent;
    }

    public Iterator<Tree> iterator() {
	return new Iterator<Tree>() {
	    public boolean hasNext() {
		return curr.parent != null;
	    }
	    
	    public Tree next() {
		curr = curr.parent;
		return curr.leaf;
	    }
	    
	    public void remove() {
		throw new UnsupportedOperationException();
	    }
	    
	    private TreePath curr;
	};
    }
    
    private CompilationUnitTree compilationUnit;
    private Tree leaf;
    private TreePath parent;
}
