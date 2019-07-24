#!/bin/sh

APP_NAME=tea_shop_service
SERVICE_PID="server.pid"

pid=`cat $SERVICE_PID | awk '{print $1}'`
pid=`ps -aef | grep $pid | grep $APP_NAME |grep -v grep | grep -v kill | awk '{print $2}' |grep $pid`
if [ ${pid} ]; then
    echo 'App is running.'
else
    echo 'App is NOT running.'
fi
