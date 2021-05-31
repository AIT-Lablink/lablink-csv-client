REM =============================================================
REM Edit the following variables to comply with your local setup.
REM =============================================================

REM Connection string for configuration server.
SET LLCONFIG=http://localhost:10101/get?id=

REM Version of CSV client package.
SET VERSION=0.0.1

REM Root directory of CSV client package (only change this if you really know what you are doing).
SET CSV_CLIENT_ROOT_DIR=%~DP0..

REM Path to Java JAR file of CSV client package.
SET CSV_CLIENT_JAR_FILE=%CSV_CLIENT_ROOT_DIR%\target\assembly\csvclient-%VERSION%-jar-with-dependencies.jar

REM Directory containing Java class files for testing.
SET CSV_CLIENT_TEST_CLASS_DIR=%CSV_CLIENT_ROOT_DIR%\target\test-classes

REM Path to Java JAR file of data point bridge.
SET DPB_JAR_FILE=%CSV_CLIENT_ROOT_DIR%\target\dependency\dpbridge-0.0.1-jar-with-dependencies.jar

REM Path to Java JAR file of standalone sync host.
SET SYNC_JAR_FILE=%CSV_CLIENT_ROOT_DIR%\target\dependency\sync-0.0.1-jar-with-dependencies.jar

REM Path to Java JAR file of config server.
SET CONFIG_JAR_FILE=%CSV_CLIENT_ROOT_DIR%\target\dependency\config-0.0.1-jar-with-dependencies.jar

REM Path to Java JAR file of data plotter.
SET PLOTTER_JAR_FILE=%CSV_CLIENT_ROOT_DIR%\target\dependency\plotter-0.0.2-jar-with-dependencies.jar

REM Check if environment variable JAVA_HOME has been defined.
IF NOT DEFINED JAVA_HOME (
    ECHO WARNING: environment variable JAVA_HOME not has been defined!
    PAUSE
)
