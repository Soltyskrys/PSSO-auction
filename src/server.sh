#!/bin/bash

# The IP address of this host
myIP=192.168.22.131

# The server's codebase URL
myHTTPserverIP=$myIP

# The server's codebase port
myHTTPserverPort=8080

LOG=-Dsun.rmi.loader.logLevel=SILENT
# LOG=-Dsun.rmi.loader.logLevel=BRIEF

rm server/*.class &>/dev/null
rm client/*.class &>/dev/null

# RMIregistry cannot have a CLASSPATH to the bytecode of the remote object
cd server
rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false -J$LOG &
cd ..

javac server/AuctionServer.java
#rmic server.ComputeEngine

# Make the bytecode available via HTTP server
python -m SimpleHTTPServer $myHTTPserverPort &
echo "Press Enter when HTTP server is on..."
read

java -Djava.rmi.server.codebase=http://$myHTTPserverIP:$myHTTPserverPort/ -Djava.rmi.server.hostname=$myIP -Djava.rmi.server.useCodebaseOnly=false -Djava.security.policy=java.policy $LOG server.AuctionServer

# ps
# kill -TERM 1490
