/*
 * @test    @(#)T4093617.java	1.5 06/11/01  /nodynamiccopyright/
 * @bug     4093617
 * @summary Object has no superclass
 * @author  Peter von der Ah\u00e9
 * @compile/fail/ref=T4093617.out -XDstdout -XDdiags=%b:%l:%_%m T4093617.java
 */

package java.lang;

class Object {
    Object() { super(); }
}
