/*
 * @(#)package-info.java	1.8 07/01/18
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

/**
 * Interfaces used to model elements of the Java programming language.
 *
 * <p>When used in the context of annotation processing, an accurate
 * model of the element being represented must be returned.  As this
 * is a language model, the source code provides the fiducial
 * (reference) representation of the construct in question rather than
 * a representation in an executable output like a class file.
 * Executable output may serve as the basis for creating a modeling
 * element.  However, the process of translating source code to
 * executable output may not permit recovering some aspects of the
 * source code representation.  For example, annotations with
 * {@linkplain java.lang.annotation.RetentionPolicy#SOURCE source}
 * {@linkplain java.lang.annotation.Retention retention} cannot be
 * recovered from class files and class files might not be able to
 * provide source position information.  The {@linkplain
 * javax.lang.model.element.Modifier modifiers} on an element may
 * differ in some cases including
 *
 * <ul>
 * <li> {@code strictfp} on a class or interface
 * <li> {@code final} on a parameter
 * <li> {@code protected}, {@code private}, and {@code static} on classes and interfaces
 * </ul>
 *
 * Additionally, synthetic constructs in a class file, such as
 * accessor methods used in implementing nested classes and bridge
 * methods used in implementing covariant returns, are translation
 * artifacts outside of this model.
 *
 * <p>During annotation processing, operating on incomplete or
 * erroneous programs is necessary; however, there are fewer
 * guarantees about the nature of the resulting model.  If the source
 * code is not syntactically well-formed, a model may or may not be
 * provided as a quality of implementation issue.  If a program is
 * syntactically valid but erroneous in some other fashion, the
 * returned model must have no less information than if all the method
 * bodies in the program were replaced by {@code "throw new
 * RuntimeException();"}.  If a program refers to a missing type XYZ,
 * the returned model must contain no less information than if the
 * declaration of type XYZ were assumed to be {@code "class XYZ {}"},
 * {@code "interface XYZ {}"}, {@code "enum XYZ {}"}, or {@code
 * "@interface XYZ {}"}. If a program refers to a missing type {@code
 * XYZ<K1, ... ,Kn>}, the returned model must contain no less
 * information than if the declaration of XYZ were assumed to be
 * {@code "class XYZ<T1, ... ,Tn> {}"} or {@code "interface XYZ<T1,
 * ... ,Tn> {}"}
 *
 * <p> Unless otherwise specified in a particular implementation, the
 * collections returned by methods in this package should be expected
 * to be unmodifiable by the caller and unsafe for concurrent access.
 *
 * <p> Unless otherwise specified, methods in this package will throw
 * a {@code NullPointerException} if given a {@code null} argument.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version 1.8 07/01/18
 * @since 1.6
 */
package javax.lang.model.element;
