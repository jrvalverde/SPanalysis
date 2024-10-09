source_java=${1:-TrackAnalyzer_.java}
ext=${source_java##*.}

indir=0

if [ "$ext" != "java" ] ; then
    echo "ERROR: can only compile java files"
    exit
fi

if [ -d new ] ; then
    indir=1
    cd new
fi
/usr/lib/jvm/java-8-openjdk-amd64/bin/javac \
    -classpath "plugins/*:/home/jr/Fiji.app/jars/*:" \
    -Xlint:deprecation \
    -Xlint:unchecked \
    $source_java \
    |& tee zygJavaC.log | less

#java ${source_java%.java}

if [ $indir -eq 1 ] ; then
    cd ..
    indir=0
fi
