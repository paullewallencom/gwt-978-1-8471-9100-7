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
 * @test @(#)TestGetPackageOf.java	1.4
 * @bug 6453386
 * @summary Test Elements.getPackageOf
 * @author  Joseph D. Darcy
 * @build TestGetPackageOf
 * @compile -processor TestGetPackageOf -proc:only TestGetPackageOf.java
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.lang.model.util.ElementFilter.*;
import static javax.tools.Diagnostic.Kind.*;
import static javax.tools.StandardLocation.*;

/**
 * Test basic workings of Elements.getPackageOf
 */
@SupportedAnnotationTypes("*")
public class TestGetPackageOf extends AbstractProcessor {
    private Elements eltUtils;

    /**
     * Check expected behavior on classes and packages.
     */
    public boolean process(Set<? extends TypeElement> annotations,
			   RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
	    TypeElement    stringElt   = eltUtils.getTypeElement("java.lang.String");
	    PackageElement javaLangPkg = eltUtils.getPackageElement("java.lang");
	    PackageElement unnamedPkg  = eltUtils.getPackageElement("");
	    PackageElement pkg = null;
	    
	    if (!javaLangPkg.equals(pkg=eltUtils.getPackageOf(stringElt) ) )
		throw new RuntimeException("Unexpected package for String: " + pkg);

	    if (!javaLangPkg.equals(pkg=eltUtils.getPackageOf(javaLangPkg) ) )
		throw new RuntimeException("Unexpected package for java.lang: " + pkg);

	    if (!unnamedPkg.equals(pkg=eltUtils.getPackageOf(unnamedPkg) ) )
		throw new RuntimeException("Unexpected package for unnamed pkg: " + pkg);
        }
        return true;
    }
    
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
    
    public void init(ProcessingEnvironment processingEnv) {
	super.init(processingEnv);
	eltUtils = processingEnv.getElementUtils();
    }
}
