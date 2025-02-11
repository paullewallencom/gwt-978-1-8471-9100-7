/*
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *   
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
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
 *  
 */

// @(#)CheckClassFileVersion.java	1.4 07/01/18

import java.io.*;
import java.nio.*;
import java.nio.channels.*;


public class CheckClassFileVersion {

    static String get(String fn) throws IOException {
	ByteBuffer bb = ByteBuffer.allocate(1024);
	FileChannel fc = new FileInputStream(fn).getChannel();
	try {
	    bb.clear();
	    if (fc.read(bb) < 0)
		throw new IOException("Could not read any bytes");
	    bb.flip();
	    int minor = bb.getShort(4);
	    int major = bb.getShort(6);
	    return major + "." + minor;
	} finally {
	    fc.close();
	}
    }

    public static void main(String[] args) throws IOException {
	String cfv = get(args[0]);
	if (args.length == 1) {
	    System.out.println(cfv);
	    return;
	}
	if (!cfv.equals(args[1])) {
	    System.err.println("Class-file version mismatch: Expected "
			       + args[1] + ", got " + cfv);
	    System.exit(1);
	}
    }

}
