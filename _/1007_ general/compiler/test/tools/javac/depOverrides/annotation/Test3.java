/*
 * @test @(#)Test3.java	1.3 06/11/01  /nodynamiccopyright/
 * @bug 5086088
 * @summary check warnings generated when overriding deprecated methods
 *
 * @compile/ref=Test3.out -XDstdout -XDrawDiagnostics -Xlint:deprecation Test3.java
 */

interface LibInterface {
    @Deprecated
	void m();
}

class LibClass {
    public void m() { }
}

class Test3 extends LibClass implements LibInterface {
}
