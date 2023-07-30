##!/usr/bin/env bash
#
#PROJECT_ROOT="/home/ubuntu/app"
#JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"
#
#APP_LOG="$PROJECT_ROOT/application.log"
#ERROR_LOG="$PROJECT_ROOT/error.log"
#DEPLOY_LOG="$PROJECT_ROOT/deploy.log"
#
#TIME_NOW=$(date +%c)
#
## build 파일 복사
#echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
#cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE
#
## jar 파일 실행
#echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
#nohup java -jar \
#        -Dspring.config.location=/home/ubuntu/app/application.yml \
#        $JAR_FILE > $APP_LOG 2> $ERROR_LOG &
#
#CURRENT_PID=$(pgrep -f $JAR_FILE)
#echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG

REPOSITORY=/home/ubuntu/app

echo "> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fla java | grep hayan | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*SNAPSHOT.jar | tail -n 1)

echo "> JAR NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
        -Dspring.config.location=/home/ubuntu/app/application.yml \
        $JAR_FILE > $APP_LOG 2> $ERROR_LOG &