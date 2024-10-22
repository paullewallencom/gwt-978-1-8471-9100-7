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
 * @test    @(#)OverEager.java	1.5 07/01/18
 * @bug     6362178
 * @summary MirroredType[s]Exception shouldn't be created too eagerly
 * @author  Scott Seligman
 * @compile -g OverEager.java
 * @compile -processor OverEager -proc:only OverEager.java
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.*;
import static javax.lang.model.util.ElementFilter.*;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("IAm")
@IAm(OverEager.class)
public class OverEager extends AbstractProcessor {

    Elements elements;
    Types types;

    public void init(ProcessingEnvironment penv) {
	super.init(penv);
	elements = penv.getElementUtils();
	types =  penv.getTypeUtils();
    }

    public boolean process(Set<? extends TypeElement> annoTypes,
			   RoundEnvironment round) {
	if (!round.processingOver())
	    doit(annoTypes, round);
	return true;
    }

    private void doit(Set<? extends TypeElement> annoTypes,
		      RoundEnvironment round) {
	for (TypeElement t : typesIn(round.getRootElements())) {
	    IAm anno = t.getAnnotation(IAm.class);
	    if (anno != null)
		checkAnno(anno);
	}
    }

    private void checkAnno(IAm anno) {
	try {
	    anno.value();
	    throw new AssertionError();
	} catch (MirroredTypeException e) {
	    System.out.println("Looking for checkAnno in this stack trace:");
	    e.printStackTrace();
	    for (StackTraceElement frame : e.getStackTrace()) {
		if (frame.getMethodName() == "checkAnno")
		    return;
	    }
	    throw new AssertionError();
	}
    }
}

@interface IAm {
    Class<?> value();
}
