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
 * @test @(#)InnerInterface1.java	1.5 07/01/18
 * @bug 4452899
 * @summary Inner interfaces are not treated as static
 * @author gafter
 *
 * @compile -source 1.5 InnerInterface1.java
 */

package test.tools.javac.generics.InnerInterface1;

interface Iterator<E> {
}

class Set<E> {
    Iterator<E> iterator() { return null; }
}

interface Map<K,V> {
    public interface Entry<K,V> {
    }
    public Set<Entry<K,V>> entrySet();
}

class Hashtable<K,V> implements Map<K,V> {
    public Set<Entry<K,V>> entrySet() { return null; }

    public void foo() {
        Iterator<Map.Entry<K,V>> it = entrySet().iterator();
    }
}
