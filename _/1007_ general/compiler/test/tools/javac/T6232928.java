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
 * @test @(#)T6232928.java	1.4 07/01/18
 * @bug 6232928
 * @summary Interface package-info should be marked abstract and synthetic
 * @author Wei Tao
 * @compile T6232928.java
 * @compile T6232928/package-info.java
 * @run main T6232928
 */

import java.io.*;
import java.lang.reflect.Modifier;

public class T6232928 {
  public static void main(String... args) throws Exception {
    Class pkginfo_cls = Class.forName("T6232928.package-info");
    int mod = pkginfo_cls.getModifiers();
    if (Modifier.isAbstract(mod) && Modifier.isInterface(mod)) {
      if ((mod & 0x1000) == 0) {
        throw new AssertionError("Test failed: interface package-info should be synthetic.");
      }
    } else {
      throw new AssertionError("Test failed: interface package-info should be abstract.");
    }
  }
}

