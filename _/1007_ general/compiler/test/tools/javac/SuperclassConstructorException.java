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
 * @test @(#)SuperclassConstructorException.java	1.5 07/01/18
 * @bug 4350863 4358130
 * @summary Verify that name of superclass constructor exception can be
 * resolved correctly when instantiating a subclass.
 * @author maddox
 *
 * @compile SuperclassConstructorException.java
 */

class Superclass {
  public Superclass() throws java.io.IOException {}
}

class Subclass extends SuperclassConstructorException {}

class SuperclassConstructorException {

  public static void main(String args[]) {        
    try {
      Object x = new Superclass(){};
      Object y = new Subclass();
    } catch (java.io.IOException e) {};
  }
}
