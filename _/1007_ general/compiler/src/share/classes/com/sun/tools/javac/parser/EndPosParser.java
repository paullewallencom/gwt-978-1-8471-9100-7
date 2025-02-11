/*
 * @(#)EndPosParser.java	1.9 07/01/18
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

package com.sun.tools.javac.parser;

import java.util.Map;
import java.util.HashMap;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeInfo;
import com.sun.tools.javac.util.Position;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Version;

import static com.sun.tools.javac.tree.JCTree.*;

/**
 * This class is similar to Parser except that it stores ending
 * positions for the tree nodes.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b></p>
 */
@Version("@(#)EndPosParser.java	1.9 07/01/18")
public class EndPosParser extends Parser {

    public EndPosParser(Factory fac, Lexer S, boolean keepDocComments) {
	super(fac, S, keepDocComments);
	this.S = S;
	endPositions = new HashMap<JCTree,Integer>();
    }

    private Lexer S;

    /** A hashtable to store ending positions
     *  of source ranges indexed by the tree nodes.
     *  Defined only if option flag genEndPos is set.
     */
    Map<JCTree, Integer> endPositions;

    /** {@inheritDoc} */
    @Override
    protected void storeEnd(JCTree tree, int endpos) {
        int errorEndPos = getErrorEndPos();
	endPositions.put(tree, errorEndPos > endpos ? errorEndPos : endpos);
    }

    /** {@inheritDoc} */
    @Override
    protected <T extends JCTree> T to(T t) {
	storeEnd(t, S.endPos());
	return t;
    }

    /** {@inheritDoc} */
    @Override
    protected <T extends JCTree> T toP(T t) {
	storeEnd(t, S.prevEndPos());
	return t;
    }

    @Override
    public JCCompilationUnit compilationUnit() {
	JCCompilationUnit t = super.compilationUnit();
	t.endPositions = endPositions;
	return t;
    }

    /** {@inheritDoc} */
    @Override
    JCExpression parExpression() {
	int pos = S.pos();
	JCExpression t = super.parExpression();
	return toP(F.at(pos).Parens(t));
    }

    /** {@inheritDoc} */
    @Override
    public int getEndPos(JCTree tree) {
        return TreeInfo.getEndPos(tree, endPositions);
    }

}
