source_java=${1:-TrackAnalyzer_.java}
ext=${source_java##*.}

FIJI=/home/jr/contrib/Fiji.app
UPDATE=YES

indir=0

if [ "$ext" != "java" ] ; then
    echo "ERROR: can only compile java files"
    exit
fi

if [ -d new ] ; then
    indir=1
    cd new
fi

# ensure we do not use an old compilation
echo "cleaning up"
rm -f "$FIJI"/plugins/TrackAnalyzer_.jar
rm -f *.class

/usr/lib/jvm/java-8-openjdk-amd64/bin/javac \
    ${comment# -classpath "../plugins/*:$FIJI/jars/*:" } \
    ${comment# -classpath "$FIJI/plugins*:$FIJI/plugins/*:$FIJI/jars/*:" } \
    -classpath "$FIJI/plugins/*:$FIJI/jars/*:../extra/jars/*:" \
    -Xlint:deprecation \
    -Xlint:unchecked \
    -Xdiags:verbose \
    $source_java \
    |& tee zygJavaC.log #| less

#java -classpath "plugins/*:/home/jr/contrib/Fiji.app/jars/*:" ${source_java%.java}


UPDATE=NO
if [ "$UPDATE" = "YES" ] ; then
    jar -cvf ../TrackAnalyzer_.new.jar .
    echo ""
    echo "Installing plugin"
    cp ../TrackAnalyzer_.new.jar "$FIJI"/plugins/TrackAnalyzer_.jar
fi

if [ $indir -eq 1 ] ; then
    cd ..
    indir=0
fi

