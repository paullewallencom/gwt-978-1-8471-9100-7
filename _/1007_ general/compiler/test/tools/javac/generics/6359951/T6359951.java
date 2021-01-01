/**
 * @test    @(#)T6359951.java	1.2 06/11/01  /nodynamiccopyright/
 * @bug     6359951
 * @summary Crash when using class field
 * @author  fabrice@derepas.com
 * @author  Peter von der Ah\u00e9
 * @compile/fail/ref=T6359951.out -XDstdout -XDrawDiagnostics T6359951.java
 */

public class T6359951 {
    public static String x;
    public static class C {}
    class D<T> { }
    class E<T> extends D<T.classOfT> {}
    class F<T extends T6359951> extends D<T.x> {}
    class G<T extends T6359951> extends D<T.C> {}
}
