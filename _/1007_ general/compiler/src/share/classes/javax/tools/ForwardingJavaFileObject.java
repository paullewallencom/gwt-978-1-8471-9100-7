/*
 * @(#)ForwardingJavaFileObject.java	1.11 07/01/18
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

package javax.tools;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;

/**
 * Forwards calls to a given file object.  Subclasses of this class
 * might override some of these methods and might also provide
 * additional fields and methods.
 *
 * @param <F> the kind of file object forwarded to by this object
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public class ForwardingJavaFileObject<F extends JavaFileObject>
    extends ForwardingFileObject<F>
    implements JavaFileObject
{

    /**
     * Creates a new instance of ForwardingJavaFileObject.
     * @param fileObject delegate to this file object
     */
    protected ForwardingJavaFileObject(F fileObject) {
	super(fileObject);
    }

    public Kind getKind() {
        return fileObject.getKind();
    }

    public boolean isNameCompatible(String simpleName, Kind kind) {
        return fileObject.isNameCompatible(simpleName, kind);
    }

    public NestingKind getNestingKind() { return fileObject.getNestingKind(); }

    public Modifier getAccessLevel()  { return fileObject.getAccessLevel(); }

}
