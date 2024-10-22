/*
 * @test @(#)MissingSuperRecovery.java	1.4 06/11/01  /nodynamiccopyright/
 * @bug 4332631 4785453
 * @summary Check for proper error recovery when superclass of extended
 * class is no longer available during a subsequent compilation.
 * @author maddox
 *
 * @compile/fail/ref=MissingSuperRecovery.out -XDstdout -XDdiags=%b:%l:%_%m MissingSuperRecovery.java
 */

// Requires "golden" class file 'impl.class', which contains
// a reference to a superclass at a location no longer on the classpath.
// Note that this test should elicit an error, but should not cause a compiler crash.

public class MissingSuperRecovery extends impl {
  private String workdir="";
}
