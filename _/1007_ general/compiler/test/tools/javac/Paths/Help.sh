#!/bin/sh
#
# @test @(#)Help.sh	1.2 03/10/20
# @bug 4940642
# @summary Check for -help and -X flags
# @author Martin Buchholz
#
# @run shell Help.sh

# To run this test manually, simply do ./MineField.sh


. ${TESTSRC-.}/Util.sh

set -u

DiagnosticsInEnglishPlease

HELP="`$javac -help 2>&1`"
XHELP="`$javac -X 2>&1`"

#----------------------------------------------------------------
# Standard options
#----------------------------------------------------------------
for opt in \
    "-X " \
    "-J" \
    "-classpath " \
    "-cp " \
    "-bootclasspath " \
    "-sourcepath "; 
do
    case "$HELP" in *"$opt"*) ;; *) Fail "Bad help output" ;; esac
done

#----------------------------------------------------------------
# Non-standard options
#----------------------------------------------------------------
for opt in \
    "-Xbootclasspath/p:"; 
do
    case "$XHELP" in *"$opt"*) ;; *) Fail "Bad help output" ;; esac
done

Bottom Line
