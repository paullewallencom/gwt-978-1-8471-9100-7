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
 * @test @(#)Switch1.java	1.4 07/01/18
 * @bug 4099995 4118025
 * @summary Bad optimization of "switch" statement
 * @author dps
 *
 * @run clean Switch1
 * @run compile -O Switch1.java
 * @run main Switch1
 */

public class Switch1
{
    public static void main (String argc[])
    {
	for (int i = 0; i < 2; i++) {
	    String ret;

	    ret = test(0);
	    if (ret.equals("Error"))
		throw new RuntimeException("test(0) = Error");
	    System.out.println("test(0) = " + ret);

	    ret = test(1);
	    if (ret.equals("Error"))
		throw new RuntimeException("test(1) = Error");
	    System.out.println("test(1) = " + ret);
	}
    }

    private static String test (int i)
    {
        switch (i) {
        case 0:
            return ("found 0");
        case 1:
            return ("found 1");
        }
        return ("Error");
    }
}
