/*
 * @test @(#)T5048776.java	1.3 06/11/01  /nodynamiccopyright/
 * @bug 5048776
 * @compile/ref=T5048776a.out -XDstdout -XDrawDiagnostics                  T5048776.java
 * @compile/ref=T5048776b.out -XDstdout -XDrawDiagnostics -Xlint:all,-path T5048776.java
 */
class A1 {
    void foo(Object[] args) { }
}

class A1a extends A1 {
    void foo(Object... args) { }
}

class A2 {
    void foo(Object... args) { }
}

class A2a extends A2 {
    void foo(Object[] args) { }
}
