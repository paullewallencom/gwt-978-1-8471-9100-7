/* Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/* @test    @(#)Neg2.java	1.2 07/01/02 /nodynamiccopyright/
 * @bug     6192945
 * @summary  Declaration order of interdependent generic types should not matter
 * @author  Peter von der Ah\u00e9
 * @compile/fail/ref=Neg2.out -XDstdout -XDrawDiagnostics Neg2.java
 */

public class Neg2<A extends D, D extends E, E extends B, B extends C, C extends C1, C1 extends B> {
}
