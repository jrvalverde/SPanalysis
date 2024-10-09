#!/usr/bin/env zsh

ME="${0:A}"	# :A => resolve all symlinks
#echo "$ME"
myname=`basename "$ME"`
mydir="${0:A:h}"	# :h truncate last path component
mydir=$( cd "$mydir" ; pwd )
#echo "<$mydir><$myname>" $( realpath "$mydir" )
#exit

#java  -cp ".:$mydir:/usr/share/java/groovy-all.jar:$mydir/groovy-all.jar" Summ
java  -cp ".:$mydir:/usr/share/java/groovy-all.jar:$mydir/groovy-all.jar" -jar "$mydir"/Summ.jar
