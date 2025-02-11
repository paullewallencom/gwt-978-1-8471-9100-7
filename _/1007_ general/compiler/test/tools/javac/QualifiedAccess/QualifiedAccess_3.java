/**
 * @test @(#)QualifiedAccess_3.java	1.10 06/11/01  /nodynamiccopyright/
 * @bug 4094658 4785453
 * @summary Test enforcement of JLS 6.6.1 and 6.6.2 rules requiring that
 * the type to which a component member belongs be accessible in qualified
 * names.
 *
 * @run shell QualifiedAccess_3.sh
 */

import pack1.P1;

class CMain {

    class Foo {
        class Bar {}
    }

    static class Baz {
        private static class Quux {
            static class Quem {}
        }
    }

    // These are all OK.
    CMain z = new CMain();
    Foo x = z.new Foo();
    Foo.Bar y = x.new Bar();

    void test() {
        P1 p1 = new P1();

        // These are NOT errors, and should NOT be detected, as observed.
        /*------------------------------------*
        Baz.Quux z = null;
        Baz.Quux.Quem y = null;
        *------------------------------------*/

        P1.Foo.Bar x = null;            // ERROR - 'P1.Foo' not accessible
        int i = p1.a.length;            // ERROR - Type of 'a' not accessible
        // The type of the expression from which a component
        // is selected must be accessible.
        p1.p2.privatei = 3;             // ERROR - Type of 'p1.p2' not accessible.
        System.out.println (p1.p2.privatei);    // ERROR - Type of 'p1.p2' not accessible.
    }

}
