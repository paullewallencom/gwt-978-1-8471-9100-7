/*
 * @(#)SourceVersion.java	1.10 07/01/18
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

package javax.lang.model;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

/**
 * Source versions of the Java&trade; programming language.
 * 
 * See <a
 * href="http://java.sun.com/docs/books/jls/">http://java.sun.com/docs/books/jls/</a>
 * for information on editions of <i>The Java&trade; Language
 * Specification</i>, including updates and clarifications.
 *
 * <p>Note that additional source version constants will be added to
 * model future releases of the language.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version 1.10 07/01/18
 * @since 1.6
 */
public enum SourceVersion {
    /*
     * Summary of language evoluation
     * 1.1: nested classes
     * 1.2: strictfp
     * 1.3: no changes
     * 1.4: assert
     * 1.5: annotations, generics, autoboxing, var-args... 
     * 1.6: no changes
     */

    /**
     * The original version.
     * 
     * The language described in the first edition of <i>The
     * Java&trade; Language Specification</i>.
     */
    RELEASE_0,

    /**
     * The version recognized by the Java Platform 1.1.
     *
     * The language is {@code RELEASE_0} <a
     * href="http://java.sun.com/docs/books/jls/first_edition/html/1.1Update.html">augmented</a>
     * with nested classes.
     */
    RELEASE_1,

    /**
     * The version recognized by the Java 2 Platform, Standard Edition,
     * v 1.2.
     *
     * The language described in <i>The Java&trade; Language
     * Specification, Second Edition</i>, which includes the {@code
     * strictfp} modifier.
     */
    RELEASE_2,

    /**
     * The version recognized by the Java 2 Platform, Standard Edition,
     * v 1.3.
     *
     * No major changes from {@code RELEASE_2}.
     */
    RELEASE_3,

    /**
     * The version recognized by the Java 2 Platform, Standard Edition,
     * v 1.4.
     *
     * Added a simple assertion facility.
     */
    RELEASE_4,

    /**
     * The version recognized by the Java 2 Platform, Standard
     * Edition 5.0.
     *
     * The language described in <i>The Java&trade; Language
     * Specification, Third Edition</i>.  First release to support
     * generics, annotations, autoboxing, var-args, enhanced {@code
     * for} loop, and hexadecimal floating-point literals.
     */
    RELEASE_5,

    /**
     * The version recognized by the Java Platform, Standard Edition
     * 6.
     *
     * No major changes from {@code RELEASE_5}.
     */
    RELEASE_6,

    /**
     * The version recognized by the Java Platform, Standard Edition
     * 7.
     *
     * @since 1.7
     */
    RELEASE_7;

    // Note that when adding constants for newer releases, the
    // behavior of latest() and latestSupported() must be updated too.

    /**
     * Returns the latest source version that can be modeled.
     *
     * @return the latest source version that can be modeled
     */
    public static SourceVersion latest() {
	return RELEASE_7;
    }

    private static final SourceVersion latestSupported = getLatestSupported();
    
    private static SourceVersion getLatestSupported() {
	try {
	    String specVersion = System.getProperty("java.specification.version");
	    if ("1.7".equals(specVersion))
		return RELEASE_7;
	    else if ("1.6".equals(specVersion))
		return RELEASE_6;
	} catch (SecurityException se) {}
	
	return RELEASE_5;
    }

    /**
     * Returns the latest source version fully supported by the
     * current execution environment.  {@code RELEASE_5} or later must
     * be returned.
     *
     * @return the latest source version that is fully supported
     */
    public static SourceVersion latestSupported() {
	return latestSupported;
    }

    /**
     * Returns whether or not {@code name} is a syntactically valid
     * identifier (simple name) or keyword in the latest source
     * version.  The method returns {@code true} if the name consists
     * of an initial character for which {@link
     * Character#isJavaIdentifierStart(int)} returns {@code true},
     * followed only by characters for which {@link
     * Character#isJavaIdentifierPart(int)} returns {@code true}.
     * This pattern matches regular identifiers, keywords, and the
     * literals {@code "true"}, {@code "false"}, and {@code "null"}.
     * The method returns {@code false} for all other strings.
     *
     * @param name the string to check
     * @return {@code true} if this string is a
     * syntactically valid identifier or keyword, {@code false}
     * otherwise.
     */
    public static boolean isIdentifier(CharSequence name) {
	String id = name.toString();
	
	if (id.length() == 0) {
	    return false;
	}
	int cp = id.codePointAt(0);
        if (!Character.isJavaIdentifierStart(cp)) {
	    return false;
	}
        for (int i = Character.charCount(cp);
		i < id.length();
		i += Character.charCount(cp)) {
	    cp = id.codePointAt(i);
            if (!Character.isJavaIdentifierPart(cp)) {
		return false;
	    }
        }
        return true;
    }

    /**
     *  Returns whether or not {@code name} is a syntactically valid
     *  qualified name in the latest source version.  Unlike {@link
     *  #isIdentifier isIdentifier}, this method returns {@code false}
     *  for keywords and literals.
     *
     * @param name the string to check
     * @return {@code true} if this string is a
     * syntactically valid name, {@code false} otherwise.
     * @jls3 6.2 Names and Identifiers
     */
    public static boolean isName(CharSequence name) {
	String id = name.toString();
	
	for(String s : id.split("\\.", -1)) {
	    if (!isIdentifier(s) || isKeyword(s))
		return false;
	}
	return true;
    }

    private final static Set<String> keywords;
    static {
	Set<String> s = new HashSet<String>();
	String [] kws = {
	    "abstract",	"continue",	"for",		"new",		"switch",
	    "assert",	"default",	"if",		"package",	"synchronized",
	    "boolean",	"do",		"goto",		"private",	"this",
	    "break",	"double",	"implements",	"protected",	"throw",
	    "byte",	"else",		"import",	"public",	"throws",
	    "case",	"enum",		"instanceof",	"return",	"transient",
	    "catch",	"extends",	"int",		"short",	"try",
	    "char",	"final",	"interface",	"static",	"void",
	    "class",	"finally",	"long",		"strictfp",	"volatile",
	    "const",	"float",	"native",	"super",	"while",
	    // literals
	    "null",	"true",		"false"
	};
	for(String kw : kws)
	    s.add(kw);
	keywords = Collections.unmodifiableSet(s);
    }

    /**
     *  Returns whether or not {@code s} is a keyword or literal in the
     *  latest source version.
     *
     * @param s the string to check
     * @return {@code true} if {@code s} is a keyword or literal, {@code false} otherwise.
     */
    public static boolean isKeyword(CharSequence s) {
	String keywordOrLiteral = s.toString();
	return keywords.contains(keywordOrLiteral);
    }
} 
