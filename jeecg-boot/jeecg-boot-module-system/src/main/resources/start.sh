#!/bin/sh


APP_NAME=tea_shop_service
SCRIPT="$0"

while [ -h "$SCRIPT" ] ; do
  ls=`ls -ld "$SCRIPT"`
  # Drop everything prior to ->
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    SCRIPT="$link"
  else
    SCRIPT=`dirname "$SCRIPT"`/"$link"
  fi
done

# determine home
APP_HOME=`dirname "$SCRIPT"`

# make HOME absolute
APP_HOME=`cd "$APP_HOME"; pwd`

SERVICE_PID="server.pid"

if [ ! -f "${SERVICE_PID}" ]; then
touch "${SERVICE_PID}"
fi

parse_jvm_options() {
  if [ -f "$1" ]; then
    echo "$(grep "^-" "$1" | tr -d '\r' | tr '\n' ' ')"
  fi
}

if [ -z "$APP_JVM_OPTIONS" ]; then
    for jvm_options in "$APP_HOME"/config/jvm.options; do
        if [ -r "$jvm_options" ]; then
            APP_JVM_OPTIONS=$jvm_options
            break
        fi
    done
fi

APP_JAVA_OPTS="$(parse_jvm_options "$APP_JVM_OPTIONS") $APP_JAVA_OPTS"

if [ -x "$JAVA_HOME/bin/java" ]; then
    JAVA="$JAVA_HOME/bin/java"
else
    JAVA=`which java`
fi

if [ ! -x "$JAVA" ]; then
    echo "Could not find any executable java binary. Please install java in your PATH or set JAVA_HOME"
    exit 1
fi

APP_STARTUP_SLEEP_TIME=5

HOSTNAME=`hostname | cut -d. -f1`
export HOSTNAME

daemonized=`echo $* | egrep -- '(^-d |-d$| -d |--daemonize$|--daemonize )'`
if [ -z "$daemonized" ] ; then
    exec "$JAVA" $APP_JAVA_OPTS -jar $APP_HOME/lib/$APP_NAME*.jar "$@"
else
    exec "$JAVA" $APP_JAVA_OPTS -jar $APP_HOME/lib/$APP_NAME*.jar "$@" <&- &
    retval=$?
    pid=$!
    [ $retval -eq 0 ] || exit $retval
    if [ ! -z "$APP_STARTUP_SLEEP_TIME" ]; then
      sleep $APP_STARTUP_SLEEP_TIME
    fi
    if ! ps -p $pid > /dev/null ; then
      exit 1
    fi
    
    echo $pid > $SERVICE_PID
    
    exit 0
fi

exit $?
