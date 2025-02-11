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
 * @test @(#)TestFatalityOfParseErrors.java	1.5
 * @bug 6403459
 * @summary Test that generating programs with syntax errors is a fatal condition
 * @author  Joseph D. Darcy
 * @compile TestFatalityOfParseErrors.java
 * @compile/fail -XprintRounds -processor TestFatalityOfParseErrors -proc:only TestFatalityOfParseErrors.java
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.tools.Diagnostic.Kind.*;

import java.io.PrintWriter;
import java.io.IOException;

/**
 * Write out an incomplete source file and observe that the next round
 * is marked as an error.
 */
@SupportedAnnotationTypes("*")
public class TestFatalityOfParseErrors extends AbstractProcessor {
    int round = 0;
    Messager messager;
    Filer filer;

    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment) {
	try {
	    PrintWriter pw = null;
	    round++;

	    switch (round) {
	    case 1:
		pw = new PrintWriter(filer.createSourceFile("SyntaxError").openWriter());
		pw.println("class SyntaxError {");
		pw.close();
		break;

	    case 2:
		pw = new PrintWriter(filer.createSourceFile("SimpleClass").openWriter());
		pw.println("class SimpleClass {}");
		pw.close();
	    
		if (!roundEnvironment.errorRaised() || !roundEnvironment.processingOver() ) {
		    System.err.println(roundEnvironment);
		    throw new RuntimeException("Second round not erroneous as expected.");
		}
		if (!roundEnvironment.getRootElements().isEmpty()) {
		    System.err.println(roundEnvironment);
		    throw new RuntimeException("Root elements not empty as expected.");
		}
		break;

	    default:
		throw new RuntimeException("Unexpected round number " + round);
	    }
	} catch (IOException ioException) {
	    throw new RuntimeException(ioException);
	}
	return true;
    }
    
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    public void init(ProcessingEnvironment processingEnv) {
	super.init(processingEnv);
	messager = processingEnv.getMessager();
	filer    = processingEnv.getFiler();
    }
}
