/*
 * @(#)AbstractAnnotationValueVisitor6.java	1.6 07/01/18
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

package javax.lang.model.util;


import java.util.List;
import javax.lang.model.element.*;

import javax.lang.model.type.TypeMirror;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.SourceVersion;
import javax.annotation.processing.SupportedSourceVersion;

/**
 * A skeletal visitor for annotation values with default behavior
 * appropriate for the {@link SourceVersion#RELEASE_6 RELEASE_6}
 * source version.
 *
 * <p> <b>WARNING:</b> The {@code AnnotationValueVisitor} interface
 * implemented by this class may have methods added to it in the
 * future to accommodate new, currently unknown, language structures
 * added to future versions of the Java&trade; programming language.
 * Therefore, methods whose names begin with {@code "visit"} may be
 * added to this class in the future; to avoid incompatibilities,
 * classes which extend this class should not declare any instance
 * methods with names beginning with {@code "visit"}.
 * 
 * <p>When such a new visit method is added, the default
 * implementation in this class will be to call the {@link
 * #visitUnknown visitUnknown} method.  A new abstract annotation
 * value visitor class will also be introduced to correspond to the
 * new language level; this visitor will have different default
 * behavior for the visit method in question.  When the new visitor is
 * introduced, all or portions of this visitor may be deprecated.
 *
 * @param <R> the return type of this visitor's methods
 * @param <P> the type of the additional parameter to this visitor's methods.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version 1.6 07/01/18
 * @since 1.6
 */
@SupportedSourceVersion(RELEASE_6)
public abstract class AbstractAnnotationValueVisitor6<R, P> 
    implements AnnotationValueVisitor<R, P> {

    /**
     * Constructor for concrete subclasses to call.
     */
    protected AbstractAnnotationValueVisitor6() {}

    /**
     * Visits an annotation value as if by passing itself to that
     * value's {@link AnnotationValue#accept accept}.  The invocation
     * {@code v.visit(av)} is equivalent to {@code av.accept(v, p)}.
     * @param av {@inheritDoc}
     * @param p  {@inheritDoc}
     */
    public final R visit(AnnotationValue av, P p) {
	return av.accept(this, p);
    }

    /**
     * Visits an annotation value as if by passing itself to that
     * value's {@link AnnotationValue#accept accept} method passing
     * {@code null} for the additional parameter.  The invocation
     * {@code v.visit(av)} is equivalent to {@code av.accept(v,
     * null)}.
     * @param av {@inheritDoc}
     */
    public final R visit(AnnotationValue av) {
	return av.accept(this, null);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation of this method in {@code
     * AbstractAnnotationValueVisitor6} will always throw {@code
     * UnknownAnnotationValueException}.  This behavior is not
     * required of a subclass.
     *
     * @param av {@inheritDoc}
     * @param p  {@inheritDoc}
     */
    public R visitUnknown(AnnotationValue av, P p) {
	throw new UnknownAnnotationValueException(av, p);
    }
}
