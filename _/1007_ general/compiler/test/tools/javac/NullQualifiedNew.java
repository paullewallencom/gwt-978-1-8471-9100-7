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
 * @test @(#)NullQualifiedNew.java	1.4 07/01/18
 * @bug 4309176
 * @summary Verify exception thrown for null outer instance
 * in qualifed new instance expression.
 * @author William Maddox, Gilad Bracha
 * 
 * @run compile NullQualifiedNew.java
 * @run main/fail NullQualifiedNew
 */

public class NullQualifiedNew {

  class Nested {
    int j;
    Nested(int i){ j = i;}
  }

  public static void main(String[] args) {
    NullQualifiedNew e = null;
    e.new Nested(6);
  }

}
