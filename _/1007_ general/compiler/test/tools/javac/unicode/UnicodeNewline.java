/*
 * @test @(#)UnicodeNewline.java	1.4 06/11/01  /nodynamiccopyright/
 * @bug 4739428 4785453
 * @summary when \u000a is used, diagnostics are reported on the wrong line.
 *
 * @compile/fail/ref=UnicodeNewline.out -XDstdout -XDdiags=%b:%l:%_%m UnicodeNewline.java
 */

class UnicodeNewline {
    // \u000a \u000a \u000a
    xyzzy plugh; // error should be HERE
}
