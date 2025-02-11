/*
 * @(#)UninitializedType.java	1.8 07/01/18
 * 
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *  
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
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
 */

package com.sun.tools.javac.jvm;

import com.sun.tools.javac.code.*;

import com.sun.tools.javac.util.Version;

/** These pseudo-types appear in the generated verifier tables to
 *  indicate objects that have been allocated but not yet constructed.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
@Version("@(#)UninitializedType.java	1.8 07/01/18")
class UninitializedType extends Type.DelegatedType {
    public static final int UNINITIALIZED_THIS = TypeTags.TypeTagCount;
    public static final int UNINITIALIZED_OBJECT = UNINITIALIZED_THIS + 1;

    public static UninitializedType uninitializedThis(Type qtype) {
	return new UninitializedType(UNINITIALIZED_THIS, qtype, -1);
    }

    public static UninitializedType uninitializedObject(Type qtype, int offset) {
	return new UninitializedType(UNINITIALIZED_OBJECT, qtype, offset);
    }

    public final int offset; // PC where allocation took place
    private UninitializedType(int tag, Type qtype, int offset) {
	super(tag, qtype);
	this.offset = offset;
    }

    Type initializedType() {
	return qtype;
    }
}
