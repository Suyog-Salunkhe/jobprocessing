#!/bin/sh

sudo ps aux | grep '[j]ava -Xmx2g -Xms512m -jar {Path}/jobprocessing-0.0.1-SNAPSHOT.jar' | awk '{print $2}' |  xargs sudo kill -9

if [ "$?" -eq "0" ]
then
  sudo nohup java -Xmx2g -Xms512m -jar {Path}/portal-auth-server-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
else
  sudo nohup java -Xmx2g -Xms512m -jar {Path}/portal-auth-server-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
fi
