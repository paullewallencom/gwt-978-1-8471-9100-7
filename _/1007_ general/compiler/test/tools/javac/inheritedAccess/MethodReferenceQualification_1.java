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
 * @test @(#)MethodReferenceQualification_1.java	1.4 07/01/18
 *
 * @bug 4283246
 * @summary Verify proper bytecode-level qualification of method accesses.
 * @author maddox (cribbed from gbracha)
 *
 * @run clean P1.*
 * @run compile MethodReferenceQualification_1.java
 * @run main/othervm -Xverify:all MethodReferenceQualification_1
 */

/*
 * All method references should be qualified at the bytecode level with
 * the class of the object through which the access is made, which is
 * public.  The method 'foo' is declared in a package-private class, but
 * inherited by the public class 'P1.pub'.  This program will fail to
 * verify if the method is qualified by the declaring class.
 */

public class MethodReferenceQualification_1 extends P1.pub {

  void bar () {
    P1.pub p = new P1.pub();
    p.foo();
    this.foo();
    foo();
  }

  public static void main(String[] args) {
    (new MethodReferenceQualification_1()).bar();
  }
}
