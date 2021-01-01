/* Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/* @test    @(#)Neg3.java	1.2 07/01/02 /nodynamiccopyright/
 * @bug     6192945
 * @summary  Declaration order of interdependent generic types should not matter
 * @author  Peter von der Ah\u00e9
 * @compile/fail/ref=Neg3.out -XDstdout -XDrawDiagnostics Neg3.java
 */

public class Neg3<A extends B & C, B extends A, C extends A> {
}
