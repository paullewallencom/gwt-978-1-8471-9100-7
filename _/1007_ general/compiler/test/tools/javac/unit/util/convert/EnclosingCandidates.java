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
 * @test    @(#)EnclosingCandidates.java	1.4 07/01/18
 * @bug     6397652
 * @summary javac compilation failure when imported class with $ sign in the name
 * @author  Peter von der Ah\u00e9
 */

import static com.sun.tools.javac.util.Convert.enclosingCandidates;
import com.sun.tools.javac.util.*;

import java.util.Arrays;
import java.util.Locale;

public class EnclosingCandidates {

    Name.Table names = Name.Table.instance(new Context());

    void test(String name, String... expected) {
        List<Name> result = enclosingCandidates(names.fromString(name));
        if (!result.isEmpty() || expected.length != 0) {
            Name[] expectedNames = new Name[expected.length];
            int i = 0;
            for (String s : expected)
                expectedNames[i++] = names.fromString(s);
            if (!Arrays.equals(result.toArray(), expectedNames))
                throw new AssertionError(name + " : " +
                                         Arrays.toString(expectedNames) + " != " + result);
        }
        System.out.format((Locale)null, "OK: %s -> [%s]%n", name, result);
    }

    public static void main(String... args) {
        EnclosingCandidates test = new EnclosingCandidates();
        test.test("");
        test.test("foo");
        test.test("foo$bar", "foo");
        test.test("foo$bar$baz", "foo", "foo$bar");
        test.test("x$foo", "x");
        test.test("$foo$", "$foo");
        test.test("$foo$x", "$foo");
        test.test("$foo");
        test.test("foo$", "foo");
        test.test("foo$bar$", "foo", "foo$bar");
    }

}
