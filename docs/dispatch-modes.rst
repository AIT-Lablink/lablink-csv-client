Fixed interval data source
==========================

The data from the columns of the CSV source are sent one after the other as individual measurements.
The time between two consecutive measurements is constant (but configurable for each CSV column).
The CSV source itself does not contain any timing information, but only the data values.

.. note:: This dispatch mode is enable by setting configuration parameter ``DispatchType`` to ``FIXED``. 

Supported CSV formats for fixed interval data sources
"""""""""""""""""""""""""""""""""""""""""""""""""""""

This dispatch mode supports als CSV formats according to the `Apache Commons CSV Format <https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVFormat.Predefined.html>`__.
The default format uses ``,`` as data delimiter and ``.`` as decimal separator.
You can use other formats by specifying parameter ``CsvFormat`` in the `client configuration <configuration.html#client-configuration>`__ accordingly.

The first line of the CSV source must be a header line, providing a name for each column.
When selecting a CSV column as output for the CSV client, it has to be referred to by this name in parameter ``ColName`` in the `measurements configuration <configuration.html#measurements-configuration>`__.

.. seealso:: An **example** for a **fixed interval data source** can be found `here <examples.html#example-1-fixed-interval-data-source>`__.

Timed data source
=================

The data from the CSV source uses a dedicated format (see `below <#supported-csv-formats-for-timed-data-sources>`_), providing data values associated to timestamps, which are sent one-by-one as individual measurements.
The timing of the measurements is determined by the timestamps associated to each data value.

.. note:: This dispatch mode is enable by setting configuration parameter ``DispatchType`` to ``TIME_VARIANT``. 

Supported CSV formats for timed data sources
""""""""""""""""""""""""""""""""""""""""""""

This dispatch mode requires each line of the CSV source to be of the following format:

::

   <device-name>;<datapoint-name>;<timestamp>;<value>

All entries with the same ``<device-name>`` and ``<datapoint-name>`` belong to a **series of measurements**.
When selecting a series of measurements as output for the CSV client, it has to be referred to as  ``<device-name>.<datapoint-name>`` in parameter ``ColName`` in the `measurements configuration <configuration.html#measurements-configuration>`__.

The timestamps have to be provided in `Unix time <https://en.wikipedia.org/wiki/Unix_time>`__.

.. seealso:: An **example** for a **timed data source** can be found `here <examples.html#example-2-timed-data-source>`__.

