Maven
=====

Include the Lablink CSV client to your Maven setup by including the following dependency into your *pom.xml*:

.. code-block:: xml

   <dependency>
     <groupId>at.ac.ait.lablink.clients</groupId>
     <artifactId>csvclient</artifactId>
     <version>0.0.1</version>
   </dependency>

Building from source
====================

Check out the project and compile it with `Maven <https://maven.apache.org/>`__:

.. code-block:: none

   git clone https://github.com/AIT-Lablink/lablink-csv-client.git
   cd lablink-csv-client
   mvnw clean package

This should create JAR file *csvlient-<VERSION>-jar-with-dependencies.jar* in subdirectory *target/assembly*.
Also, all additional Lablink resources needed for running the :doc:`examples <examples>` will be copied to directory *target/dependency*.