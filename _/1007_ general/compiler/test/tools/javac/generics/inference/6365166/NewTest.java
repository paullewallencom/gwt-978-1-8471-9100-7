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
 * @test    @(#)NewTest.java	1.5 07/01/18
 * @bug     6365166
 * @summary javac (generic) unable to resolve methods
 * @author  mike.skells@validsoft.com
 * @ignore  waiting for 6365166
 * @compile NewTest.java
 */

import java.util.*;

public class NewTest<A,B> {
    private List<A> toAdd;

    public NewTest(List<A> toAdd) {
        this.toAdd = toAdd;
    }

    private List<A> getRelated(B b) {
        //some application logic
        //for demo
        return toAdd;
    }

    @SuppressWarnings("unchecked")
    public <L extends List<? super A>,LF extends Factory<L>> L addOrCreate4(B b,L l,LF lf) {
        if (l == null) {
            l = lf.create();
        }
        ((List<? super A>)l).addAll(getRelated(b)); //to get round the compiler bug
        return l;
    }

    public static class ListFactory<T>  implements Factory<List<T>>{
        public List<T> create() {
            return new ArrayList<T>();
        }
    }
    public static interface Factory<T> {
        public T create();
    }

    public static void main(String ... args) {
        ListFactory<Number> lf = new ListFactory<Number>();
        List<Long> longs = new ArrayList<Long>();
        longs.add(new Long(1));
        NewTest<Long,Number> test = new NewTest<Long,Number>(longs);

        List<Number> ret4 = null;

        ret4 = test.addOrCreate4(1, ret4,lf);

    }
}
