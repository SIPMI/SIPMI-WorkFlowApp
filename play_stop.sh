#/bin/sh
kill -9 $(cat RUNNING_PID)
rm RUNNING_PID
