/**
 * @test    @(#)T6384542a.java	1.2 06/11/01  /nodynamiccopyright/
 * @bug     6384542
 * @summary crash: test/tools/javac/versions/check.sh
 * @author  Peter von der Ah\u00e9
 * @compile/fail -source 5   T6384542a.java
 * @compile      -source 1.4 T6384542a.java
 * @compile/fail/ref=T6384542a_5.out -source 5   -XDstdout -XDrawDiagnostics T6384542a.java
 * @compile/ref=T6384542a_1_4.out    -source 1.4 -XDstdout -XDrawDiagnostics T6384542a.java
 */

public class T6384542a {
    T6384542a enum = null;
}
