/*
 * @(#)AbstractProcessor.java	1.10 07/01/18
 * 
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *  
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
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
 */

package javax.annotation.processing;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import javax.lang.model.element.*;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;

/**
 * An abstract annotation processor designed to be a convenient
 * superclass for most concrete annotation processors.  This class
 * examines annotation values to compute the {@linkplain
 * #getSupportedOptions options}, {@linkplain
 * #getSupportedAnnotationTypes annotations}, and {@linkplain
 * #getSupportedSourceVersion source version} supported by its
 * subtypes.
 *
 * <p>The getter methods may {@linkplain Messager#printMessage issue
 * warnings} about noteworthy conditions using the facilities available
 * after the processor has been {@linkplain #isInitialized
 * initialized}.
 * 
 * <p>Subclasses are free to override the implementation and
 * specification of any of the methods in this class as long as the
 * general {@link javax.annotation.processing.Processor Processor}
 * contract for that method is obeyed.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version 1.10 07/01/18
 * @since 1.6
 */
public abstract class AbstractProcessor implements Processor {
    /**
     * Processing environment providing by the tool framework.
     */
    protected ProcessingEnvironment processingEnv;
    private boolean initialized = false;

    /**
     * Constructor for subclasses to call.
     */
    protected AbstractProcessor() {}

    /**
     * If the processor class is annotated with {@link
     * SupportedOptions}, return an unmodifiable set with the same set
     * of strings as the annotation.  If the class is not so
     * annotated, an empty set is returned.
     *
     * @return the options recognized by this processor, or an empty
     * set if none
     */
    public Set<String> getSupportedOptions() {
	SupportedOptions so = this.getClass().getAnnotation(SupportedOptions.class);
	if  (so == null) 
	    return Collections.emptySet();
	else
	    return arrayToSet(so.value()); 
    }

    /**
     * If the processor class is annotated with {@link
     * SupportedAnnotationTypes}, return an unmodifiable set with the
     * same set of strings as the annotation.  If the class is not so
     * annotated, an empty set is returned.
     *
     * @return the names of the annotation types supported by this
     * processor, or an empty set if none
     */
    public Set<String> getSupportedAnnotationTypes() {
	    SupportedAnnotationTypes sat = this.getClass().getAnnotation(SupportedAnnotationTypes.class);
	    if  (sat == null) {
		if (isInitialized())
		    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
							     "No SupportedAnnotationTypes annotation " +
							     "found on " + this.getClass().getName() +
							     ", returning an empty set.");
		return Collections.emptySet();
	    }
	    else
		return arrayToSet(sat.value()); 
	}

    /**
     * If the processor class is annotated with {@link
     * SupportedSourceVersion}, return the source version in the
     * annotation.  If the class is not so annotated, {@link
     * SourceVersion#RELEASE_6} is returned.
     *
     * @return the latest source version supported by this processor
     */
    public SourceVersion getSupportedSourceVersion() {
	SupportedSourceVersion ssv = this.getClass().getAnnotation(SupportedSourceVersion.class);
	SourceVersion sv = null;
	if (ssv == null) {
	    sv = SourceVersion.RELEASE_6;
	    if (isInitialized())
		processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
							 "No SupportedSourceVersion annotation " +
							 "found on " + this.getClass().getName() +
							 ", returning " + sv + ".");
	} else
	    sv = ssv.value();
	return sv;
    }

    
    /**
     * Initializes the processor with the processing environment by
     * setting the {@code processingEnv} field to the value of the
     * {@code processingEnv} argument.  An {@code
     * IllegalStateException} will be thrown if this method is called
     * more than once on the same object.
     * 
     * @param processingEnv environment to access facilities the tool framework
     * provides to the processor
     * @throws IllegalStateException if this method is called more than once.
     */
    public synchronized void init(ProcessingEnvironment processingEnv) {
	if (initialized)
	    throw new IllegalStateException("Cannot call init more than once.");
	if (processingEnv == null)
	    throw new NullPointerException("Tool provided null ProcessingEnvironment");

	this.processingEnv = processingEnv;
	initialized = true;
    }

    /**
     * {@inheritDoc}
     */
    public abstract boolean process(Set<? extends TypeElement> annotations,
				    RoundEnvironment roundEnv);

    /**
     * Returns an empty iterable of completions.
     *
     * @param element {@inheritDoc}
     * @param annotation {@inheritDoc}
     * @param member {@inheritDoc}
     * @param userText {@inheritDoc}
     */
    public Iterable<? extends Completion> getCompletions(Element element,
							 AnnotationMirror annotation,
							 ExecutableElement member,
							 String userText) { 
	return Collections.emptyList();
    }

    /**
     * Returns {@code true} if this object has been {@linkplain #init
     * initialized}, {@code false} otherwise.
     *
     * @return {@code true} if this object has been initialized,
     * {@code false} otherwise.
     */
    protected synchronized boolean isInitialized() {
	return initialized;
    }

    private static Set<String> arrayToSet(String[] array) {
	assert array != null;
	Set<String> set = new HashSet<String>(array.length);
	for (String s : array)
	    set.add(s);
	return Collections.unmodifiableSet(set);
    }
}
