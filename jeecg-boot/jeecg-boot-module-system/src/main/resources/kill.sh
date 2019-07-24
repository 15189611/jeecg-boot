#!/bin/sh

SERVICE_PID="server.pid"

pid=`cat $SERVICE_PID | awk '{print $1}'`
pid=`ps -aef | grep $pid | grep -v grep | grep -v kill | awk '{print $2}' |grep $pid`
if [ ${pid} ]; then
    echo 'Kill Process!'
    kill -9 $pid
else
    echo 'app is not runing!'
fi
