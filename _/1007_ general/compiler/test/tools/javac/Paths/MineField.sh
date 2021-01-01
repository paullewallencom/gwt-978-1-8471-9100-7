#!/bin/sh
#
# @test @(#)MineField.sh	1.3 03/10/31
# @bug 4758537 4809833
# @summary Test that javac and java find files in similar ways
# @author Martin Buchholz
#
# @run shell/timeout=600 MineField.sh

# To run this test manually, simply do ./MineField.sh

#----------------------------------------------------------------
# The search order for classes used by both java and javac is:
#
# -Xbootclasspath/p:<path>
# -endorseddirs <dirs> or -Djava.endorsed.dirs=<dirs> (search for jar/zip only)
# -bootclasspath <path> or -Xbootclasspath:<path>
# -Xbootclasspath/a:<path>
# -extdirs <dirs> or -Djava.ext.dirs=<dirs> (search for jar/zip only)
# -classpath <path>, -cp <path>, env CLASSPATH=<path>
#
# Peculiarities of the class file search:
# - Empty elements of the (user) classpath default to ".",
#   while empty elements of other paths are ignored.
# - Only for the user classpath is an empty string value equivalent to "."
# - Specifying a bootclasspath on the command line obliterates any
#   previous -Xbootclasspath/p: or -Xbootclasspath/a: command line flags.
#----------------------------------------------------------------

. ${TESTSRC-.}/Util.sh

set -u

BCP=`DefaultBootClassPath`

#----------------------------------------------------------------
# Prepare the "Minefield"
#----------------------------------------------------------------
Cleanup() {
    Sys rm -rf GooSrc GooJar GooZip GooClass
    Sys rm -rf BadSrc BadJar BadZip BadClass
    Sys rm -rf OneDir *.class Main.java MANIFEST.MF
}

Cleanup
Sys mkdir  GooSrc GooJar GooZip GooClass
Sys mkdir  BadSrc BadJar BadZip BadClass

echo 'public class Lib {public static void f(){}}' > Lib.java
Sys "$javac" Lib.java
Sys "$jar" cf GooJar/Lib.jar Lib.class
Sys "$jar" cf GooZip/Lib.zip Lib.class
Sys mv Lib.class GooClass/.
Sys mv Lib.java GooSrc/.
CheckFiles GooZip/Lib.zip GooJar/Lib.jar GooSrc/Lib.java

echo 'public class Lib {/* Bad */}' > Lib.java
Sys "$javac" Lib.java
Sys "$jar" cf BadJar/Lib.jar Lib.class
Sys "$jar" cf BadZip/Lib.zip Lib.class
Sys mv Lib.class BadClass/.
Sys mv Lib.java BadSrc/.
CheckFiles BadZip/Lib.zip BadJar/Lib.jar BadSrc/Lib.java

echo 'public class Main {public static void main(String[] a) {Lib.f();}}' > Main.java

#----------------------------------------------------------------
# Verify that javac class search order is the same as java's
#----------------------------------------------------------------
Failure "$javac" \
    -Xbootclasspath/p:"BadClass" \
    -J-Djava.endorsed.dirs="GooJar" \
    Main.java
Success "$javac" \
    -Xbootclasspath/p:"GooClass${PS}BadJar/Lib.jar" \
    -J-Djava.endorsed.dirs="BadJar${PS}GooZip" \
    Main.java
Failure "$java" \
    -Xbootclasspath/p:"BadClass" \
    -Djava.endorsed.dirs="GooJar" \
    Main
Success "$java" \
    -Xbootclasspath/p:"GooClass${PS}BadJar/Lib.jar" \
    -Djava.endorsed.dirs="BadJar${PS}GooZip" \
    Main

#----------------------------------------------------------------
Failure "$javac" \
    -Xbootclasspath/p:"GooClass" \
    -bootclasspath "$BCP${PS}BadZip/Lib.zip" \
    Main.java
Success "$javac" \
    -Xbootclasspath/p:"BadClass${PS}GooClass" \
    -bootclasspath "$BCP${PS}GooZip/Lib.zip${PS}BadClass" \
    Main.java
