Overview
========

The configuration has to be JSON-formatted.
It is divided into the following two categories:

:*Client*: basic configuration of the Lablink client (JSON object)
:*Measurements*: configuration of the client's outputs, which read CSV data and provides them as *measurements* (JSON array of JSON objects)

In the following, the configuration parameters for these categories are listed.

.. seealso:: See `below <#example-configuration>`_ for an example of a complete JSON configuration.

Client configuration
====================

.. topic:: CSV-specifc configuration parameters

  :*CsvUrl*: URL to CSV sourcse, such as a network location (e.g., ``http://localhost:10101/get?id=data"``) or a local file location (e.g., ``file:///c:/path/to/data.csv``)
  :*DispatchType*: select CSV data dispatch model (see :doc:`here <dispatch-modes>` for more information), must be either ``FIXED`` or ``TIME_VARIANT``

.. topic:: Dispatch type-specific configuration parameters

  Configuration parameters for dispatch type ``FIXED`` (fixed interval data source):

  :*CsvFormat*: format of the CSV file according to `Apache Commons CSV Format <https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVFormat.Predefined.html>`__ (optional, default: ``Default``)

  Configuration parameters for dispatch type ``TIME_VARIANT`` (timed data source):

  :*StartFromDataTime*: When starting the client, this timestamp will be considered the starting time.
    This means that no data entries with timestamps prior to this time will be sent. Data entries with timestamps occuring afterwards will scheduled relative to this timestamp.
    The timestamp must be fomatted according to configuration parameter *DatetimeFormat*.
  :*EndAtDataTime*:
    No data entries with timestamps later than this timestamp will be sent.
    The timestamp must be fomatted according to configuration parameter *DatetimeFormat*.
  :*DatetimeFormat*:
    `date and time format <https://www.w3.org/TR/NOTE-datetime>`__ for configuration parameters *StartFromDataTime* and *EndAtDataTime*

.. topic:: General client configuration parameters

  :*ClientName*: client name
  :*GroupName*: group name
  :*ScenarioName*: scenario name
  :*LabLinkPropertiesUrl*: URI to Lablink configuration
  :*SyncHostPropertiesUrl*: URI to sync host configuration
  :*ClientDescription*: description of the client (optional)
  :*ClientShell*: activate Lablink shell (optional, default: ``false``)

Measurements configuration
==========================

.. topic:: General measurement configuration parameters

  :*DataType*: type of CSV data, , allowed values are ``INT``, ``DOUBLE``, ``STRING``, ``BOOLEAN`` and ``LONG``
  :*DpName*: name of data point (i.e., the name of the CSV client's associated output data service)
  :*StartAfter*: after starting the CSV client, it will start sending the measurements with a delay according to this value; delay values have to be provided in ``ms``, ``s``, ``sec`` or ``min``, e.g. ``5 s`` (optional, default: ``0 s``)
  :*Unit*: unit associated to the measurement (optional, default: ``NONE``)

.. topic:: Dispatch type-specific configuration parameters

  Measurements configuration parameters for dispatch type ``FIXED`` (fixed interval data source):

  :*ColName*: name of the data column in the CSV source
  :*Interval*: fixed time interval between sending two consecutive measurements; delay values have to be provided in ``ms``, ``s``, ``sec`` or ``min``, e.g. ``5 s``


  Measurements configuration parameters for dispatch type ``TIME_VARIANT`` (timed data source):

  :*ColName*: name of a series of measurements in the CSV source of the form ``<device-name>.<datapoint-name>``
  :*ScaleFactor*: The time intervals between two consecutive measurements are determined by the associated timestamps in the CSV source.
    This configuration parameter allows to scale these time intervals (optional, default: ``1``).


Example configuration
=====================

.. code-block:: json

   {
       "Client": {
           "ClientDescription": "CSV client sending fixed-interval data",
           "ClientName": "CSVClient",
           "CsvUrl": "http://localhost:10101/get?id=ait.test.csvclient.fixed.data",
           "DispatchType": "FIXED",
           "GroupName": "CSVClientDemo",
           "LablinkPropertiesUrl": "http://localhost:10101/get?id=ait.all.all.llproperties",
           "ScenarioName": "CSVClientFixed",
           "SyncHostPropertiesUrl": "http://localhost:10101/get?id=ait.all.all.sync-host.properties"
       },
       "Measurements": [
           {
               "ColName": "test1.val1",
               "DataType": "DOUBLE",
               "DpName": "Value1",
               "Interval": "1000 ms",
               "StartAfter": "0 ms"
           },
           {
               "ColName": "test2.val2",
               "DataType": "DOUBLE",
               "DpName": "Value2",
               "Interval": "1000 ms",
               "StartAfter": "150 ms"
           },
           {
               "ColName": "test3.val3",
               "DataType": "DOUBLE",
               "DpName": "Value3",
               "Interval": "1000 ms",
               "StartAfter": "300 ms"
           },
           {
               "ColName": "test4.n",
               "DataType": "LONG",
               "DpName": "N",
               "Interval": "1000 ms",
               "StartAfter": "0 sec"
           }
       ]
   }
