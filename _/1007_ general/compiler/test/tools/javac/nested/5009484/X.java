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

/**
 * @test    @(#)X.java	1.5 07/01/18
 * @bug     5009484
 * @summary Compiler fails to resolve appropriate type for outer member
 * @author  Philippe P Mulet
 * @compile/fail X.java
 */

public class X<T> {
   private T t;
   X(T t) {
       this.t = t;
   }
   public static void main(String[] args) {
       new X<String>("OUTER").bar();
   }
   void bar() {
       new X<X>(this) {     // #1
           void run() {
               new Object() {  // #2
                   void run() {
                       X x = t;        // #3 <--- which t is bound ?
                   System.out.println(x);
                   }
               }.run();
           }
       }.run();
   }
}
