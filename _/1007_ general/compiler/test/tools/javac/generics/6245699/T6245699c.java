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
 * @test    @(#)T6245699c.java	1.5 07/01/18
 * @bug     6245699
 * @summary Missing bridge for final method (gives AbstractMethodError at runtime)
 * @author  Peter von der Ah\u00e9
 */

public class T6245699c {
    public static void main(String[] args) {
        Interface x = new Derived();
        x.method("blah"); // throws AbstractMethodError
    }

    static interface Interface {
        void method(String arg);
    }

    static class Base<T> {
        public final void method(T arg) {}
    }

    static class Derived extends Base<String> implements Interface {}
}
