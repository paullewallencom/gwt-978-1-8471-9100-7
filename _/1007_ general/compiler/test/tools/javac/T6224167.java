/*
 * @test @(#)T6224167.java	1.3 06/11/01  /nodynamiccopyright/
 * @bug 6224167
 * @summary misleading error message when both array and varargs
 *	methods are defined
 * @compile/fail/ref=T6224167.out -XDstdout -XDrawDiagnostics T6224167.java
 */
class T6224167
{
    void printf(String s, Object[] args) { }
    void printf(String s, Object... args) { }
}
