/*
 * @test @(#)OverridePosition.java	1.4 06/11/01  /nodynamiccopyright/
 * @bug 4524388 4785453
 * @summary "attemping to assign weaker access" message doesn't give method line number
 * @author Neal Gafter
 *
 * @compile/fail/ref=OverridePosition.out -XDstdout -XDdiags=%b:%l:%_%m OverridePosition.java
 */

package T4524388;

interface Interface {
    void method();
}

class Testa implements Interface {
    void method() {}
}

class A {
    void method() {}
}

class B extends A implements Interface {
}
