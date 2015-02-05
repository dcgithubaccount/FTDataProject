#!/bin/sh
export process_path=/Volumes/MyData/SharesProject/DCFTCalc
#export JVM_ARGS="-XX:+CMSClassUnloadingEnabled -XX:PermSize=512M -XX:MaxPermSize=1024M"
export LOGBACK_CONFIG="-Dlogback.configurationFile=$process_path/properties/logback.xml"
export PARAMS="-DCountry=$1"



JAVA_OPTS="$LOGBACK_CONFIG $PARAMS" /usr/local/bin/scala  $process_path/lib/FTDataProject-assembly-1.0.jar "$@"
