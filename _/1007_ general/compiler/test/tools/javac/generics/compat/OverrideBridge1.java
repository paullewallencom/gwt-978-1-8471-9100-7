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
 * @test @(#)OverrideBridge1.java	1.8 07/01/18
 * @bug 4836048 4868021 5030040 5052968 5056864
 * @summary generics: compiler allows 1.4 code to override a bridge method
 * @author gafter
 *
 * @compile -source 1.5 OverrideBridge1.java
 * @compile/fail -Werror -source 1.4 OverrideBridge2.java
 * @compile -source 1.4 OverrideBridge2.java
 * @compile -source 1.5 OverrideBridge3.java
 */

// ALLOW users to override bridge methods.

// Note the long list of bug numbers on this regression test.  They
// indicate the number of times we've flip-flopped on this issue.
// 5030040 shows why we must give a diagnostic.  5052968 shows why it
// must be a warning.

class OverrideBridge1 {
    static class A<T> {
	public void foo(T t) { }
    }
    static class B extends A<String> {
	public void foo(String t) { }
    }
}
