/**
 * @test @(#)Bar.java	1.3 06/11/01  /nodynamiccopyright/
 * @bug 5045412
 * @compile/fail/ref=out -XDstdout -XDrawDiagnostics -Xlint:serial -XDfailcomplete=java.io.Serializable Bar.java 
 */

/**
 * @test @(#)Bar.java	1.3 06/11/01  /nodynamiccopyright/
 * @bug 5045412
 * @compile/fail/ref=out -XDstdout -XDrawDiagnostics -Xlint:serial -XDfailcomplete=java.io.Serializable Bar.java Foo.java
 */

class Bar implements java.io.Serializable { }
