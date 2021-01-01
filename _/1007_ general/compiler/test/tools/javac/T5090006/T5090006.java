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

/*
 * @test @(#)T5090006.java	1.5 07/01/18
 * @bug 5090006
 * @summary javac fails with assertion error
 * @author Peter von der Ah\u00e9
 * @run shell compiler.sh T5090006.java
 * @run main T5090006
 */

import stub_tie_gen.wsdl_hello_lit.client.*;
import junit.framework.*;
import testutil.ClientServerTestUtil;
 
public class T5090006 {

    void getStub() throws Exception {
        Hello_PortType_Stub x = null;
        new ClientServerTestUtil().setTransport(x, null, null, null);
    }
 
    public static void main(String[] args) {
	System.out.println("FISK");
    }
}
