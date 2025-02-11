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
 * @test    @(#)T6412656.java	1.5 07/01/18
 * @bug     6412656 6443062
 * @summary JSR 199: pass annotation processor instances to compiler
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 */

import java.util.Set;
import java.util.Collections;
import javax.lang.model.element.TypeElement;
import javax.annotation.processing.*;
import com.sun.tools.javac.processing.AnnotationProcessingError;


public class T6412656 extends ToolTester {

    int count = 0;

    void test(String... args) {
        task = tool.getTask(null, fm, null, null,
                            Collections.singleton(T6412656.class.getName()), null);
        task.setProcessors(Collections.singleton(new MyProc(this)));
        task.call();
        if (count == 0)
            throw new AssertionError("Annotation processor not run");
        System.out.println("OK");
    }

    public static void main(String... args) {
        new T6412656().test(args);
    }

    @SupportedAnnotationTypes("*")
    static class MyProc extends AbstractProcessor {
        T6412656 test;
        MyProc(T6412656 test) {
            this.test = test;
        }
        public boolean process(Set<? extends TypeElement> annotations,
                               RoundEnvironment roundEnv) {
            test.count++;
            return false;
        }
    }
}
