#!/bin/bash

config="/Volumes/MyData/SharesProject/Mongodb/mongod.conf"
prog=dbfinder.sh
RETVAL=0

stop() {
grep_mongo=`ps -ef | grep mongo | grep -v grep`
if [ ${#grep_mongo} -gt 0 ]
then 
    #echo ${#grep_mongo}
    echo "`date` : Stop MongoDB."
    PID=`ps -ef | grep mongo | grep -v grep | awk '{ print $2}'`
    `kill -2 ${PID}`
    RETVAL=$?
    echo `date`
else 
    echo "`date` : MongoDB is not running."	
fi 
}
start() {
grep_mongo=`ps -ef | grep mongo | grep -v grep`
if [ -n "${grep_mongo}" ]
then
   echo "`date` : MongoDB is already running."
else 
   echo "`date` : Start MongoDB."
   `mongod --fork --config ${config}`
   RETVAL=$?
   echo `date`
fi
}

case "$1" in
    start)
	start
	;;
    stop)
	stop
	;;
    restart)
	stop
	start
	;;
    *)
	echo $"Usage: $prog {start|stop|restart}"
	exit 1
esac
 
exit $RETVAL
