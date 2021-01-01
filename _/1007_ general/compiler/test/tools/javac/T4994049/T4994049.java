/*
 * @test    @(#)T4994049.java	1.4 06/11/01  /nodynamiccopyright/
 * @bug     4994049
 * @summary Improved diagnostics while parsing enums
 * @author  Peter von der Ah\u00e9
 * @compile/fail/ref=T4994049.out -XDstdout -XDrawDiagnostics T4994049.java
 */

public enum T4994049 {
    FOO {
    }
 
    BAR;
}
