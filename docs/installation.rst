Installation
============

Maven
-----

The Lablink CSV client's compiled Java package is available on the |MCR|_.
Use it in your local Maven_ setup by including the following dependency into your *pom.xml*:

.. code-block:: xml

   <dependency>
     <groupId>at.ac.ait.lablink.clients</groupId>
     <artifactId>csvclient</artifactId>
     <version>0.0.2</version>
   </dependency>

.. note:: You may have to adapt this snippet to use the latest version, please check the |MCR|_.

Building from source
--------------------

Installation from source requires a local **Java Development Kit** installation, for instance the `Oracle Java SE Development Kit 13 <https://www.oracle.com/technetwork/java/javase/downloads/index.html>`_ or the `OpenJDK <https://openjdk.java.net/>`_.

Check out the project and compile it with Maven_:

.. code-block:: none

   git clone https://github.com/AIT-Lablink/lablink-csv-client.git
   cd lablink-csv-client
   mvnw clean package

This should create JAR file *csvclient-<VERSION>-jar-with-dependencies.jar* in subdirectory *target/assembly*.

Publishing to the Maven Central Repository
------------------------------------------

A release is published with ``mvnw deploy``. The build signs the artifacts and uploads them to the `Central Portal <https://central.sonatype.com/>`_. The upload uses a Central Portal user token from your Maven ``settings.xml`` and a local GPG key. Publishing is manual: the deployment stays pending until you publish it in the portal, and the build waits until that happens.

Docker image
------------

A ready-to-run image is published on `Docker Hub <https://hub.docker.com/r/ait1/lablink-csv-client>`_ as ``ait1/lablink-csv-client:latest``:

.. code-block:: winbatch

   docker pull ait1/lablink-csv-client:latest

See :doc:`here <running>` for how to start the container.
Building the image locally with :github_blob:`docker/build.cmd <docker/build.cmd>` tags the result with the same name.


.. |MCR| replace:: Maven Central Repository
.. _MCR: https://search.maven.org/artifact/at.ac.ait.lablink.clients/csvclient
.. _Maven: https://maven.apache.org