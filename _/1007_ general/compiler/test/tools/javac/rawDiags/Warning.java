/*
 * @test @(#)Warning.java	1.4 06/11/01  /nodynamiccopyright/
 * @bug 6177732
 * @summary add hidden option to have compiler generate diagnostics in more machine-readable form
 * @compile/ref=Warning.out -XDrawDiagnostics -XDstdout -Xlint:unchecked Warning.java
 */

import java.util.HashSet;
import java.util.Set;

class Warning
{
    static void useUnchecked() {
	Set s = new HashSet<String>();
	s.add("abc");
    }
}
