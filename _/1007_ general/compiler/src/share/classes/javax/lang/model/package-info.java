/*
 * @(#)package-info.java	1.6 07/01/18
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
 * Classes and hierarchies of packages used to model the Java
 * programming language.  
 *
 * The members of this package and its subpackages are for use in
 * language modeling and language processing tasks and APIs including,
 * but not limited to, the {@linkplain javax.annotation.processing
 * annotation processing} framework.
 *
 * <p> This language model follows a <i>mirror</i>-based design; see
 *
 * <blockquote>
 * Gilad Bracha and David Ungar. <i>Mirrors: Design Principles for
 * Meta-level Facilities of Object-Oriented Programming Languages</i>.
 * In Proc. of the ACM Conf. on Object-Oriented Programming, Systems,
 * Languages and Applications, October 2004.
 * </blockquote>
 * 
 * In particular, the model makes a distinction between static
 * language constructs, like the {@linkplain javax.lang.model.element
 * element} representing {@code java.util.Set}, and the family of
 * {@linkplain javax.lang.model.type types} that may be associated
 * with an element, like the raw type {@code java.util.Set}, {@code
 * java.util.Set<String>}, and {@code java.util.Set<T>}.
 *
 * <p> Unless otherwise specified, methods in this package will throw
 * a {@code NullPointerException} if given a {@code null} argument.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version 1.6 07/01/18
 * @since 1.6
 */

package javax.lang.model;
