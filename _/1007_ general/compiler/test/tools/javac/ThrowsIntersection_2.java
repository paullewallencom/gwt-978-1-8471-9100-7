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
 * @test @(#)ThrowsIntersection_2.java	1.4 07/01/18
 * @bug 4042259
 * @summary Check that a class can inherit multiple methods with conflicting throws clauses.
 * @author maddox
 *
 * @compile ThrowsIntersection_2.java
 */

class Ex1 extends Exception {}
class Ex2 extends Exception {}
class Ex3 extends Exception {}

interface a {
  int m1() throws Ex1, Ex3;
}

interface b {
  int m1() throws Ex3, Ex2;
}

// Either order should work
abstract class c1 implements a, b {}
abstract class c2 implements b, a {}

class d extends c1  {
  public int m1() throws Ex3 {
    return 1;
  }
}

class e extends c2 {
  public int m1() throws Ex3 {
    return 1;
  }
}
