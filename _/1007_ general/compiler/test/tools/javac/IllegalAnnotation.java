/**
 * @test @(#)IllegalAnnotation.java	1.3 06/11/01  /nodynamiccopyright/
 * @bug 5012028 6384539
 * @summary javac crash when declare an annotation type illegally
 *
 * @compile/fail IllegalAnnotation.java
 * @compile/fail/ref=IllegalAnnotation.out -XDdev -XDrawDiagnostics -XDstdout IllegalAnnotation.java
 */
class IllegalAnnotation {
    {
        @interface SomeAnnotation { }
    }
}
