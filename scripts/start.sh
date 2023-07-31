#!/usr/bin/env bash

REPOSITORY="/home/ubuntu/app"

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/deploy/*.jar $REPOSITORY/"

cp $REPOSITORY/deloy/*.jar $REPOSITORY/

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
        -Dspring.config.location=/home/ubuntu/app/application.yml \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

#CURRENT_PID=$(pgrep -f $JAR_FILE)
#echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG