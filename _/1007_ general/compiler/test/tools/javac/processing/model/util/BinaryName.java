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
 * @test    @(#)BinaryName.java	1.4 07/01/18
 * @bug     6346251
 * @summary Test Elements.getBinaryName
 * @author  Scott Seligman
 * @build   BinaryName
 * @compile -processor BinaryName -proc:only BinaryName.java
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.*;

import static javax.lang.model.util.ElementFilter.typesIn;

@SupportedAnnotationTypes("*")
@HelloIm("BinaryName")
public class BinaryName extends AbstractProcessor {

    Elements elements;

    public void init(ProcessingEnvironment penv) {
	super.init(penv);
	elements = penv.getElementUtils();
    }

    public boolean process(Set<? extends TypeElement> tes,
			   RoundEnvironment round) {
	if (round.processingOver()) return true;

	Set<? extends TypeElement> ts = typesIn(round.getElementsAnnotatedWith(
		elements.getTypeElement("HelloIm")));

	boolean success = true;
	for (TypeElement t : ts) {
	    String expected = t.getAnnotation(HelloIm.class).value();
	    CharSequence found = elements.getBinaryName(t);
	    if (expected.contentEquals(found)) {
		System.out.println(expected + " == " + found);
	    } else {
		success = false;
		System.out.println(expected + " != " + found + "  [FAIL]");
	    }
	}
	if (! success)
	    throw new AssertionError();
	return true;
    }

    @HelloIm("BinaryName$Nested")
    private static class Nested {
    }
}

@interface HelloIm {
    String value();
}
