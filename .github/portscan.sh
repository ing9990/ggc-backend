#!/bin/bash
port=$1
PID=$(lsof -t -i :$port)

if [ -z "$PID" ]; then
    echo "$port 포트를 사용하는 프로세스가 없습니다."
else
    kill -9 $PID
    echo "포트 $port 를 사용하는 프로세스 (PID: $PID)를 종료했습니다."
fi
