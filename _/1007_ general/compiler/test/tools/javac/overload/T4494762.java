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
 * @test    @(#)T4494762.java	1.2 07/01/18
 * @bug     4494762 6491939
 * @summary Request for Clarification of JLS 15.12.2.2
 * @author  Peter von der Ah\u00e9
 * @compile T4494762.java
 */

public class T4494762 {

    static class Homer {
        float doh(float f) {
            System.out.println("doh(float)");
            return 1.0f;
        }

        char doh(char c) {
            System.out.println("doh(string)");
            return 'd';
        }
    }

    static class Bart extends Homer {
        float doh(float f) {
            System.out.println("doh(float)");
            return 1.0f;
        }
    }

    public static void main(String[] args) {
        Bart b = new Bart();
        b.doh('x');//compiler error in this line
        b.doh(1);
        b.doh(1.0f);
    }
}
