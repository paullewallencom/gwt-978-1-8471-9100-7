/*
 * @(#)TaskEvent.java	1.6 07/01/18
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

package com.sun.source.util;

import com.sun.source.tree.CompilationUnitTree;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Provides details about work that has been done by the Sun Java Compiler, javac.
 *
 * @author Jonathan Gibbons
 * @since 1.6
 */
public final class TaskEvent
{
    /**
     * Kind of task event.
     * @since 1.6
     */
    public enum Kind {
        /**
         * For events related to the parsing of a file.
         */
	PARSE,
        /**
         * For events relating to elements being entered.
         **/
	ENTER,
        /**
         * For events relating to elements being analyzed for errors.
         **/
	ANALYZE,
        /**
         * For events relating to class files being generated.
         **/
	GENERATE,
        /**
         * For events relating to overall annotaion processing.
         **/
        ANNOTATION_PROCESSING,
        /**
         * For events relating to an individual annotation processing round.
         **/
        ANNOTATION_PROCESSING_ROUND
    };

    public TaskEvent(Kind kind) {
	this(kind, null, null, null);
    }

    public TaskEvent(Kind kind, JavaFileObject sourceFile) {
	this(kind, sourceFile, null, null);
    }

    public TaskEvent(Kind kind, CompilationUnitTree unit) {
	this(kind, unit.getSourceFile(), unit, null);
    }

    public TaskEvent(Kind kind, CompilationUnitTree unit, TypeElement clazz) {
	this(kind, unit.getSourceFile(), unit, clazz);
    }

    private TaskEvent(Kind kind, JavaFileObject file, CompilationUnitTree unit, TypeElement clazz) {
	this.kind = kind;
	this.file = file;
	this.unit = unit;
	this.clazz = clazz;
    }

    public Kind getKind() {
	return kind;
    }

    public JavaFileObject getSourceFile() {
	return file;
    }

    public CompilationUnitTree getCompilationUnit() {
	return unit;
    }

    public TypeElement getTypeElement() {
	return clazz;
    }
    
    public String toString() {
	return "TaskEvent[" 
	    + kind + "," 
	    + file + ","
	    // the compilation unit is identified by the file
	    + clazz + "]";
    }

    private Kind kind;
    private JavaFileObject file;
    private CompilationUnitTree unit;
    private TypeElement clazz;
}
