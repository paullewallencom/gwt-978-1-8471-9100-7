/*
 * @(#)JavaFileObject.java	1.10 07/01/18
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

package javax.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.Modifier;

/**
 * File abstraction for tools operating on Java&trade; programming language
 * source and class files.
 *
 * <p>All methods in this interface might throw a SecurityException if
 * a security exception occurs.
 *
 * <p>Unless explicitly allowed, all methods in this interface might
 * throw a NullPointerException if given a {@code null} argument.
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @see JavaFileManager
 * @since 1.6
 */
public interface JavaFileObject extends FileObject {

    /**
     * Kinds of JavaFileObjects.
     */
    enum Kind {
        /**
         * Source files written in the Java programming language.  For
         * example, regular files ending with {@code .java}.
         */
        SOURCE(".java"),

        /**
         * Class files for the Java Virtual Machine.  For example,
         * regular files ending with {@code .class}.
         */
        CLASS(".class"),

        /**
         * HTML files.  For example, regular files ending with {@code
         * .html}.
         */
        HTML(".html"),

        /**
         * Any other kind.
         */
        OTHER("");
        /**
         * The extension which (by convention) is normally used for
         * this kind of file object.  If no convention exists, the
         * empty string ({@code ""}) is used.
         */
        public final String extension;
        private Kind(String extension) {
            extension.getClass(); // null check
            this.extension = extension;
        }
    };

    /**
     * Gets the kind of this file object.
     *
     * @return the kind
     */
    Kind getKind();

    /**
     * Checks if this file object is compatible with the specified
     * simple name and kind.  A simple name is a single identifier
     * (not qualified) as defined in the <a
     * href="http://java.sun.com/docs/books/jls/">Java Language
     * Specification</a> 3rd ed., section 6.2 "Names and Identifiers".
     *
     * @param simpleName a simple name of a class
     * @param kind a kind
     * @return {@code true} if this file object is compatible; false
     * otherwise
     */
    boolean isNameCompatible(String simpleName, Kind kind);

    /**
     * Provides a hint about the nesting level of the class
     * represented by this file object.  This method may return
     * {@link NestingKind#MEMBER} to mean
     * {@link NestingKind#LOCAL} or {@link NestingKind#ANONYMOUS}.
     * If the nesting level is not known or this file object does not
     * represent a class file this method returns {@code null}.
     *
     * @return the nesting kind, or {@code null} if the nesting kind
     * is not known
     */
    NestingKind getNestingKind();

    /**
     * Provides a hint about the access level of the class represented
     * by this file object.  If the access level is not known or if
     * this file object does not represent a class file this method
     * returns {@code null}.
     *
     * @return the access level
     */
    Modifier getAccessLevel();

}
