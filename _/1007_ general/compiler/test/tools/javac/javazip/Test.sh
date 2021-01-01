#! /bin/sh -f
# 
# SCCS: @(#)Test.sh	1.3 06/02/21E

# @test
# @bug 4098712 6304984 6388453
# @summary check that source files inside zip files on the class path are ignored
# @run shell Test.sh

TS=${TESTSRC-.}
TC=${TESTCLASSES-.}
SCR=`pwd`

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

check() {
    expected=$1
    shift

    # clean old classes
    rm -f ${TC}${FS}*.class 

    echo "$*"
    if $* 2>&1 ; then
      actual=ok
    else
      actual=err
    fi
    if [ "$actual" != "$expected" ]; then
      case "$actual" in
        ok  ) echo "error: unexpected result: command succeeded" ;;
        err ) echo "error: unexpected result: command failed"
      esac
      exit 1
    else 
      case "$actual" in
        ok  ) echo "command succeeded as expected" ;;
        err ) echo "command failed as expected."
      esac
    fi

    echo 
}

echo "# create zip/jar files with source code"
check ok   ${TESTJAVA}${FS}bin${FS}jar cf ${SCR}${FS}good.jar -C ${TESTSRC}${FS}good B.java
check ok   ${TESTJAVA}${FS}bin${FS}jar cf ${SCR}${FS}good.zip -C ${TESTSRC}${FS}good B.java
check ok   ${TESTJAVA}${FS}bin${FS}jar cf ${SCR}${FS}bad.jar  -C ${TESTSRC}${FS}bad B.java
check ok   ${TESTJAVA}${FS}bin${FS}jar cf ${SCR}${FS}bad.zip  -C ${TESTSRC}${FS}bad B.java

echo "# control tests, with no paths"
check ok   ${TESTJAVA}${FS}bin${FS}javac -d ${TC} ${TESTSRC}${FS}A.java ${TESTSRC}${FS}good${FS}B.java
check err  ${TESTJAVA}${FS}bin${FS}javac -d ${TC} ${TESTSRC}${FS}A.java ${TESTSRC}${FS}bad${FS}B.java

echo "# test that source files are found in directories on path"
check ok   ${TESTJAVA}${FS}bin${FS}javac -d ${TC} -classpath ${TESTSRC}${FS}good   ${TESTSRC}${FS}A.java
check ok   ${TESTJAVA}${FS}bin${FS}javac -d ${TC} -sourcepath ${TESTSRC}${FS}good  ${TESTSRC}${FS}A.java
check err  ${TESTJAVA}${FS}bin${FS}javac -d ${TC} -classpath ${TESTSRC}${FS}bad    ${TESTSRC}${FS}A.java
check err  ${TESTJAVA}${FS}bin${FS}javac -d ${TC} -sourcepath ${TESTSRC}${FS}bad   ${TESTSRC}${FS}A.java

echo "# test that source files are found in zip/jar files on path"
check ok   ${TESTJAVA}${FS}bin${FS}javac -d ${TC} -classpath ${SCR}${FS}good.zip   ${TESTSRC}${FS}A.java
check ok   ${TESTJAVA}${FS}bin${FS}javac -d ${TC} -classpath ${SCR}${FS}good.jar   ${TESTSRC}${FS}A.java
check err  ${TESTJAVA}${FS}bin${FS}javac -d ${TC} -sourcepath ${SCR}${FS}bad.zip   ${TESTSRC}${FS}A.java  
check err  ${TESTJAVA}${FS}bin${FS}javac -d ${TC} -sourcepath ${SCR}${FS}bad.jar   ${TESTSRC}${FS}A.java 
