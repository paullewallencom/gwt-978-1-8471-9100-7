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
 * @test    @(#)T6395981.java	1.6 07/01/18
 * @bug     6395981 6458819
 * @summary JavaCompilerTool and Tool must specify version of JLS and JVMS
 * @author  Peter von der Ah\u00e9
 * @run main/fail T6395981
 * @run main/fail T6395981 RELEASE_3 RELEASE_5 RELEASE_6
 * @run main/fail T6395981 RELEASE_0 RELEASE_1 RELEASE_2 RELEASE_3 RELEASE_4 RELEASE_5 RELEASE_6
 * @run main T6395981 RELEASE_3 RELEASE_4 RELEASE_5 RELEASE_6 RELEASE_7
 */

import java.util.EnumSet;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.tools.Tool;
import javax.tools.ToolProvider;
import static javax.lang.model.SourceVersion.*;

public class T6395981 {
    public static void main(String... args) {
	Tool compiler = ToolProvider.getSystemJavaCompiler();
        Set<SourceVersion> expected = EnumSet.noneOf(SourceVersion.class);
        for (String arg : args)
            expected.add(SourceVersion.valueOf(arg));
        Set<SourceVersion> found = compiler.getSourceVersions();
        Set<SourceVersion> notExpected = EnumSet.copyOf(found);
        for (SourceVersion version : expected) {
            if (!found.contains(version))
                throw new AssertionError("Expected source version not found: " + version);
            else
                notExpected.remove(version);
        }
        if (!notExpected.isEmpty())
            throw new AssertionError("Unexpected source versions: " + notExpected);
    }
}
