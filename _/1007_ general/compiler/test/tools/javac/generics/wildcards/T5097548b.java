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
 * @test @(#)T5097548b.java	1.5 07/01/18
 * @bug 5097548
 * @summary Stack overflow in capture conversion
 * @author Peter von der Ah\u00e9
 * @compile -source 5 T5097548b.java
 */

interface Edge<N extends Node<? extends Edge<N>>> {
    void setEndNode(N n);
}
interface Node<E extends Edge<? extends Node<E>>> {
    E getOutEdge();
}

public class T5097548b {
    public static void main(String[] args) {
	Node<?> node = null;
	node.getOutEdge().setEndNode(null);
    }
}
