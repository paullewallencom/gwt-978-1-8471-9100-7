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
 * @test @(#)TestIterables.java	1.5
 * @bug 6406164
 * @summary Test that ElementFilter iterable methods behave properly.
 * @author  Joseph D. Darcy
 * @compile TestIterables.java
 * @compile -processor TestIterables -proc:only Foo1.java
 * @compile Foo1.java
 * @compile -processor TestIterables Foo1 TestIterables.java
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.tools.Diagnostic.Kind.*;

import static javax.lang.model.util.ElementFilter.*;

/**
 * Verify that the {@code ElementFilter} methods taking iterables
 * behave correctly by comparing ExpectedElementCounts with actual
 * results.
 */
@SupportedAnnotationTypes("ExpectedElementCounts")
@ExpectedElementCounts(methods=3)
public class TestIterables extends AbstractProcessor {
    
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
	if (!roundEnv.processingOver()) {
	    boolean error = false;
	    int topLevelCount = 0;

	    for (TypeElement type : typesIn(roundEnv.getRootElements())) {
		topLevelCount++;
		ExpectedElementCounts elementCounts = type.getAnnotation(ExpectedElementCounts.class);
		if (elementCounts == null)
		    throw new RuntimeException("Missing ExpectedElementCounts annotation!");

		Iterable<? extends Element> irritable = type.getEnclosedElements();
			
		int constructorCount = getCount(constructorsIn(irritable));
		int fieldCount       = getCount(fieldsIn(irritable));
		int methodCount      = getCount(methodsIn(irritable));
		int typeCount        = getCount(typesIn(irritable));
		
		System.out.println("\nType: " + type.getQualifiedName());
		System.out.format("Element      Actual\tExpected%n");
		System.out.format("constructors %d\t\t%d%n", constructorCount, elementCounts.constructors());
		System.out.format("fields       %d\t\t%d%n", fieldCount,       elementCounts.fields());
		System.out.format("methods      %d\t\t%d%n", methodCount,      elementCounts.methods());
		System.out.format("types        %d\t\t%d%n", typeCount,        elementCounts.types());

		if ((constructorCount != elementCounts.constructors()) ||
		    (fieldCount       != elementCounts.fields()) ||
		    (methodCount      != elementCounts.methods()) ||
		    (typeCount        != elementCounts.types()) )
		    error = true;
	    }

	    if (topLevelCount == 0)
		error = true;

	    if (error)
		throw new RuntimeException("A count mismatch has occured.");
	}

        return true;
    }

    private int getCount(Iterable<? extends Element> iter) {
	int count1 = 0;
	int count2 = 0;

	Iterator<? extends Element> iterator = iter.iterator();
	while (iterator.hasNext() ) {
	    count1++;
	    iterator.next();
	}

	iterator = iter.iterator();
	while (iterator.hasNext() ) {
	    count2++;
	    iterator.next();
	}
	
	if (count1 != count2)
	    throw new RuntimeException("Inconsistent counts from iterator.");

	return count1;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
	return SourceVersion.latest();
    }
    
}

