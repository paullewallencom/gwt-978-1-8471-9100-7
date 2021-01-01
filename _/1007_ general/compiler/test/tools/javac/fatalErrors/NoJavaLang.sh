#!/bin/sh

if [ "${TESTSRC}" = "" ]
then
  echo "TESTSRC not set.  Test cannot execute.  Failed."
  exit 1
fi
printf '%s' "TESTSRC=${TESTSRC}" ; echo
if [ "${TESTJAVA}" = "" ]
then
  echo "TESTJAVA not set.  Test cannot execute.  Failed."
  exit 1
fi
printf '%s' "TESTJAVA=${TESTJAVA}" ; echo
if [ "${TESTCLASSES}" = "" ]
then
  echo "TESTCLASSES not set.  Test cannot execute.  Failed."
  exit 1
fi
printf '%s' "TESTCLASSES=${TESTCLASSES}" ; echo
printf '%s' "CLASSPATH=${CLASSPATH}" ; echo
echo

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

cp "${TESTSRC}${FS}NoJavaLang.java" .

echo "- verifing that fatal error is not produced in the regular case" 
"${TESTJAVA}${FS}bin${FS}javac" NoJavaLang.java 2> "${TMP1}"
result=$?

if [ $result -eq 0 ]
then
  echo "Passed - base compilation successful"
else
  echo "Failed - unable to compile test"
  exit $result
fi

echo

echo "- verifing the fatal error is produced"
rm "${TMP1}"
"${TESTJAVA}${FS}bin${FS}javac" -bootclasspath . NoJavaLang.java 2> "${TMP1}"

# return code should be EXIT_SYSERR
result=$?
if [ $result -ne 3 ]
then
  echo "Failed - unexpected return code"
  exit $result
else
  echo "Passed - expected return code"
fi

# expected message
cat "${TMP1}"
diff -c "${TESTSRC}${FS}NoJavaLang.out" "${TMP1}"
result=$?
rm "${TMP1}"

if [ $result -eq 0 ]
then
  echo "Passed - expected message"
else
  echo "Failed - unexpected message"
  exit $result

fi

exit
