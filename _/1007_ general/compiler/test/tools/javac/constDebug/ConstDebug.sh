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

cp ${TESTSRC}${FS}$1.java .
${TESTJAVA}${FS}bin${FS}javac -g -d . -classpath .${PS}${TESTSRC} $1.java 2> ${TMP1}
result=$?
if [ $result -ne 0 ]; then exit $result; fi
if strings $1.class | grep clinit; then
  echo "Failed"
  exit 1;
else
  echo "Passed"
  exit 0;
fi
