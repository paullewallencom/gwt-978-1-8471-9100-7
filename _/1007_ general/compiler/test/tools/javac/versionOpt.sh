#!/bin/sh

# @test
# @bug 4461214 6227587
# @summary support-version and -fullversion
# @run shell versionOpt.sh

if [ "${TESTJAVA}" = "" ]
then
  echo "TESTJAVA not set.  Test cannot execute.  Failed."
  exit 1
fi
echo "TESTJAVA=${TESTJAVA}"

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

# create reference files based on java values
${TESTJAVA}${FS}bin${FS}java -version 2>&1 | \
    sed -e 's/java version "\([^"]*\)"/javac \1/' -e '2,$d' > version.ref.out

${TESTJAVA}${FS}bin${FS}java -fullversion 2>&1 | \
    sed -e 's/java full version/javac full version/' -e '2,$d' > fullversion.ref.out

# run javac
${TESTJAVA}${FS}bin${FS}javac -version 2> version.out
cat version.out
diff -c version.ref.out version.out
version_result=$?

${TESTJAVA}${FS}bin${FS}javac -fullversion 2> fullversion.out
cat fullversion.out
diff -c fullversion.ref.out fullversion.out
fullversion_result=$?

if [ $version_result -eq 0 -a $fullversion_result -eq 0 ]
then
  echo "Passed"
  exit 0
else
  echo "Failed"
  exit 1
fi




