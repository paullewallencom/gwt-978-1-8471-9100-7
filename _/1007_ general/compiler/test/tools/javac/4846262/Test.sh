#!/bin/sh -f
#
# @test
# @bug 4846262
# @summary check that javac operates correctly in EBCDIC locale



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

rm -f Test.java Test.out

${TESTJAVA}${FS}bin${FS}native2ascii -reverse -encoding IBM1047 ${TESTSRC}${FS}Test.java Test.java

${TESTJAVA}${FS}bin${FS}javac -J-Duser.language=en -J-Duser.region=US -J-Dfile.encoding=IBM1047 Test.java 2>Test.tmp

${TESTJAVA}${FS}bin${FS}native2ascii -encoding IBM1047 Test.tmp Test.out

diff -c ${TESTSRC}${FS}Test.out Test.out
result=$?

if [ $result -eq o ]
then
  echo "Passed"
else
  echo "Failed"
fi
exit $result
