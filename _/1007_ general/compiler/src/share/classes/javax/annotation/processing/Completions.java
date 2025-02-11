/*
 * @(#)Completions.java	1.4 07/01/18
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

import java.util.Arrays;

/**
 * Utility class for assembling {@link Completion} objects.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version 1.4 07/01/18
 * @since 1.6
 */
public class Completions {
    // No instances for you.
    private Completions() {}

    private static class SimpleCompletion implements Completion {
	private String value;
	private String message;

	SimpleCompletion(String value, String message) {
	    if (value == null || message == null)
		throw new NullPointerException("Null completion strings not accepted.");
	    this.value = value;
	    this.message = message;
	}

	public String getValue() {
	    return value;
	}


	public String getMessage() {
	    return message;
	}

	@Override
	public String toString() {
	    return "[\"" + value + "\", \"" + message + "\"]";
	}
	// Default equals and hashCode are fine.
    }

    /**
     * Returns a completion of the value and message.
     *
     * @param value the text of the completion
     * @param message a message about the completion
     * @return a completion of the provided value and message
     */
    public static Completion of(String value, String message) {
	return new SimpleCompletion(value, message);
    }

    /**
     * Returns a completion of the value and an empty message
     *
     * @param value the text of the completion
     * @return a completion of the value and an empty message
     */
    public static Completion of(String value) {
	return new SimpleCompletion(value, "");
    }
}
