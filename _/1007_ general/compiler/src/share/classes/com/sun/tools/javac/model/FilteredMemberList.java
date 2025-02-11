/*
 * @(#)FilteredMemberList.java	1.3 07/01/18
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
import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import com.sun.tools.javac.code.Scope;
import com.sun.tools.javac.code.Symbol;

import static com.sun.tools.javac.code.Flags.*;

/**
 * Utility to construct a view of a symbol's members,
 * filtering out unwanted elements such as synthetic ones.
 * This view is most efficiently accessed through its iterator() method.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 * you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */
@Version("@(#)FilteredMemberList.java	1.3 07/01/18")
public class FilteredMemberList extends AbstractList<Symbol> {

    private final Scope scope;

    public FilteredMemberList(Scope scope) {
	this.scope = scope;
    }

    public int size() {
	int cnt = 0;
	for (Scope.Entry e = scope.elems; e != null; e = e.sibling) {
	    if (!unwanted(e.sym))
		cnt++;
	}
	return cnt;
    }

    public Symbol get(int index) {
	for (Scope.Entry e = scope.elems; e != null; e = e.sibling) {
	    if (!unwanted(e.sym) && (index-- == 0))
		return e.sym;
	}
	throw new IndexOutOfBoundsException();
    }

    // A more efficient implementation than AbstractList's.
    public Iterator<Symbol> iterator() {
	return new Iterator<Symbol>() {

	    /** The next entry to examine, or null if none. */
	    private Scope.Entry nextEntry = scope.elems;

	    private boolean hasNextForSure = false;

	    public boolean hasNext() {
		if (hasNextForSure) {
		    return true;
		}
		while (nextEntry != null && unwanted(nextEntry.sym)) {
		    nextEntry = nextEntry.sibling;
		}
		hasNextForSure = (nextEntry != null);
		return hasNextForSure;
	    }

	    public Symbol next() {
		if (hasNext()) {
		    Symbol result = nextEntry.sym;
		    nextEntry = nextEntry.sibling;
		    hasNextForSure = false;
		    return result;
		} else {
		    throw new NoSuchElementException();
		}
	    }

	    public void remove() {
		throw new UnsupportedOperationException();
	    }
	};
    }

    /**
     * Tests whether this is a symbol that should never be seen by
     * clients, such as a synthetic class.  Returns true for null.
     */
    private static boolean unwanted(Symbol s) {
	return s == null  ||  (s.flags() & SYNTHETIC) != 0;
    }
}
