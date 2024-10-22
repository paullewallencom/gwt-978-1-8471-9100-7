/*
 * @test    @(#)T6425594.java	1.2 06/11/01  /nodynamiccopyright/
 * @bug     6424491
 * @summary javac accepts illegal forward references
 * @author  Peter von der Ah\u00e9
 * @compile/fail/ref=T6425594.out -XDstdout -XDrawDiagnostics -XDuseBeforeDeclarationWarning T6425594.java
 */

public class T6425594 {
    static int x = T6425594.x;
    static final int y = z;
    static final int z = 0;
}
