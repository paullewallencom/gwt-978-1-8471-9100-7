/*
 * @test    @(#)T6491592.java	1.1 06/11/08 /nodynamiccopyright/
 * @bug     6491592
 * @summary Compiler crashes on assignment operator
 * @author  alex.buckley@sun.com
 * @compile/fail/ref=T6491592.out -XDstdout -XDrawDiagnostics T6491592.java
 */

public class T6491592 {
    public static void main(String... args) {
        Object o = null;
        o += null;
    }
}
