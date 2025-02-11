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
 * @test    @(#)DirectSupersOfErr.java	1.5 07/01/18
 * @bug     6346973
 * @summary directSupertypes(t) should not return t
 * @author  Scott Seligman
 * @build   DirectSupersOfErr
 * @compile -processor DirectSupersOfErr -proc:only C1.java
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.*;
import static javax.lang.model.util.ElementFilter.*;

@SupportedAnnotationTypes("*")
public class DirectSupersOfErr extends AbstractProcessor {

    Types types;

    public void init(ProcessingEnvironment penv) {
	super.init(penv);
	types = penv.getTypeUtils();
    }

    public boolean process(Set<? extends TypeElement> tes,
			   RoundEnvironment round) {
	if (round.processingOver()) return true;

	for (TypeElement te : typesIn(round.getRootElements())) {
	    TypeMirror sup = te.getSuperclass();
	    for (TypeMirror supOfSup : types.directSupertypes(sup)) {
		if (sup == supOfSup)
		    throw new AssertionError("I'm my own supertype.");
	    }
	}
	return true;
    }
}
