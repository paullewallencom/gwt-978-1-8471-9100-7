/* Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/* @test    @(#)Neg.java	1.2 07/01/02 /nodynamiccopyright/
 * @bug     6192945
 * @summary  Declaration order of interdependent generic types should not matter
 * @author  Peter von der Ah\u00e9
 * @compile/fail/ref=Neg.out -XDstdout -XDrawDiagnostics Neg.java
 */

public class Neg<A extends B, B extends A> {
}
