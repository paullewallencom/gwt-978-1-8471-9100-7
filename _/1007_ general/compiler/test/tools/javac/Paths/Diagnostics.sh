#!/bin/sh
#
# @test @(#)Diagnostics.sh	1.6 06/06/08
# @bug 4884487 6295519 6236704 6429613
# @summary Test for proper diagnostics during path manipulation operations
# @author Martin Buchholz
#
# @run shell/timeout=180 Diagnostics.sh

# To run this test manually, simply do ./Diagnostics.sh

. ${TESTSRC-.}/Util.sh

set -u

BCP=`DefaultBootClassPath`

DiagnosticsInEnglishPlease

No() { NO="no"; "$@"; NO=""; }	# No means NO!

Warning() {
    HorizontalRule
    echo "$@"
    output=`"$@" 2>&1`; rc="$?"
    test -n "$output" && echo "$output"
    test $rc -eq 0 || Fail "Command \"$*\" failed with exitValue $rc";
    case "$output" in *warning:*) gotwarning="yes";; *) gotwarning="no";; esac

    if test "$gotwarning" = "yes" -a "$NO" = "no"; then
	Fail "Command \"$*\" printed an unexpected warning"
    elif test "$gotwarning" = "no" -a "$NO" != "no"; then
	Fail "Command \"$*\" did not generate the expected warning"
    fi
}

Error() {
    HorizontalRule
    echo "$@"
    output=`"$@" 2>&1`; rc="$?"
    test -n "$output" && echo "$output"
    case "$output" in *error:*) goterror="yes";; *) goterror="no";; esac

    if test "$NO" = "no"; then
	test "$rc" -ne 0 && \
	    Fail "Command \"$*\" failed with return code $rc"
	test "$goterror" = "yes" && \
	    Fail "Command \"$*\" did not generate any error message"
    else
	test "$rc" -eq 0 && \
	    Fail "Command \"$*\" was supposed to Die with fatal error";
	test "$goterror" = "no" && \
	    Fail "Command \"$*\" printed an unexpected error message"
    fi
}

Cleanup() {
    Sys rm -rf Main.java Main.class 
    Sys rm -rf classes classes.foo classes.jar classes.war classes.zip
    Sys rm -rf MANIFEST.MF classesRef.jar classesRefRef.jar jars
}

Cleanup
echo "public class Main{public static void main(String[]a){}}" > Main.java

#----------------------------------------------------------------
# No warnings unless -Xlint:path is used
#----------------------------------------------------------------
No Warning "$javac" Main.java
No Warning "$javac" -cp ".${PS}classes" Main.java

#----------------------------------------------------------------
# Warn for missing elts in user-specified paths
#----------------------------------------------------------------
Warning "$javac" -Xlint:path -cp ".${PS}classes"         Main.java
Warning "$javac" -Xlint:path "-Xbootclasspath/p:classes" Main.java
Warning "$javac" -Xlint      "-Xbootclasspath/a:classes" Main.java
Warning "$javac" -Xlint:path "-endorseddirs" "classes"   Main.java
Warning "$javac" -Xlint      "-extdirs"      "classes"   Main.java
Warning "$javac" -Xlint:path "-Xbootclasspath:classes${PS}${BCP}" Main.java

#----------------------------------------------------------------
# No warning for missing elts in "system" paths
#----------------------------------------------------------------
No Warning "$javac" -Xlint:path "-J-Djava.endorsed.dirs=classes" Main.java
No Warning "$javac" -Xlint:path "-J-Djava.ext.dirs=classes"      Main.java
No Warning "$javac" -Xlint:path "-J-Xbootclasspath/p:classes"    Main.java
No Warning "$javac" -Xlint:path "-J-Xbootclasspath/a:classes"    Main.java
No Warning "$javac" -Xlint:path "-J-Xbootclasspath:classes${PS}${BCP}" Main.java

