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
 * 1.4 07/01/18
 * Auxilliary file for test for 087314.
 * Test access checking within 'extends' and 'implements' clause.
 *
 */

package p;

public class ExtendsAccess {
    class defaultClass { }
    public class publicClass { }
    private class privateClass { }
    protected class protectedClass { }

    static class defaultStaticClass { }
    public static class publicStaticClass { }
    private static class privateStaticClass { }
    protected static class protectedStaticClass { }

    interface defaultInterface { }
    public interface publicInterface { }
    private interface privateInterface { }
    protected interface protectedInterface { }
}
