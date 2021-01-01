/* Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/* @test    @(#)MethodNeg.java	1.2 07/01/02 /nodynamiccopyright/
 * @bug     6192945
 * @summary Declaration order of interdependent generic types should not matter
 * @author  Peter von der Ah\u00e9
 * @compile/fail/ref=MethodNeg.out -XDstdout -XDrawDiagnostics MethodNeg.java
 */

public class MethodNeg {
    <A extends D, D extends E, E extends B, B extends C, C extends C1, C1 extends B> void m(E e, D d) {
        m(e, d);
        m(null, null);
    }
}
