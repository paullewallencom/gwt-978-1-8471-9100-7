#! /bin/sh -f
#
# @test
# @bug 6302184 6350124 6357979
# @summary javac hidden options that generate source should use the given encoding, if available
# @run shell T6302184.sh

TS=${TESTSRC-.}
TC=${TESTCLASSES-.}

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

${TESTJAVA}${FS}bin${FS}javac -d ${TC} -cp ${TC} -encoding iso-8859-1 -XD-printsource ${TS}${FS}T6302184.java 2>&1 > ${NULL}
diff -c ${TC}${FS}T6302184.java ${TS}${FS}T6302184.out
result=$?


if [ $result -eq 0 ]
then
  echo "Passed"
else
  echo "Failed"
fi
exit $result
