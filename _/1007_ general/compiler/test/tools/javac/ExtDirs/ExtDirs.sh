#!/bin/sh

# @test @(#)ExtDirs.sh	1.7 04/03/19
# @bug 4204897 4256097 4785453 4863609
# @summary Test that '.jar' files in -extdirs are found.
# @author maddox
#
# @run shell/timeout=180 ExtDirs.sh

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

fail() {
	echo 'FAIL: unexpected result encountered'
        exit 1
}

javac=${TESTJAVA}${FS}bin${FS}javac

for i in 1 2 3; do
    if test ! -d ext${i}; then mkdir ext${i}; fi
    cp ${TESTSRC}${FS}ext${i}${FS}*.jar ext${i}
done

echo "Test 1"
$javac -d . -extdirs ext1 ${TESTSRC}${FS}ExtDirTest_1.java
if [ $? -ne 0 ] ; then fail ; fi

echo "Test 2"
$javac -d . -extdirs ext1${PS}ext2 ${TESTSRC}${FS}ExtDirTest_2.java
if [ $? -ne 0 ] ; then fail ; fi

echo "Test 3"
$javac -d . -extdirs ext3 ${TESTSRC}${FS}ExtDirTest_3.java
if [ $? -ne 0 ] ; then fail ; fi

echo PASS: all tests gave expected results
exit 0
