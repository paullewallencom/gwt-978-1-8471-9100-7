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
 * @test @(#)QualifiedOuterThis2.java	1.4 07/01/18
 * @bug 4843805
 * @summary compiler crash when class literals refers to enclosing class in a super clause
 * @author gafter
 *
 * @compile QualifiedOuterThis2.java
 */

public class QualifiedOuterThis2 {
    class TableCellEditor extends X {
        TableCellEditor() {
            super(new Object() {
		    public String xyz() {
			return QualifiedOuterThis2.this.toString();
		    }
		});
	}
    }
}
class X {
    X(Object o) {}
}
