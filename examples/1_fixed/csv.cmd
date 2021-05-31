@ECHO OFF

SETLOCAL

REM Load the setup for the examples.
CALL %~DP0\..\setup.cmd

REM Path to class implementing the main routine.
SET CSV_CLIENT=at.ac.ait.lablink.clients.csvclient.CsvClient

REM CSV client configuration.
REM SET CONFIG_FILE_URI=%LLCONFIG%ait.test.csvclient.fixed.config
SET CONFIG_FILE_URI=%LLCONFIG%ait.test.csvclient.fixed.config

REM Logger configuration.
SET LOGGER_CONFIG=-Dlog4j.configurationFile=%LLCONFIG%ait.all.all.log4j2

REM Run the example.
"%JAVA_HOME%\bin\java.exe" %LOGGER_CONFIG% -cp %CSV_CLIENT_JAR_FILE% %CSV_CLIENT% -c %CONFIG_FILE_URI%

PAUSE
