#!/bin/sh

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

TMP1=OUTPUT.txt

cp ${TESTSRC}${FS}QualifiedAccess_1.java .
cp -r ${TESTSRC}${FS}pack1 .
${TESTJAVA}${FS}bin${FS}javac pack1${FS}P1.java
${TESTJAVA}${FS}bin${FS}javac pack1${FS}P2.java
${TESTJAVA}${FS}bin${FS}javac QualifiedAccess_1.java 2> ${TMP1}
cat ${TMP1}

diff -c ${TESTSRC}${FS}QualifiedAccess_1.out ${TMP1}
result=$?
rm ${TMP1}

if [ $result -eq 0 ]
then
  echo "Passed"
else
  echo "Failed"
fi
exit $result
