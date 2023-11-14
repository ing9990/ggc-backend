#!/bin/bash

# Find the PID of the process using port 8080
PID=$(netstat -nltp | grep ':8080 ' | awk '{print $7}' | cut -d'/' -f1)

# Check if PID is not empty (process is running)
if [ -n "$PID" ]; then
    echo "Process using port 8080 found with PID: $PID"

    # Kill the process
    kill $PID

    echo "Process killed successfully."
else
    echo "No process found using port 8080."
fi

nohup java -jar /root/gigacoffee/build/libs/gigacoffee-backend.jar --spring.profiles.active=prod  > /dev/null 2>&1 &
echo "Start Gigacoffee-Backend Application"
exit