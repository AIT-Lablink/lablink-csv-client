Invoking the client from the command line
=========================================

When running the clients, the use of the ``-c`` command line flag followed by the URI to the configuration (see :doc:`here <configuration>`) is mandatory.

For example, on Windows this could look something like this:

.. code-block:: winbatch

   SET CONFIG_FILE_URI=%LLCONFIG%ait.test.csvclient.fixed.config
   SET LOGGER_CONFIG=-Dlog4j.configurationFile=%LLCONFIG%ait.all.all.log4j2
   java.exe %LOGGER_CONFIG% -jar path\to\csvclient-<VERSION>-jar-with-dependencies.jar -c %CONFIG_FILE_URI%

Running with Docker
===================

The image ``ait1/lablink-csv-client:latest`` (see :doc:`installation`) starts the client with ``-c`` set to ``${LLCONFIG}${CSV_CONFIG}``.
The defaults are ``LLCONFIG=http://lablink-config:10101/get?id=`` and ``CSV_CONFIG=lablink-csvclient.config``, so the image expects a configuration server reachable as the host ``lablink-config``.
Override both variables to point at another server.
``MEMORY_FLAG`` is the JVM heap setting and defaults to ``-Xmx512m``.
``LOGGER_ID`` is the logging configuration id and defaults to ``ait.all.log4j2``.

On Windows, a configuration server on the host is reachable as ``host.docker.internal``:

.. code-block:: winbatch

   docker run --rm -e LLCONFIG=http://host.docker.internal:10101/get?id= -e CSV_CONFIG=ait.test.csvclient.fixed.config ait1/lablink-csv-client:latest

On Linux, add that hostname explicitly:

.. code-block:: bash

   docker run --rm --add-host=host.docker.internal:host-gateway -e LLCONFIG=http://host.docker.internal:10101/get?id= -e CSV_CONFIG=ait.test.csvclient.fixed.config ait1/lablink-csv-client:latest

Arguments after the image name are passed through to the client.
