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
 * @test    @(#)T5034571.java	1.5 07/01/18
 * @bug     5034571
 * @summary Wildcard capture must use the bounds of the formal
 * @compile T5034571.java
 */

public class T5034571 {
    interface I1 {
        void i1();
    }

    class G1<T extends I1> {
        T get() { return null; }
    }

    interface I2 {
        void i2();
    }

    class Main {
        void f1(G1<?> g1) {
            g1.get().i1();
        }
        void f2(G1<? extends I2> g1) {
            g1.get().i1();
            g1.get().i2();
        }
    }
}
