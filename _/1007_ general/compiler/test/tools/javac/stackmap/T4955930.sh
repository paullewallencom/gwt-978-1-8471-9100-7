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
echo "CLASSPATH=${CLASSPATH}"

# set platform-dependent variables
OS=`uname -s`
case "$OS" in
  SunOS | Linux )
    FS="/"
    ;;
  Windows_95 | Windows_98 | Windows_NT )
    FS="\\"
    ;;
  * )
    echo "Unrecognized system!"
    exit 1;
    ;;
esac

TMP1=T4955930.javap

cp ${TESTSRC}${FS}T4955930.java .
${TESTJAVA}${FS}bin${FS}javac -target 6 T4955930.java
result=$?
if [ $result -ne 0 ]
then
    exit $result
fi

${TESTJAVA}${FS}bin${FS}javap -verbose T4955930 > ${TMP1}
grep "StackMapTable: number_of_entries = 2" ${TMP1}
result=$?

if [ $result -eq 0 ]
then
  echo "Passed"
else
  echo "Failed"
fi
exit $result
