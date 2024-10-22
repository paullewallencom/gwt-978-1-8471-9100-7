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
 * @test @(#)TestCompletions.java	1.4
 * @bug 6341177
 * @summary Some simple tests of the methods in Completions
 * @author Joseph D. Darcy
 */
import javax.annotation.processing.Completion;
import static javax.annotation.processing.Completions.*;

public class TestCompletions {
    public static void main(String... argv) {
	String value = "value";
	String message = "message";

	Completion c = of(value, message);
	if (!value.equals(c.getValue()) ||
	    !message.equals(c.getMessage()))
	    throw new RuntimeException("Bad full completion" + c);
	
	c = of(value);
	if (!value.equals(c.getValue()) ||
	    !"".equals(c.getMessage()))
	    throw new RuntimeException("Bad value completion" + c);
    }
}
