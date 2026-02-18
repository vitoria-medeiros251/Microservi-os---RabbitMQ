@echo off
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
echo Usando Java 21...
java -version
echo.
echo Iniciando ms.users...
mvn spring-boot:run