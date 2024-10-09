#!/usr/bin/env bash

ME=${BASH_SOURCE[0]}
#echo "$ME"
myname=`basename "$ME"`
mydir=`dirname "$ME"`
mydir=$( cd "$mydir" ; pwd )
#echo "<$mydir><$myname>" $( realpath "$mydir" )
#exit

#java  -cp ".:$mydir:/usr/share/java/groovy-all.jar:$mydir/groovy-all.jar" Summ
java  -cp ".:$mydir:/usr/share/java/groovy-all.jar:$mydir/groovy-all.jar" -jar "$mydir"/Summ.jar
