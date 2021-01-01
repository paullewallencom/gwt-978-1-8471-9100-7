/*
 * @test @(#)T4666866.java	1.4 06/11/01  /nodynamiccopyright/
 * @bug 4666866 4785453
 * @summary REGRESSION: Generated error message unhelpful for missing methods
 * @author gafter
 *
 * @compile/fail/ref=T4666866.out -XDstdout -XDdiags=%b:%l:%_%m T4666866.java
 */

class t implements Runnable {}
