/*
 * @(#)Context.java	1.23 07/01/18
 * 
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *  
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
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
 */

package com.sun.tools.javac.util;

import com.sun.tools.javac.Main;
import java.util.*;

/**
 * Support for an abstract context, modelled loosely after ThreadLocal
 * but using a user-provided context instead of the current thread.
 *
 * <p>Within the compiler, a single Context is used for each
 * invocation of the compiler.  The context is then used to ensure a
 * single copy of each compiler phase exists per compiler invocation.
 *
 * <p>The context can be used to assist in extending the compiler by
 * extending its components.  To do that, the extended component must
 * be registered before the base component.  We break initialization
 * cycles by (1) registering a factory for the component rather than
 * the component itself, and (2) a convention for a pattern of usage
 * in which each base component registers itself by calling an
 * instance method that is overridden in extended components.  A base
 * phase supporting extension would look something like this:
 *
 * <p><pre>
 * public class Phase {
 *     protected static final Context.Key<Phase> phaseKey =
 *	   new Context.Key<Phase>();
 *
 *     public static Phase instance(Context context) {
 *	   Phase instance = context.get(phaseKey);
 *	   if (instance == null)
 *	       // the phase has not been overridden
 *	       instance = new Phase(context);
 *	   return instance;
 *     }
 *
 *     protected Phase(Context context) {
 *	   context.put(phaseKey, this);
 *	   // other intitialization follows...
 *     }
 * }
 * </pre>
 *
 * <p>In the compiler, we simply use Phase.instance(context) to get
 * the reference to the phase.  But in extensions of the compiler, we
 * must register extensions of the phases to replace the base phase,
 * and this must be done before any reference to the phase is accessed
 * using Phase.instance().  An extended phase might be declared thus:
 *
 * <p><pre>
 * public class NewPhase extends Phase {
 *     protected NewPhase(Context context) {
 *	   super(context);
 *     }
 *     public static void preRegister(final Context context) {
 *         context.put(phaseKey, new Context.Factory<Phase>() {
 *	       public Phase make() {
 *		   return new NewPhase(context);
 *	       }
 *         });
 *     }
 * }
 * </pre>
 *
 * <p>And is registered early in the extended compiler like this
 *
 * <p><pre>
 *     NewPhase.preRegister(context);
 * </pre>
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
@Version("@(#)Context.java	1.23 07/01/18")
public class Context {
    /** The client creates an instance of this class for each key.
     */
    public static class Key<T> {
	// note: we inherit identity equality from Object.
    }

    /**
     * The client can register a factory for lazy creation of the
     * instance.
     */
    public static interface Factory<T> {
	T make();
    };

    /**
     * The underlying map storing the data.
     * We maintain the invariant that this table contains only
     * mappings of the form
     * Key<T> -> T or Key<T> -> Factory<T> */
    private Map<Key,Object> ht = new HashMap<Key,Object>();

    /** Set the factory for the key in this context. */
    public <T> void put(Key<T> key, Factory<T> fac) {
	checkState(ht);
	Object old = ht.put(key, fac);
	if (old != null)
	    throw new AssertionError("duplicate context value");
    }

    /** Set the value for the key in this context. */
    public <T> void put(Key<T> key, T data) {
	if (data instanceof Factory)
	    throw new AssertionError("T extends Context.Factory");
	checkState(ht);
	Object old = ht.put(key, data);
	if (old != null && !(old instanceof Factory) && old != data && data != null)
	    throw new AssertionError("duplicate context value");
    }

    /** Get the value for the key in this context. */
    public <T> T get(Key<T> key) {
	checkState(ht);
	Object o = ht.get(key);
	if (o instanceof Factory) {
	    Factory fac = (Factory)o;
	    o = fac.make();
	    if (o instanceof Factory)
		throw new AssertionError("T extends Context.Factory");
	    assert ht.get(key) == o;
	}

	/* The following cast can't fail unless there was
	 * cheating elsewhere, because of the invariant on ht.
	 * Since we found a key of type Key<T>, the value must
	 * be of type T.
	 */
	return Context.<T>uncheckedCast(o);
    }

    public Context() {}

    private Map<Class<?>, Key<?>> kt = new HashMap<Class<?>, Key<?>>();
    
    private <T> Key<T> key(Class<T> clss) {
	checkState(kt);
	Key<T> k = uncheckedCast(kt.get(clss));
	if (k == null) {
	    k = new Key<T>();
	    kt.put(clss, k);
	}
	return k;
    }

    public <T> T get(Class<T> clazz) {
	return get(key(clazz));
    }

    public <T> void put(Class<T> clazz, T data) {
	put(key(clazz), data);
    }
    public <T> void put(Class<T> clazz, Factory<T> fac) {
	put(key(clazz), fac);
    }

    /**
     * TODO: This method should be removed and Context should be made type safe.
     * This can be accomplished by using class literals as type tokens.
     */
    @SuppressWarnings("unchecked")
    private static <T> T uncheckedCast(Object o) {
        return (T)o;
    }

    public void dump() {
	for (Object value : ht.values())
	    System.err.println(value == null ? null : value.getClass());
    }

    public void clear() {
	ht = null;
	kt = null;
    }
    
    private static void checkState(Map<?,?> t) {
	if (t == null)
	    throw new IllegalStateException();
    }
}
