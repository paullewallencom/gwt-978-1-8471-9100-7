/*
 * @(#)MandatoryWarningHandler.java	1.11 07/01/18
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

package com.sun.tools.javac.util;

import java.util.HashSet;
import java.util.Set;
import javax.tools.JavaFileObject;

import com.sun.tools.javac.util.JCDiagnostic.DiagnosticPosition;


/**
 * A handler to process mandatory warnings, setting up a deferred diagnostic
 * to be printed at the end of the compilation if some warnings get suppressed
 * because too many warnings have already been generated.
 * 
 * Note that the SuppressWarnings annotation can be used to suppress warnings
 * about conditions that would otherwise merit a warning. Such processing
 * is done when the condition is detected, and in those cases, no call is
 * made on any API to generate a warning at all. In consequence, this handler only
 * gets to handle those warnings that JLS says must be generated.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
@Version("@(#)MandatoryWarningHandler.java	1.11 07/01/18")
public class MandatoryWarningHandler {

    /**
     * The kinds of different deferred diagnostics that might be generated
     * if a mandatory warning is suppressed because too many warnings have
     * already been output.
     * 
     * The parameter is a fragment used to build an I18N message key for Log.
     */
    private enum DeferredDiagnosticKind {
	/** 
	 * This kind is used when a single specific file is found to have warnings
	 * and no similar warnings have already been given. 
	 * It generates a message like:
	 * 	FILE has ISSUES
	 */
	IN_FILE(".filename"),
	/** 
	 * This kind is used when a single specific file is found to have warnings
	 * and when similar warnings have already been reported for the file.
	 * It generates a message like:
	 * 	FILE has additional ISSUES
	 */
	ADDITIONAL_IN_FILE(".filename.additional"),
	/**
	 * This kind is used when multiple files have been found to have warnings,
	 * and none of them have had any similar warnings.
	 * It generates a message like:
	 * 	Some files have ISSUES
	 */
	IN_FILES(".plural"),
	/**
	 * This kind is used when multiple files have been found to have warnings,
	 * and some of them have had already had specific similar warnings.
	 * It generates a message like:
	 * 	Some files have additional ISSUES
	 */
	ADDITIONAL_IN_FILES(".plural.additional");

	DeferredDiagnosticKind(String v) { value = v; }
	String getKey(String prefix) { return prefix + value; }

	private String value;
    }


    /**
     * Create a handler for mandatory warnings.
     * @param log     The log on which to generate any diagnostics
     * @param verbose Specify whether or not detailed messages about
     *                individual instances should be given, or whether an aggregate
     *                message should be generated at the end of the compilation.
     *                Typically set via  -Xlint:option.
     * @param prefix  A common prefix for the set of message keys for
     *                the messages that may be generated.
     */
    public MandatoryWarningHandler(Log log, boolean verbose, String prefix) {
	this.log = log;
	this.verbose = verbose;
	this.prefix = prefix;
    }

    /**
     * Report a mandatory warning.
     */
    public void report(DiagnosticPosition pos, String msg, Object... args) {
	JavaFileObject currentSource = log.currentSource();

	if (verbose) {
	    if (sourcesWithReportedWarnings == null)
		sourcesWithReportedWarnings = new HashSet<JavaFileObject>();

	    if (log.nwarnings < log.MaxWarnings) {
		// generate message and remember the source file
		log.mandatoryWarning(pos, msg, args);
		sourcesWithReportedWarnings.add(currentSource);
	    } else if (deferredDiagnosticKind == null) {
		// set up deferred message
		if (sourcesWithReportedWarnings.contains(currentSource)) {
		    // more errors in a file that already has reported warnings
		    deferredDiagnosticKind = DeferredDiagnosticKind.ADDITIONAL_IN_FILE;
		} else {
		    // warnings in a new source file
		    deferredDiagnosticKind = DeferredDiagnosticKind.IN_FILE;
		}
		deferredDiagnosticSource = currentSource;
		deferredDiagnosticArg = currentSource;
	    } else if ((deferredDiagnosticKind == DeferredDiagnosticKind.IN_FILE
			|| deferredDiagnosticKind == DeferredDiagnosticKind.ADDITIONAL_IN_FILE)
		       && !equal(deferredDiagnosticSource, currentSource)) {
		// additional errors in more than one source file
		deferredDiagnosticKind = DeferredDiagnosticKind.ADDITIONAL_IN_FILES;
		deferredDiagnosticArg = null;
	    }
	} else {
	    if (deferredDiagnosticKind == null) {
		// warnings in a single source
		deferredDiagnosticKind = DeferredDiagnosticKind.IN_FILE;
		deferredDiagnosticSource = currentSource;
 		deferredDiagnosticArg = currentSource;
	    }  else if (deferredDiagnosticKind == DeferredDiagnosticKind.IN_FILE &&
			!equal(deferredDiagnosticSource, currentSource)) {
		// warnings in multiple source files
		deferredDiagnosticKind = DeferredDiagnosticKind.IN_FILES;
		deferredDiagnosticArg = null;
	    }
	}
    }

    /**
     * Report any diagnostic that might have been deferred by previous calls of report().
     */
    public void reportDeferredDiagnostic() {
	if (deferredDiagnosticKind != null) {
	    if (deferredDiagnosticArg == null)
		log.mandatoryNote(deferredDiagnosticSource, deferredDiagnosticKind.getKey(prefix));
	    else
		log.mandatoryNote(deferredDiagnosticSource, deferredDiagnosticKind.getKey(prefix), deferredDiagnosticArg);

	    if (!verbose)
		log.mandatoryNote(deferredDiagnosticSource, prefix + ".recompile");
	}
    }

    /**
     * Check two objects, each possibly null, are either both null or are equal.
     */
    private static boolean equal(Object o1, Object o2) {
	return ((o1 == null || o2 == null) ? (o1 == o2) : o1.equals(o2));
    }

    /**
     * The log to which to report warnings.
     */
    private Log log;

    /**
     * Whether or not to report individual warnings, or simply to report a 
     * single aggregate warning at the end of the compilation.
     */
    private boolean verbose;

    /**
     * The common prefix for all I18N message keys generated by this handler.
     */
    private String prefix;

    /**
     * A set containing the names of the source files for which specific
     * warnings have been generated -- i.e. in verbose mode.  If a source name
     * appears in this list, then deferred diagnostics will be phrased to
     * include "additionally"...
     */
    private Set<JavaFileObject> sourcesWithReportedWarnings;

    /**
     * A variable indicating the latest best guess at what the final
     * deferred diagnostic will be. Initially as specific and helpful
     * as possible, as more warnings are reported, the scope of the
     * diagnostic will be broadened.
     */
    private DeferredDiagnosticKind deferredDiagnosticKind;

    /**
     * If deferredDiagnosticKind is IN_FILE or ADDITIONAL_IN_FILE, this variable
     * gives the value of log.currentSource() for the file in question.
     */
    private JavaFileObject deferredDiagnosticSource;

    /**
     * An optional argument to be used when constructing the 
     * deferred diagnostic message, based on deferredDiagnosticKind.
     * This variable should normally be set/updated whenever
     * deferredDiagnosticKind is updated.
     */
    private Object deferredDiagnosticArg;
}
