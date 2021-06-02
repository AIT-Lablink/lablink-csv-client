Invoking the client from the command line
=========================================

When running the clients, the use of the ``-c`` command line flag followed by the URI to the configuration (see :doc:`here <configuration>`) is mandatory.

For example, on Windows this could look something like this:

.. code-block:: winbatch

   SET CONFIG_FILE_URI=%LLCONFIG%ait.test.csvclient.fixed.config
   SET LOGGER_CONFIG=-Dlog4j.configurationFile=%LLCONFIG%ait.all.all.log4j2
   java.exe %LOGGER_CONFIG% -jar path\to\csvclient-<VERSION>-jar-with-dependencies.jar -c %CONFIG_FILE_URI%