#----------------------------------------------------------------
# No warning if class path element exists
#----------------------------------------------------------------
Sys mkdir classes
No Warning "$javac" -Xlint:path -cp ".${PS}classes"         Main.java
No Warning "$javac" -Xlint:path "-endorseddirs"   "classes" Main.java
No Warning "$javac" -Xlint:path "-extdirs"        "classes" Main.java
No Warning "$javac" -Xlint:path "-Xbootclasspath/p:classes" Main.java
No Warning "$javac" -Xlint:path "-Xbootclasspath/a:classes" Main.java
No Warning "$javac" -Xlint:path "-Xbootclasspath:classes${PS}${BCP}" Main.java

Sys "$jar" cf classes.jar Main.class
Sys cp classes.jar classes.war
Sys cp classes.war classes.zip
No Warning "$javac" -Xlint:path -cp ".${PS}classes.jar"     Main.java
   Warning "$javac" -Xlint:path -cp ".${PS}classes.war"     Main.java
No Warning "$javac" -Xlint:path -cp ".${PS}classes.zip"     Main.java

#----------------------------------------------------------------
# Warn if -Xlint is used and if class path element refers to 
# regular file which doesn't look like a zip file, but is
#----------------------------------------------------------------
Sys cp classes.war classes.foo
   Warning "$javac" -Xlint:path -cp ".${PS}classes.foo"     Main.java


#----------------------------------------------------------------
# No error if class path element refers to regular file which is
# not a zip file
#----------------------------------------------------------------
No Error "$javac" -cp Main.java Main.java # Main.java is NOT a jar file
No Error "$javac" Main.java

#----------------------------------------------------------------
# Warn if -Xlint is used and if class path element refers to 
# regular file which is not a zip file
#----------------------------------------------------------------
Warning "$javac" -Xlint -cp Main.java Main.java # Main.java is NOT a jar file

#----------------------------------------------------------------
# Test jar file class path reference recursion
#----------------------------------------------------------------
MkManifestWithClassPath classesRef.jar
Sys "$jar" cmf MANIFEST.MF classesRefRef.jar Main.class

#----------------------------------------------------------------
# Non-existent recursive Class-Path reference gives warning
#----------------------------------------------------------------
No Warning "$javac"             -classpath   classesRefRef.jar Main.java
   Warning "$javac" -Xlint      -classpath   classesRefRef.jar Main.java
No Warning "$javac" -Xlint -Xbootclasspath/p:classesRefRef.jar Main.java

BadJarFile classesRef.jar

#----------------------------------------------------------------
# Non-jar file recursive Class-Path reference gives error
#----------------------------------------------------------------
   Error "$javac"      -classpath   classesRefRef.jar Main.java
No Error "$javac" -Xbootclasspath/a:classesRefRef.jar Main.java

MkManifestWithClassPath classes
Sys "$jar" cmf MANIFEST.MF classesRef.jar Main.class

#----------------------------------------------------------------
# Jar file recursive Class-Path reference is OK
#----------------------------------------------------------------
No Warning "$javac" -Xlint      -classpath   classesRefRef.jar Main.java
No Warning "$javac" -Xlint -Xbootclasspath/p:classesRefRef.jar Main.java

#----------------------------------------------------------------
# Class-Path attribute ignored in extdirs or endorseddirs
#----------------------------------------------------------------
Sys mkdir jars
Sys cp -p classesRefRef.jar jars/.
No Warning "$javac" -Xlint -extdirs      jars Main.java
No Warning "$javac" -Xlint -endorseddirs jars Main.java

#----------------------------------------------------------------
# Bad Jar file in extdirs and endorseddirs should not be ignored
#----------------------------------------------------------------
BadJarFile jars/classesRef.jar
   Error "$javac" -Xlint -extdirs      jars Main.java
   Error "$javac" -Xlint -endorseddirs jars Main.java

Cleanup

Bottom Line
