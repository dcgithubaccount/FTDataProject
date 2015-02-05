#!/bin/sh
MyDatadir="/Volumes/MyData"
musicdir="/Volumes/music"
photodir="/Volumes/photo"
homedir="/Volumes/home"

logfile=/Users/deepakchoudhary/Scripts/NASHandler.log

cp /dev/null $logfile

if [ ! -d $MyDatadir ] && [ ! -d $musicdir ] && [ ! -d $photodir ] && [ ! -d $homedir ]; then
	 automator /Users/deepakchoudhary/Scripts/ConnectToNetworkDrive.workflow >> $logfile
fi

./dbfinder.sh start >> $logfile
