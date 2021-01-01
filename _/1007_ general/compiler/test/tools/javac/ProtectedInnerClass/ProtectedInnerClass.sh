# @test 03/01/10 @(#)ProtectedInnerClass.sh	1.10
# @bug 4087314 4800342
# @summary Verify allowed access to protected class from another package.
# @author William Maddox (maddox)
#
# @run shell ProtectedInnerClass.sh


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

rm -f ${TESTCLASSES}${FS}p1${FS}*.class ${TESTCLASSES}${FS}p2${FS}*.class

${TESTJAVA}${FS}bin${FS}javac -d ${TESTCLASSES} ${TESTSRC}${FS}p1${FS}ProtectedInnerClass1.java ${TESTSRC}${FS}p2${FS}ProtectedInnerClass2.java
${TESTJAVA}${FS}bin${FS}java -classpath ${CLASSPATH}${PS}${TESTCLASSES} p2.ProtectedInnerClass2
result=$?
if [ $result -eq 0 ]
then
  echo "Passed"
else
  echo "Failed"
fi
exit $result
