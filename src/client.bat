::the client's codebase URL
SET myHTTPserverIP=192.168.43.19

::the client's codebase port
SET myHTTPserverPort=80

SET RMIserverIP=192.168.22.131

REM SET LOG=-Dsun.rmi.loader.logLevel=SILENT
SET LOG=-Dsun.rmi.loader.logLevel=BRIEF

del /S client\*.class
del /S server\*.class
javac client/Client.java

start /B hfs.exe client
@echo.
@echo Wait until HFS is ready before starting Client!
@echo.
@pause

java -Djava.rmi.server.codebase=http://%myHTTPserverIP%:%myHTTPserverPort%/ -Djava.security.policy=java.policy -Djava.rmi.server.useCodebaseOnly=false %LOG% client.Client %RMIserverIP% 23

