#!/bin/bash
PID=$(netstat -nltp | grep ':8080 ' | awk '{print $7}' | cut -d'/' -f1)
if [ -n "$PID" ]; then
    echo "Process using port 8080 found with PID: $PID"
    kill $PID
    echo "Process killed successfully."
else
    echo "No process found using port 8080."
fi