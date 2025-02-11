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
 * @test @(#)T6350057.java	1.6
 * @bug 6350057
 * @summary Test that parameters on implicit enum methods have the right kind
 * @author  Joseph D. Darcy
 * @compile T6350057.java
 * @compile -processor T6350057 -proc:only TestEnum.java
 */

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import static javax.tools.Diagnostic.Kind.*;

@SupportedAnnotationTypes("*")
public class T6350057 extends AbstractProcessor {
    static class LocalVarAllergy extends ElementKindVisitor6<Boolean, Void> {
	@Override		
	public Boolean visitTypeAsEnum(TypeElement e, Void v) {
	    System.out.println("visitTypeAsEnum: " + e.getSimpleName().toString());
	    for(Element el: e.getEnclosedElements() )
		this.visit(el);
	    return true;
	}

	@Override		
	public Boolean visitVariableAsLocalVariable(VariableElement e, Void v){ 
	    throw new IllegalStateException("Should not see any local variables!");
	}

	@Override		
	public Boolean visitVariableAsParameter(VariableElement e, Void v){ 
	    String senclm=e.getEnclosingElement().getEnclosingElement().getSimpleName().toString();
	    String sEncl = senclm+"."+e.getEnclosingElement().getSimpleName().toString();
	    String stype = e.asType().toString();
	    String name =  e.getSimpleName().toString();
	    System.out.println("visitVariableAsParameter: " +sEncl+"." + stype + " " + name);
	    return true; 
	}

	@Override		
	public Boolean visitExecutableAsMethod(ExecutableElement e, Void v){ 
	    String name=e.getEnclosingElement().getSimpleName().toString();	  
	    name = name + "."+e.getSimpleName().toString();
	    System.out.println("visitExecutableAsMethod: " + name);
	    for (VariableElement ve: e.getParameters())
		this.visit(ve);
	    return true; 
	}
    }

    public boolean process(Set<? extends TypeElement> annotations,
			   RoundEnvironment roundEnvironment) {
	if (!roundEnvironment.processingOver())
	    for(Element element : roundEnvironment.getRootElements())
		element.accept(new LocalVarAllergy(), null);
	return true;
    }
}