Success "$javac" \
    -Xbootclasspath/p:"BadJar/Lib.jar" \
    -Xbootclasspath:"$BCP${PS}GooClass" \
    Main.java
Failure "$java" \
    -Xbootclasspath/p:"GooClass" \
    -Xbootclasspath:"$BCP${PS}BadZip/Lib.zip" \
    Main
Success "$java" \
    -Xbootclasspath/p:"BadClass${PS}GooClass" \
    -Xbootclasspath:"$BCP${PS}GooZip/Lib.zip${PS}BadClass" \
    Main

#----------------------------------------------------------------
Failure "$javac" \
    -bootclasspath "$BCP${PS}GooZip/Lib.zip" \
    -Xbootclasspath/p:"BadClass" \
    Main.java
Success "$javac" \
    -bootclasspath "$BCP${PS}BadZip/Lib.zip" \
    -Xbootclasspath/p:"GooClass${PS}BadJar/Lib.jar" \
    Main.java
Failure "$java" \
    -Xbootclasspath:"$BCP${PS}GooClass" \
    -Xbootclasspath/p:"BadClass" \
    Main
Success "$java" \
    -Xbootclasspath:"$BCP${PS}BadClass" \
    -Xbootclasspath/p:"GooClass${PS}BadJar/Lib.jar" \
    Main

#----------------------------------------------------------------
Failure "$javac" \
    -Xbootclasspath/p:"BadClass" \
    -Xbootclasspath/a:"GooClass" \
    Main.java
Success "$javac" \
    -Xbootclasspath/p:"GooClass${PS}BadClass" \
    -Xbootclasspath/a:"BadClass" \
    Main.java
Failure "$java" \
    -Xbootclasspath/p:"BadClass" \
    -Xbootclasspath/a:"GooClass" \
    Main
Success "$java" \
    -Xbootclasspath/p:"GooClass${PS}BadClass" \
    -Xbootclasspath/a:"BadClass" \
    Main

#----------------------------------------------------------------
Failure "$javac" \
    -Xbootclasspath/p:"GooClass" \
    -J-Djava.endorsed.dirs="BadZip" \
    -bootclasspath "GooClass${PS}$BCP" \
    Main.java
Success "$javac" \
    -Xbootclasspath/p:"BadClass" \
    -J-Djava.endorsed.dirs="BadClass${PS}GooZip${PS}BadJar" \
    -bootclasspath "BadClass${PS}$BCP" \
    Main.java
Failure "$java" \
    -Xbootclasspath/p:"GooClass" \
    -Djava.endorsed.dirs="BadZip" \
    -Xbootclasspath:"GooClass${PS}$BCP" \
    Main
Success "$java" \
    -Xbootclasspath/p:"BadClass" \
    -Djava.endorsed.dirs="BadClass${PS}GooZip${PS}BadJar" \
    -Xbootclasspath:"BadClass${PS}$BCP" \
    Main

#----------------------------------------------------------------
Failure "$javac" \
    -Xbootclasspath/p:"GooClass" \
    -Xbootclasspath:"BadClass${PS}$BCP" \
    -Xbootclasspath/a:"GooClass" \
    Main.java
Success "$javac" \
    -Xbootclasspath/p:"BadClass" \
    -Xbootclasspath:"GooClass${PS}BadClass${PS}$BCP" \
    -Xbootclasspath/a:"BadClass" \
    Main.java
Failure "$java" \
    -Xbootclasspath/p:"GooClass" \
    -Xbootclasspath:"BadClass${PS}$BCP" \
    -Xbootclasspath/a:"GooClass" \
    Main
Success "$java" \
    -Xbootclasspath/p:"BadClass" \
    -Xbootclasspath:"GooClass${PS}BadClass${PS}$BCP" \
    -Xbootclasspath/a:"BadClass" \
    Main

#----------------------------------------------------------------
Failure "$javac" \
    -J-Djava.endorsed.dirs="BadZip" \
    -Xbootclasspath:"GooClass${PS}$BCP" \
    Main.java
Success "$javac" \
    -endorseddirs "BadClass${PS}GooZip${PS}BadJar" \
    -Xbootclasspath:"BadClass${PS}$BCP" \
    Main.java
