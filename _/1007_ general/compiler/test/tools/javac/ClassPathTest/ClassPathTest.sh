#!/bin/sh

# @test @(#)ClassPathTest.sh	1.6 03/10/10
# @bug 4241229 4785453
# @summary Test -classpath option and classpath defaults.
# @author maddox
#
# @run shell/timeout=180 ClassPathTest.sh

# TODO: Should test sourcepath and classpath separately.

if [ "${TESTSRC}" = "" ]
then
  echo "TESTSRC not set.  Test cannot execute.  Failed."
  exit 1
fi
echo "TESTSRC=${TESTSRC}"
if [ "${TESTJAVA}" = "" ]
then
  echo "TESTJAVA not set.  Test cannot execute.  Failed."
  exit 1
fi
echo "TESTJAVA=${TESTJAVA}"
if [ "${TESTCLASSES}" = "" ]
then
  echo "TESTCLASSES not set.  Test cannot execute.  Failed."
  exit 1
fi
echo "TESTCLASSES=${TESTCLASSES}"
echo "CLASSPATH=${CLASSPATH}"

# set platform-dependent variables
OS=`uname -s`
case "$OS" in
  SunOS | Linux )
    NULL=/dev/null
    PS=":"
    FS="/"
    ;;
  Windows* )
    NULL=NUL
    PS=";"
    FS="\\"
    ;;
  * )
    echo "Unrecognized system!"
    exit 1;
    ;;
esac

javac=${TESTJAVA}${FS}bin${FS}javac

cleanup() {
	rm -f *.class pkg${FS}*.class foo${FS}pkg${FS}*.class bar${FS}pkg${FS}*.class
	cp -rf $TESTSRC${FS}* .
}

fail() {
	echo "FAIL: $1"
	failed="yes"
}

# report expectedResult $?
report() {
	if   test "$1" = "success" -a "$2" = 0; then
		echo "PASS: succeeded as expected"
	elif test "$1" = "failure" -a "$2" != 0; then
		echo "PASS: failed as expected"
	elif test "$1" = "success" -a "$2" != 0; then
		fail "test failed unexpectedly"
	elif test "$1" = "failure" -a "$2" = 0; then
		fail "test succeeded unexpectedly"
	else
		fail "internal error"
	fi
}

# testJavac expectedResult javacArgs...
testJavac() {
	expectedResult="$1"; shift
	cleanup
	echo $javac "$@"
	$javac "$@"
	report $expectedResult $?
}

unset CLASSPATH

# classpath should default to current directory

testJavac success ClassPathTest3.java
testJavac failure ClassPathTest1.java

# if CLASSPATH is set, it should be honored

CLASSPATH=bar; export CLASSPATH

testJavac success ClassPathTest2.java
testJavac failure ClassPathTest1.java
testJavac failure ClassPathTest3.java

# -classpath option should override default

testJavac success -classpath foo ClassPathTest1.java
testJavac failure -classpath foo ClassPathTest2.java
testJavac failure -classpath foo ClassPathTest3.java

if test -n "$failed"; then
	echo "Some tests failed"
	exit 1
else
	echo PASS: all tests gave expected results
	exit 0
fi
