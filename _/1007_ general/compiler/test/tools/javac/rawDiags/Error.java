/*
 * @test @(#)Error.java	1.4 06/11/01  /nodynamiccopyright/ 
 * @bug 6177732
 * @summary add hidden option to have compiler generate diagnostics in more machine-readable form
 * @compile/fail/ref=Error.out -XDrawDiagnostics -XDstdout Error.java
 */
class Error
{
    static void error
