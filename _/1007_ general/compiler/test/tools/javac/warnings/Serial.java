/*
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *   
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *   
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *  
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *   
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 *  
 */

/**
 * @test
 * @bug 4986256
 * @compile Serial.java
 * @compile -Xlint:serial Serial.java
 * @compile -Xlint:all Serial.java
 * @compile -Werror Serial.java
 * @compile/fail -Werror -Xlint:serial Serial.java
 * @compile/fail -Werror -Xlint:all,-path T4994049/ Serial.java
 */

import java.io.Serializable;

// control: this class should generate warnings
class Serial implements Serializable
{
    static class Inner implements Serializable
    {
    }

    @SuppressWarnings("serial")
    void m() {
	class Inner implements Serializable
	{
	}
    }
}

// tests: the warnings that would otherwise be generated should all be suppressed
@SuppressWarnings("serial")
class Serial1 implements Serializable
{
    static class Inner implements Serializable
    {
    }
}

class Serial2
{
    @SuppressWarnings("serial")
    static class Inner implements Serializable
    {
    }

    @SuppressWarnings("serial")
    void m() {
	class Inner implements Serializable
	{
	}
    }
}

// this class should produce warnings because @SuppressWarnings should not be inherited
class Serial3 extends Serial1
{
    static class Inner implements Serializable
    {
    }
}
