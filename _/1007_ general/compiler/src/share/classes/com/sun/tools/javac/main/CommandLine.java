/*
 * @(#)CommandLine.java	1.18 07/01/18
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

package com.sun.tools.javac.main;

import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.StreamTokenizer;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Version;

/**
 * Various utility methods for processing Java tool command line arguments.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
@Version("@(#)CommandLine.java	1.18 07/01/18")
public class CommandLine {
    /**
     * Process Win32-style command files for the specified command line
     * arguments and return the resulting arguments. A command file argument
     * is of the form '@file' where 'file' is the name of the file whose
     * contents are to be parsed for additional arguments. The contents of
     * the command file are parsed using StreamTokenizer and the original
     * '@file' argument replaced with the resulting tokens. Recursive command
     * files are not supported. The '@' character itself can be quoted with
     * the sequence '@@'.
     */
    public static String[] parse(String[] args)
	throws IOException
    {
	ListBuffer<String> newArgs = new ListBuffer<String>();
	for (int i = 0; i < args.length; i++) {
	    String arg = args[i];
	    if (arg.length() > 1 && arg.charAt(0) == '@') {
		arg = arg.substring(1);
		if (arg.charAt(0) == '@') {
		    newArgs.append(arg);
		} else {
		    loadCmdFile(arg, newArgs);
		}
	    } else {
		newArgs.append(arg);
	    }
	}
	return newArgs.toList().toArray(new String[newArgs.length()]);
    }

    private static void loadCmdFile(String name, ListBuffer<String> args)
	throws IOException
    {
	Reader r = new BufferedReader(new FileReader(name));
	StreamTokenizer st = new StreamTokenizer(r);
	st.resetSyntax();
	st.wordChars(' ', 255);
	st.whitespaceChars(0, ' ');
	st.commentChar('#');
	st.quoteChar('"');
	st.quoteChar('\'');
	while (st.nextToken() != st.TT_EOF) {
	    args.append(st.sval);
	}
	r.close();
    }
}
