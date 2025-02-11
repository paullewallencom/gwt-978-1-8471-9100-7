/*
 * @test @(#)T6406771.java	1.5 06/11/01  /nodynamiccopyright/
 * @bug 6406771
 * @summary CompilationUnitTree needs access to a line map
 */

// WARNING: White-space and layout is important in this file, especially tab characters.

import java.io.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.*;
import com.sun.tools.javac.api.*;
import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.tree.JCTree;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("*")
public class T6406771 extends AbstractProcessor {
    String[] tests = {
	"line:24",
	"line:25",
	"line:26", "line:26",
//       1         2         3         4         5         6
//3456789012345678901234567890123456789012345678901234567890
      "col:7", "col:16", "col:26",                 // this line uses spaces
	"col:9",	"col:25",	"col:41",  // this line uses tabs
		   "col:20",	  	  "col:43" // this line uses a mixture
    };

    // White-space after this point does not matter

    public static void main(String[] args) {
	String self = T6406771.class.getName();
	String testSrc = System.getProperty("test.src");
	String testClasses = System.getProperty("test.classes");

	JavacTool tool = JavacTool.create();
	StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null);
	JavaFileObject f = fm.getJavaFileObjectsFromFiles(Arrays.asList(new File(testSrc, self+".java"))).iterator().next();

	List<String> opts = Arrays.asList("-d", ".", "-processorpath", testClasses, "-processor", self, "-proc:only");

	JavacTask task = tool.getTask(null, fm, null, opts, null, Arrays.asList(f));

	if (!task.call())
	    throw new AssertionError("failed");
    }

    public boolean process(Set<? extends TypeElement> elems, RoundEnvironment rEnv) {
	final String LINE = "line" + ':';   // avoid matching this string
	final String COLUMN  = "col" + ':';
	final Messager messager = processingEnv.getMessager();
	final Trees trees = Trees.instance(processingEnv);

	TreeScanner<Void,LineMap> s = new  TreeScanner<Void,LineMap>() {
	    public Void visitLiteral(LiteralTree tree, LineMap lineMap) {
		if (tree.getKind() == Tree.Kind.STRING_LITERAL) {
		    String s = (String) tree.getValue();
		    int pos = ((JCTree) tree).pos; // can't get through public api, bug 6412669 filed
		    String prefix;
		    long found;
		    if (s.startsWith(LINE)) {
			prefix = LINE;
			found = lineMap.getLineNumber(pos);
		    }
		    else if (s.startsWith(COLUMN)) {
			prefix = COLUMN;
			found = lineMap.getColumnNumber(pos);
		    }
		    else
			return null; 
		    int expect = Integer.parseInt(s.substring(prefix.length()));
		    if (expect != found) {
			messager.printMessage(Diagnostic.Kind.ERROR,
					      "Error: " + prefix + " pos=" + pos 
					      + " expect=" + expect + " found=" + found);
		    }
		}
		return null;
	    }
	};

	for (Element e: rEnv.getRootElements()) {
	    System.err.println(e);
	    Tree t = trees.getTree(e);
	    TreePath p = trees.getPath(e);
	    s.scan(p.getLeaf(), p.getCompilationUnit().getLineMap());

	}

	return true;
    }

}
