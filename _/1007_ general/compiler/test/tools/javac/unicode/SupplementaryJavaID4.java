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
 * @test @(#)SupplementaryJavaID4.java	1.4 07/01/18
 * @bug 4914724
 * @summary Ensure that a supplementary character that cannot be the start of a Java
 *          identifier causes a compilation failure, if it is used as the start of an
 *          identifier
 * @author Naoto Sato
 *
 * @compile/fail SupplementaryJavaID4.java
 */

public class SupplementaryJavaID4 {
    // U+1D17B (\ud834\udd7b): MUSICAL SYMBOL COMBINING ACCENT (can only be part)
    int \ud834\udd7b;
}
