/*
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *   
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
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
 *  
 */

/*
 * @test @(#)SuperfluousAbstract.java	1.4 07/01/18
 * @bug 4912795
 * @summary AbstractMethodError throws if not redeclare abstract iterator() method
 * @author gafter
 *
 * @compile -source 1.5 SuperfluousAbstract.java
 * @run main SuperfluousAbstract
 */

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

interface Q<E> extends Collection<E> {}

abstract class AbstractQ<E> extends AbstractCollection<E> implements Q<E> {
    // Uncomment this to avoid the AbstractMethodError:
    //public abstract Iterator<E> iterator();
}

class ConcreteQ<E> extends AbstractQ<E> {
    public int size() { return 0; }
    public Iterator<E> iterator() { return null; }
}

public class SuperfluousAbstract {
    public static void main(String[] args) {
	try {
	    Q<Integer> q = new ConcreteQ<Integer>() ;
	    for (Integer i : q) {}
	}
	catch (AbstractMethodError e) {
	    e.printStackTrace(System.err);
	}
	catch (NullPointerException e) {
	    // expected, since iterator() returns null
	}
    }
}