Success "$javac" \
    -Djava.endorsed.dirs="BadClass${PS}GooZip${PS}BadJar" \
    -Xbootclasspath:"BadClass${PS}$BCP" \
    Main.java
Success "$javac" \
    -J-Djava.endorsed.dirs="BadClass${PS}GooZip${PS}BadJar" \
    -Xbootclasspath:"BadClass${PS}$BCP" \
    Main.java
Failure "$java" \
    -Djava.endorsed.dirs="BadZip" \
    -Xbootclasspath:"GooClass${PS}$BCP" \
    Main
Success "$java" \
    -Djava.endorsed.dirs="BadClass${PS}GooZip${PS}BadJar" \
    -Xbootclasspath:"BadClass${PS}$BCP" \
    Main

#----------------------------------------------------------------
Failure "$javac" \
    -Xbootclasspath/a:"BadClass" \
    -extdirs "GooZip" \
    Main.java
Success "$javac" \
    -Xbootclasspath/a:"GooClass${PS}BadClass" \
    -extdirs "BadZip" \
    Main.java
Failure "$java" \
    -Xbootclasspath/a:"BadClass" \
    -Djava.ext.dirs="GooZip" \
    Main
Success "$java" \
    -Xbootclasspath/a:"GooClass${PS}BadClass" \
    -Djava.ext.dirs="BadZip" \
    Main

#----------------------------------------------------------------
Failure "$javac" \
    -bootclasspath "$BCP${PS}BadJar/Lib.jar" \
    -J-Djava.ext.dir="GooJar" \
    Main.java
Success "$javac" \
    -bootclasspath "$BCP${PS}GooJar/Lib.jar${PS}BadClass" \
    -J-Djava.ext.dir="BadJar" \
    Main.java
Failure "$java" \
    -Xbootclasspath:"$BCP${PS}BadJar/Lib.jar" \
    -Djava.ext.dirs="GooJar" \
    Main
Success "$java" \
    -Xbootclasspath:"$BCP${PS}GooJar/Lib.jar${PS}BadClass" \
    -Djava.ext.dirs="BadJar" \
    Main

#----------------------------------------------------------------
Failure "$javac" \
    -extdirs "GooClass${PS}BadZip" \
    -cp "GooZip/Lib.zip" \
    Main.java
Success "$javac" \
    -extdirs "BadClass${PS}GooZip${PS}BadJar" \
    -cp "BadZip/Lib.zip" \
    Main.java
Success "$javac" \
    -Djava.ext.dirs="GooZip${PS}BadJar" \
    -classpath "BadZip/Lib.zip" \
    Main.java
Success "$javac" \
    -J-Djava.ext.dirs="GooZip${PS}BadJar" \
    -classpath "BadZip/Lib.zip" \
    Main.java
Failure "$java" \
    -Djava.ext.dirs="GooClass${PS}BadZip" \
    -cp "GooZip/Lib.zip${PS}." \
    Main
Success "$java" \
    -Djava.ext.dirs="GooZip${PS}BadJar" \
    -cp "BadZip/Lib.zip${PS}." \
    Main

#----------------------------------------------------------------
Failure "$javac" -classpath "BadClass${PS}GooClass" Main.java
Success "$javac" -classpath "GooClass${PS}BadClass" Main.java
Failure "$java"  -classpath "BadClass${PS}GooClass${PS}." Main
Success "$java"  -classpath "GooClass${PS}BadClass${PS}." Main

Failure "$javac" -cp "BadJar/Lib.jar${PS}GooZip/Lib.zip" Main.java
Success "$javac" -cp "GooJar/Lib.jar${PS}BadZip/Lib.zip" Main.java
Failure "$java"  -cp "BadJar/Lib.jar${PS}${PS}GooZip/Lib.zip" Main
Success "$java"  -cp "GooJar/Lib.jar${PS}${PS}BadZip/Lib.zip" Main

