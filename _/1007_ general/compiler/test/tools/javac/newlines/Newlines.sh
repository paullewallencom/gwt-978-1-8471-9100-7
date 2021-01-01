# @test @(#)Newlines.sh	1.3 02/12/06
# @bug 4110560 4785453
# @summary portability : javac.properties
#
# @run shell Newlines.sh

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

${TESTJAVA}${FS}bin${FS}javac -J-Dline.separator='@' > ${TMP1} 2>&1
cat ${TMP1}
result=`cat ${TMP1} | wc -l`
if [ "$result" -eq 0 ]
then
  echo "Passed"
else
  echo "Failed"
fi
exit $result
