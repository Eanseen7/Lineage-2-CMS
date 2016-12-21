#!/bin/bash

java_opts=-Xms100m
java_opts="$java_opts -Xmx300m"

java_settings="$java_settings -Dfile.encoding=UTF-8"
java_settings="$java_settings -Djava.net.preferIPv4Stack=true"

while :;
do
	java -server $java_settings $java_opts -jar cms-0.0.2.jar > stdout.log 2>&1
	[ $? -ne 2 ] && break
	sleep 10;
done