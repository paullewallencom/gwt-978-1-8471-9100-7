#! /bin/sh -f
# 
# SCCS: @(#)apt.sh	1.2 05/03/03
#
# Usage:
#  @run apt.sh <apt-args>
#
# This script is to run apt for a regression test

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

CLASSPATH=${TESTCLASSES}${PS}${TESTJAVA}${FS}lib${FS}tools.jar ${TESTJAVA}${FS}bin${FS}apt $*
result=$?

if [ $result -eq 0 ]
then
  echo "Passed"
else
  echo "Failed"
fi
exit $result


