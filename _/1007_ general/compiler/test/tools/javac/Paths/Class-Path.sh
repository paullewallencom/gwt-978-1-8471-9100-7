#!/bin/sh

# @test @(#)Class-Path.sh	1.3 03/10/31
# @bug 4212732
# @summary Test handling of the Class-Path attribute in jar file manifests
# @author Martin Buchholz
#
# @run shell Class-Path.sh

# To run this test manually, simply do ./Class-Path.sh

. ${TESTSRC-.}/Util.sh

set -u

Cleanup() {
    Sys rm -rf pkg Main.java Main.class Main.jar jars
    Sys rm -rf MANIFEST.MF A.jar B.zip
}

Cleanup
Sys mkdir pkg

#----------------------------------------------------------------
# Create mutually referential jar files
#----------------------------------------------------------------
cat >pkg/A.java <<EOF
package pkg;
import pkg.B;
public class A {
    public static int f() { return B.g(); }
    public static int g() { return 0; }
}
EOF

cat >pkg/B.java <<EOF
package pkg;
import pkg.A;
public class B {
    public static int f() { return A.g(); }
    public static int g() { return 0; }
}
EOF

Sys "$javac" pkg/A.java pkg/B.java

MkManifestWithClassPath "B.zip"
Sys "$jar" cmf MANIFEST.MF A.jar pkg/A.class

MkManifestWithClassPath "A.jar"
Sys "$jar" cmf MANIFEST.MF B.zip pkg/B.class

cat >Main.java <<EOF
import pkg.*;
public class Main {
    public static void main(String []a) { System.exit(A.f() + B.f()); }
}
EOF

Success "$javac" -cp "A.jar" Main.java
Success "$javac" -cp "B.zip" Main.java
Success "$java"  -cp "A.jar${PS}." Main
Success "$java"  -cp "B.zip${PS}." Main

#----------------------------------------------------------------
# Jar file Class-Path expanded only for jars found on user class path
#----------------------------------------------------------------
Sys mkdir jars
Sys mv A.jar B.zip jars/.
Success "$javac" -cp "jars/A.jar"       Main.java
Success "$java"  -cp "jars/A.jar${PS}." Main

Success "$javac" -cp "jars/B.zip"       Main.java
Success "$java"  -cp "jars/B.zip${PS}." Main

Success "$javac" -extdirs "jars"        -cp None Main.java
Success "$javac" -Djava.ext.dirs="jars" -cp None Main.java
Success "$java"  -Djava.ext.dirs="jars" -cp .    Main

Success "$javac" -endorseddirs "jars"        -cp None Main.java
Success "$javac" -Djava.endorsed.dirs="jars" -cp None Main.java
Success "$java"  -Djava.endorsed.dirs="jars" -cp .    Main

Failure "$java"  -Xbootclasspath/p:"jars/A.jar" -cp .    Main
Failure "$java"  -Xbootclasspath/a:"jars/B.zip" -cp .    Main
Failure "$javac" -Xbootclasspath/p:"jars/A.jar" -cp None Main.java
Failure "$javac" -Xbootclasspath/a:"jars/B.zip" -cp None Main.java
Sys mv jars/A.jar jars/B.zip .

MkManifestWithClassPath "A.jar"
echo "Main-Class: Main" >> MANIFEST.MF
Sys "$jar" cmf MANIFEST.MF Main.jar Main.class

Success "$java" -jar Main.jar

MkManifestWithClassPath "."
Sys "$jar" cmf MANIFEST.MF A.jar pkg/A.class

Success "$javac" -cp "A.jar" Main.java
Success "$java" -jar Main.jar

MkManifestWithClassPath ""
Sys "$jar" cmf MANIFEST.MF A.jar pkg/A.class

Failure "$javac" -cp "A.jar" Main.java
Failure "$java" -jar Main.jar

#----------------------------------------------------------------
# Test new flag -e (application entry point)
#----------------------------------------------------------------

cat > Hello.java <<EOF
import pkg.*;
public class Hello {
    public static void main(String []a) { System.out.println("Hello World!"); }
}
EOF

cat > Bye.java <<EOF
import pkg.*;
public class Bye {
    public static void main(String []a) { System.out.println("Good Bye!"); }
}
EOF

Success "$javac" Hello.java Bye.java

# test jar creation without manifest
#
Success "$jar" cfe "Hello.jar" "Hello" Hello.class
Success "$java" -jar Hello.jar

# test for overriding the manifest during jar creation
#
echo "Main-Class: Hello" >> MANIFEST.MF

# test for error: " 'e' flag and manifest with the 'Main-Class' 
# attribute cannot be specified together, during creation
Failure "$jar" cmfe  MANIFEST.MF "Bye.jar" "Bye" Bye.class

# test for overriding the manifest when updating the jar
#
Success "$jar" cfe "greetings.jar" "Hello" Hello.class
Success "$jar" ufe "greetings.jar" "Bye" Bye.class
Success "$java" -jar greetings.jar

# test for error: " 'e' flag and manifest with the 'Main-Class'
# attribute cannot be specified together, during update
Failure "$jar" umfe  MANIFEST.MF "greetings.jar" "Hello"

# test jar updation when there are no inputfiles 
#
Success "$jar" ufe "Hello.jar" "Bye"
Failure "$java" -jar Hello.jar
Success "$jar" umf  MANIFEST.MF "Hello.jar"

# test creating jar when the to-be-archived files
# do not contain the specified main class, there is no check done
# for the presence of the main class, so the test will pass
#
Success "$jar" cfe "Hello.jar" "Hello" Bye.class

# Jar creation and update when there is no manifest and inputfiles
specified
Failure "$jar" cvf "A.jar"
Failure "$jar" uvf "A.jar"

# error: no such file or directory
Failure "$jar" cvf "A.jar" non-existing.file
Failure "$jar" uvf "A.jar" non-existing.file

Cleanup

Bottom Line
