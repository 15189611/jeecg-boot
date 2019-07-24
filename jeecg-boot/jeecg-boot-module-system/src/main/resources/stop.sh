#!/bin/sh

SERVICE_PID="server.pid"

pid=`cat $SERVICE_PID | awk '{print $1}'`
pid=`ps -aef | grep $pid | awk '{print $2}' |grep $pid`
if [ ${pid} ]; then
    echo 'Stop Process...'
    kill -15 $pid
fi

sleep 5
pid=`ps -aef | grep $pid | awk '{print $2}' |grep $pid`
if [ ${pid} ]; then
    echo 'Kill Process!'
    kill -9 $pid
else
    echo 'Stop Success!'
fi
