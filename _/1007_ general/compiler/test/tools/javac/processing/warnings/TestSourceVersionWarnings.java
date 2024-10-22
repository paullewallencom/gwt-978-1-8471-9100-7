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
 * @test @(#)TestSourceVersionWarnings.java	1.5
 * @bug 6376083 6376084 6458819
 * @summary Test that warnings about source versions are output as expected.
 * @author  Joseph D. Darcy
 * @compile TestSourceVersionWarnings.java
 * @compile/ref=gold_0.out             -XDrawDiagnostics -XDstdout -processor TestSourceVersionWarnings -proc:only                           -source 1.5 HelloWorld.java
 * @compile/ref=gold_sv_warn_0_2.out   -XDrawDiagnostics -XDstdout -processor TestSourceVersionWarnings -proc:only -ASourceVersion=RELEASE_0 -source 1.2 HelloWorld.java
 * @compile/ref=gold_sv_none.out       -XDrawDiagnostics -XDstdout -processor TestSourceVersionWarnings -proc:only -ASourceVersion=RELEASE_2 -source 1.2 HelloWorld.java
 * @compile/ref=gold_sv_warn_2_3.out   -XDrawDiagnostics -XDstdout -processor TestSourceVersionWarnings -proc:only -ASourceVersion=RELEASE_2 -source 1.3 HelloWorld.java
 * @compile/ref=gold_sv_none.out       -XDrawDiagnostics -XDstdout -processor TestSourceVersionWarnings -proc:only -ASourceVersion=RELEASE_5 -source 1.5 HelloWorld.java
 * @compile/ref=gold_sv_warn_5_6.out   -XDrawDiagnostics -XDstdout -processor TestSourceVersionWarnings -proc:only -ASourceVersion=RELEASE_5 -source 1.6 HelloWorld.java
 * @compile/ref=gold_sv_none.out       -XDrawDiagnostics -XDstdout -processor TestSourceVersionWarnings -proc:only -ASourceVersion=RELEASE_6 -source 1.6 HelloWorld.java
 * @compile/ref=gold_unsp_warn.out     -XDrawDiagnostics -XDstdout -processor TestSourceVersionWarnings -proc:only -ASourceVersion=RELEASE_6 -source 1.6 -Aunsupported HelloWorld.java
 * @compile/ref=gold_sv_none.out       -XDrawDiagnostics -XDstdout -processor TestSourceVersionWarnings -proc:only -ASourceVersion=RELEASE_7 -source 1.7 HelloWorld.java
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.tools.Diagnostic.Kind.*;

/**
 * This processor returns the supported source level as indicated by
 * the "SourceLevel" option.
 */
@SupportedAnnotationTypes("*")
@SupportedOptions("SourceVersion")
public class TestSourceVersionWarnings extends AbstractProcessor {
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
	String sourceVersion = processingEnv.getOptions().get("SourceVersion");
	if (sourceVersion == null) {
	    processingEnv.getMessager().printMessage(WARNING,
						     "No SourceVersion option given");
	    return SourceVersion.RELEASE_6;
	} else {
	    return SourceVersion.valueOf(sourceVersion);
	}
    }
    
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment) {
        return true;
    }
}

