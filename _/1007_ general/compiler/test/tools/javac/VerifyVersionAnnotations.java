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
 * @test @(#)VerifyVersionAnnotations.java	1.6 07/01/18
 * @bug 5015955
 * @ignore until 6443677 is fixed.
 * @summary add at-Version annotation to sources
 * @compile VerifyVersionAnnotations.java
 * @run shell apt.sh -factory VerifyVersionAnnotations
 */

import com.sun.mirror.apt.*;
import com.sun.mirror.declaration.*;
import com.sun.mirror.type.*;
import com.sun.mirror.util.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.*;
import static com.sun.mirror.util.DeclarationVisitors.*;

/*
 * This class is used to run an annotation processor that lists class
 * names.  The functionality of the processor is analogous to the
 * ListClass doclet in the Doclet Overview.
 */
public class VerifyVersionAnnotations implements AnnotationProcessorFactory {
    // Process any set of annotations
    private static final Collection<String> supportedAnnotations
        = unmodifiableCollection(Arrays.asList("*"));

    // No supported options
    private static final Collection<String> supportedOptions = emptySet();

    public Collection<String> supportedAnnotationTypes() {
        return supportedAnnotations;
    }

    public Collection<String> supportedOptions() {
        return supportedOptions;
    }

    public AnnotationProcessor getProcessorFor(
            Set<AnnotationTypeDeclaration> atds,
            AnnotationProcessorEnvironment env) {
        return new VersionVerifier(env);
    }

    private static class VersionVerifier implements AnnotationProcessor {
        private final AnnotationProcessorEnvironment env;
	private final TypeDeclaration versionDecl;
        VersionVerifier(AnnotationProcessorEnvironment env) {
            this.env = env;
	    versionDecl = env.getTypeDeclaration("com.sun.tools.javac.util.Version");
	    if (versionDecl == null)
		throw new Error("cannot find @Version declaration");
        }
	
        public void process() {
	    String[] javacPackageNames = {
		"com.sun.tools.javac",
		"com.sun.tools.javac.main",
		"com.sun.tools.javac.code",
		"com.sun.tools.javac.comp",
		"com.sun.tools.javac.parser",
		"com.sun.tools.javac.jvm",
		"com.sun.tools.javac.tree",
		"com.sun.tools.javac.util"
	    };

	    Set<TypeDeclaration> javacDecls = new HashSet<TypeDeclaration>();
	    for (String p: javacPackageNames) {
		javacDecls.addAll(env.getPackage(p).getClasses());
		javacDecls.addAll(env.getPackage(p).getInterfaces());
		javacDecls.addAll(env.getPackage(p).getEnums());
		javacDecls.addAll(env.getPackage(p).getAnnotationTypes());
	    }
		
	    for (TypeDeclaration typeDecl : javacDecls) {
		typeDecl.accept(getDeclarationScanner(new TypeVisitor(),
						      NO_OP));
	    }
	}

	private class TypeVisitor extends SimpleDeclarationVisitor {
	    public void visitTypeDeclaration(TypeDeclaration d) {
		if (d.getDeclaringType() == null
		    && d.getSimpleName().indexOf("$") < 0) {
		    for (AnnotationMirror am: d.getAnnotationMirrors()) {
			AnnotationType at = am.getAnnotationType();
			AnnotationTypeDeclaration ad = at.getDeclaration();
			if (ad.equals(versionDecl)) {
			    for (Map.Entry<AnnotationTypeElementDeclaration,AnnotationValue> e:
				     am.getElementValues().entrySet()) {
				System.err.println(d.getQualifiedName() 
						   + " " + e.getKey()
						   + " " + e.getValue());
			    }
			    return;
			}
		    }
		    env.getMessager().printError(d.getQualifiedName() + " does not have a @Version annotation");
		}
		    
	    }

		
	}
    }
}
