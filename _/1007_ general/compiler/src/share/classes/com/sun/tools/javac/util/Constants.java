/*
 * @(#)Constants.java	1.3 07/01/18
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

import com.sun.tools.javac.code.Type;

import static com.sun.tools.javac.code.TypeTags.*;

/**
 * Utilities for operating on constant values.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 * you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */
@Version("@(#)Constants.java	1.3 07/01/18")
public class Constants {

    /**
     * Converts a constant in internal representation (in which
     * boolean, char, byte, short, and int are each represented by an
     * Integer) into standard representation.  Other values (including
     * null) are returned unchanged.
     */
    public static Object decode(Object value, Type type) {
	if (value instanceof Integer) {
	    int i = (Integer) value;
	    switch (type.tag) {
	    case BOOLEAN:  return i != 0;
	    case CHAR:	   return (char) i;
	    case BYTE:	   return (byte) i;
	    case SHORT:	   return (short) i;
	    }
	}
	return value;
    }

    /**
     * Returns a string representation of a constant value (given in
     * internal representation), quoted and formatted as in Java source.
     */
    public static String format(Object value, Type type) {
	value = decode(value, type);
	switch (type.tag) {
	case BYTE:	return formatByte((Byte) value);
	case LONG:	return formatLong((Long) value);
	case FLOAT:	return formatFloat((Float) value);
	case DOUBLE:	return formatDouble((Double) value);
	case CHAR:	return formatChar((Character) value);
	}
	if (value instanceof String)
	    return formatString((String) value);
	return value + "";
    }

    /**
     * Returns a string representation of a constant value (given in
     * standard wrapped representation), quoted and formatted as in
     * Java source.
     */
    public static String format(Object value) {
	if (value instanceof Byte)	return formatByte((Byte) value);
	if (value instanceof Long)	return formatLong((Long) value);
	if (value instanceof Float)	return formatFloat((Float) value);
	if (value instanceof Double)	return formatDouble((Double) value);
	if (value instanceof Character)	return formatChar((Character) value);
	if (value instanceof String)	return formatString((String) value);
	return value + "";
    }

    private static String formatByte(byte b) {
	return String.format("0x%02x", b);
    }

    private static String formatLong(long lng) {
	return lng + "L";
    }

    private static String formatFloat(float f) {
	if (Float.isNaN(f))
	    return "0.0f/0.0f";
	else if (Float.isInfinite(f))
	    return (f < 0) ? "-1.0f/0.0f" : "1.0f/0.0f";
	else
	    return f + "f";
    }

    private static String formatDouble(double d) {
	if (Double.isNaN(d))
	    return "0.0/0.0";
	else if (Double.isInfinite(d))
	    return (d < 0) ? "-1.0/0.0" : "1.0/0.0";
	else
	    return d + "";
    }

    private static String formatChar(char c) {
	return '\'' + Convert.quote(c) + '\'';
    }

    private static String formatString(String s) {
	return '"' + Convert.quote(s) + '"';
    }
}