Failure env CLASSPATH="BadZip/Lib.zip${PS}GooJar/Lib.jar" "$javac" Main.java
Success env CLASSPATH="GooZip/Lib.zip${PS}BadJar/Lib.jar" "$javac" Main.java
Failure env CLASSPATH="${PS}BadZip/Lib.zip${PS}GooJar/Lib.jar" "$java" Main
Success env CLASSPATH="${PS}GooZip/Lib.zip${PS}BadJar/Lib.jar" "$java" Main

#----------------------------------------------------------------
# Check behavior of empty paths and empty path elements
#----------------------------------------------------------------
In() { cd "$1"; shift; "$@"; cd ..; }

In GooClass Failure "$javac" -cp ".." ../Main.java
In GooClass Failure "$java"  -cp ".." Main

# Unspecified classpath defaults to "."
Sys mkdir OneDir; Sys cp -p Main.java GooClass/Lib.class OneDir/.
In OneDir Success "$javac" Main.java
In OneDir Success "$java"  Main

# Empty classpath elements mean "."
In GooClass Success "$javac" -cp "${PS}.." ../Main.java
In GooClass Success "$java"  -cp "${PS}.." Main

In GooClass Success "$javac" -cp "..${PS}" ../Main.java
In GooClass Success "$java"  -cp "..${PS}" Main

In GooClass Success "$javac" -cp "..${PS}${PS}/xyzzy" ../Main.java
In GooClass Success "$java"  -cp "..${PS}${PS}/xyzzy" Main

# All other empty path elements are ignored.
In GooJar Failure "$javac" -extdirs "" -cp ".." ../Main.java
In GooJar Failure "$java" -Djava.ext.dirs="" -cp ".." Main

In GooJar Failure "$javac" -extdirs        "${PS}" -cp ".." ../Main.java
In GooJar Failure "$javac" -Djava.ext.dirs="${PS}" -cp ".." ../Main.java
In GooJar Failure "$java"  -Djava.ext.dirs="${PS}" -cp ".." Main

In GooJar Success "$javac" -extdirs        "." -cp ".." ../Main.java
In GooJar Success "$javac" -Djava.ext.dirs="." -cp ".." ../Main.java
In GooJar Success "$java"  -Djava.ext.dirs="." -cp ".." Main

In GooJar Failure "$javac" -J-Djava.endorsed.dirs="" -cp ".." ../Main.java
In GooJar Failure "$javac"   -Djava.endorsed.dirs="" -cp ".." ../Main.java
In GooJar Failure "$java"    -Djava.endorsed.dirs="" -cp ".." Main

In GooJar Failure "$javac" -J-Djava.endorsed.dirs="${PS}" -cp ".." ../Main.java
In GooJar Failure "$javac"   -endorseddirs        "${PS}" -cp ".." ../Main.java
In GooJar Failure "$java"    -Djava.endorsed.dirs="${PS}" -cp ".." Main

In GooJar Success "$javac" -J-Djava.endorsed.dirs="." -cp ".." ../Main.java
In GooJar Success "$javac"   -Djava.endorsed.dirs="." -cp ".." ../Main.java
In GooJar Success "$java"    -Djava.endorsed.dirs="." -cp ".." Main

In GooClass Failure "$javac" -Xbootclasspath/p: -cp ".." ../Main.java
In GooClass Failure "$java"  -Xbootclasspath/p: -cp ".." Main

In GooClass Success "$javac" -Xbootclasspath/p:. -cp ".." ../Main.java
In GooClass Success "$java"  -Xbootclasspath/p:. -cp ".." Main

In GooClass Failure "$javac" -Xbootclasspath:"$BCP" -cp ".." ../Main.java
In GooClass Failure "$java"  -Xbootclasspath:"$BCP" -cp ".." Main

In GooClass Success "$javac" -Xbootclasspath:"$BCP${PS}." -cp ".." ../Main.java
In GooClass Success "$java"  -Xbootclasspath:"$BCP${PS}." -cp ".." Main

In GooClass Failure "$javac" -Xbootclasspath/a: -cp ".." ../Main.java
In GooClass Failure "$java"  -Xbootclasspath/a: -cp ".." Main

In GooClass Success "$javac" -Xbootclasspath/a:. -cp ".." ../Main.java
In GooClass Success "$java"  -Xbootclasspath/a:. -cp ".." Main

Cleanup

Bottom Line
