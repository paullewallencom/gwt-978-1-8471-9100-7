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
 * @test @(#)MemberModifiers.java	1.10 07/01/18
 * @bug 4249112 4785453
 * @summary Verify that implicit member modifiers are set correctly.
 *
 * @compile/ref=MemberModifiers.out -XDstdout  -source 1.4 -target 1.4.2 -XDdumpmodifiers=cfm MemberModifiers.java
 */

// Currently, we check only that members of final classes are not final.
// Originally, we tested that methods were final, per the fix for 4249112.
// This fix was backed out, however, based on a determination that the
// ACC_FINAL bit need not actually be set, and should not be for compatibility
// reasons.

public final class MemberModifiers {

    //Should not be final.
    int f;
    void m() {};
    class c {};
    interface i {}

}


class MemberModifiersAux {

    final class Foo {

	//Should not be final.
	int f;
	void m() {};
	class c {};

    }
    
}
