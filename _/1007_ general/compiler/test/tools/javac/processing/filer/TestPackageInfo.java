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
 * @test @(#)TestPackageInfo.java	1.4
 * @bug 6380018 6392177
 * @summary Test the ability to create and process package-info.java files
 * @author  Joseph D. Darcy
 * @compile TestPackageInfo.java
 * @compile -processor TestPackageInfo -proc:only foo/bar/package-info.java TestPackageInfo.java
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.lang.model.util.ElementFilter.*;
import static javax.tools.Diagnostic.Kind.*;
import static javax.tools.StandardLocation.*;

import java.io.*;

/**
 * Test the ability to process annotations on package-info.java files:
 * 1) Visibility of package-info files from the command line
 * 2) Visibility of generated package-info.java source files
 */
@SupportedAnnotationTypes("*")
public class TestPackageInfo extends AbstractProcessor {
    private Elements eltUtils;
    private Messager messager;
    private Filer filer;
    private Map<String,String> options;

    private int round = 0;

    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
	round++;

	// Verify annotations are as expected
	Set<TypeElement> expectedAnnotations = new HashSet<TypeElement>();
	if (round == 1)
	    expectedAnnotations.add(eltUtils.
				    getTypeElement("javax.annotation.processing.SupportedAnnotationTypes"));
	expectedAnnotations.add(eltUtils.
				getTypeElement("java.lang.SuppressWarnings"));

	if (!roundEnv.processingOver()) {
	    System.out.println("\nRound " + round);
	    int rootElementSize = roundEnv.getRootElements().size();
	    
	    for(Element elt : roundEnv.getRootElements()) {
		System.out.printf("%nElement %s\tkind: %s%n", elt.getSimpleName(), elt.getKind());
		eltUtils.printElements(new PrintWriter(System.out), elt);
	    }

	    switch (round) {
	    case 1:
		if (rootElementSize != 2)
		    throw new RuntimeException("Unexpected root element size " + rootElementSize);

		// Note that foo.bar.FuBar, an element of package
		// foo.bar, contains @Deprecated which should *not* be
		// included in the set of annotations to process
 		if (!expectedAnnotations.equals(annotations)) {
 		    throw new RuntimeException("Unexpected annotations: " + annotations);
 		}

		try {
		    try {
			filer.createClassFile("package-info");
			throw new RuntimeException("Created class file for \"package-info\".");
		    } catch(FilerException fe) {}

		    PrintWriter pw = new PrintWriter(filer.createSourceFile("foo.package-info").openWriter());
		    pw.println("@SuppressWarnings(\"\")");
		    pw.println("package foo;");
		    pw.close();

		} catch(IOException ioe) {
		    throw new RuntimeException(ioe);
		}
		break;

	    case 2:
		// Expect foo.package-info

		Set<Element> expectedElement = new HashSet<Element>();
		expectedElement.add(eltUtils.getPackageElement("foo"));
		if (!expectedElement.equals(roundEnv.getRootElements()))
		    throw new RuntimeException("Unexpected root element set " + roundEnv.getRootElements());

		if (!expectedAnnotations.equals(annotations)) {
		    throw new RuntimeException("Unexpected annotations: " + annotations);
		}

		break;
		
	    default:
		throw new RuntimeException("Unexpected round number " + round);
	    }
	}
	return false;
    }
    
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
	eltUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        filer    = processingEnv.getFiler();
	options  = processingEnv.getOptions();
    }
}
