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

/**
 * @test    @(#)T6194785.java	1.6 07/01/18
 * @bug     6194785
 * @summary ParameterDeclaration.getSimpleName does not return actual name from class files
 * @author  Peter von der Ah\u00e9
 * @compile -g T6194785.java T6194785a.java
 * @compile -processor T6194785 foo.T6194785a T6194785.java
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.tools.Diagnostic.Kind.*;

@SupportedAnnotationTypes("*")
public class T6194785 extends AbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment)
    {
	final Messager log = processingEnv.getMessager();
	final Elements elements = processingEnv.getElementUtils();
        class Scan extends ElementScanner6<Void,Void> {
            @Override
            public Void visitExecutable(ExecutableElement e, Void ignored) {
		for (VariableElement p : e.getParameters())
		    if ("arg0".equals(p.getSimpleName().toString()))
			throw new AssertionError(e);
                return null;
            }
        }
        Scan scan = new Scan();
        for (Element e : roundEnvironment.getRootElements()) {
            scan.scan(e);
        }
        return true;
    }
}
