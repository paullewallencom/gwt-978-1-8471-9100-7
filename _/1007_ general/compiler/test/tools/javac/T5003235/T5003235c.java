/*
 * @test    @(#)T5003235c.java	1.4 06/11/01  /nodynamiccopyright/
 * @bug     5003235
 * @summary Access to private inner classes
 * @author  Peter von der Ah\u00e9
 * @compile/fail/ref=T5003235c.out -XDstdout -XDdiags=%b:%l:%_%m T5003235c.java
 */

class T5003235c {
    private static class B {
        static class Inner {}
    }
}

class C extends T5003235c.B.Inner {}
