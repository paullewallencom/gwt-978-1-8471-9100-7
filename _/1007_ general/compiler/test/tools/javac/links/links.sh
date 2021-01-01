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
  * )
    echo "Unrecognized system - test skipped."
    exit 0;
    ;;
esac

rm -rf T.class B.class b/B.class ${TESTCLASSES}/a ${TESTCLASSES}/classes
ln -s ${TESTSRC}/b ${TESTCLASSES}/a
mkdir ${TESTCLASSES}/classes

exec ${TESTJAVA}/bin/javac -sourcepath ${TESTCLASSES} -d ${TESTCLASSES}/classes ${TESTSRC}/T.java 2>&1
