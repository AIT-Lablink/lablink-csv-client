//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient;

import at.ac.ait.lablink.clients.csvclient.config.CsvConfig;

import at.ac.ait.lablink.clients.csvclient.dispatcher.CsvDispatchTypes;
import at.ac.ait.lablink.clients.csvclient.dispatcher.CsvDispatcherFactory;
import at.ac.ait.lablink.clients.csvclient.dispatcher.IColumnDispatcher;

import at.ac.ait.lablink.clients.csvclient.services.DataServiceBoolean;
import at.ac.ait.lablink.clients.csvclient.services.DataServiceDouble;
import at.ac.ait.lablink.clients.csvclient.services.DataServiceLong;
import at.ac.ait.lablink.clients.csvclient.services.DataServiceString;

import at.ac.ait.lablink.core.client.ci.mqtt.impl.MqttCommInterfaceUtility;
import at.ac.ait.lablink.core.client.ex.ClientNotReadyException;
import at.ac.ait.lablink.core.client.ex.CommInterfaceNotSupportedException;
import at.ac.ait.lablink.core.client.ex.DataTypeNotSupportedException;
import at.ac.ait.lablink.core.client.ex.InvalidCastForServiceValueException;
import at.ac.ait.lablink.core.client.ex.NoServicesInClientLogicException;
import at.ac.ait.lablink.core.client.ex.NoSuchCommInterfaceException;
import at.ac.ait.lablink.core.client.ex.ServiceIsNotRegisteredWithClientException;
import at.ac.ait.lablink.core.client.ex.ServiceTypeDoesNotMatchClientType;
import at.ac.ait.lablink.core.client.impl.LlClient;
import at.ac.ait.lablink.core.service.IImplementedService;
import at.ac.ait.lablink.core.service.LlService;
import at.ac.ait.lablink.core.utility.Utility;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;


/**
 * Class CsvClient.
 */
public class CsvClient {

  /** Logger. */
  protected static final Logger logger = LogManager.getLogger("CsvClient");

  private static final String CLI_CONF_FLAG = "c";
  private static final String CLI_CONF_LONG_FLAG = "config";
  private static final String CLI_TEST_FLAG = "w";

  /** Flag for testing (write config and exit). */
  private static boolean writeConfigAndExitFlag;

  /** CSV client configuration. */
  private CsvConfig config;

  /** CSV columns. */
  private Map<String, CsvColumn> columns = new HashMap<String, CsvColumn>();

  /** Dispatcher tasks. */
  private Map<String, IColumnDispatcher> tasks = new HashMap<String, IColumnDispatcher>();

  /** Lablink client instance. */
  protected LlClient client;

  /**
   * The main method.
   * @param args arguments to main method
   * @throws at.ac.ait.lablink.core.client.ex.ClientNotReadyException
   *   client not ready
   * @throws at.ac.ait.lablink.core.client.ex.CommInterfaceNotSupportedException
   *   comm interface not supported
   * @throws at.ac.ait.lablink.core.client.ex.DataTypeNotSupportedException
   *   data type not supported
   * @throws at.ac.ait.lablink.core.client.ex.InvalidCastForServiceValueException
   *   invalid cast for service value
   * @throws at.ac.ait.lablink.core.client.ex.NoServicesInClientLogicException
   *   no services in client logic
   * @throws at.ac.ait.lablink.core.client.ex.NoSuchCommInterfaceException
   *   no such comm interface
   * @throws at.ac.ait.lablink.core.client.ex.ServiceIsNotRegisteredWithClientException
   *   service is not registered with client
   * @throws at.ac.ait.lablink.core.client.ex.ServiceTypeDoesNotMatchClientType
   *   service type does not match client type
   * @throws org.apache.commons.cli.ParseException
   *   parse exception
   * @throws org.apache.commons.configuration.ConfigurationException
   *   configuration error
   * @throws java.io.IOException
   *   IO error
   * @throws java.net.MalformedURLException
   *   malformed URL
   * @throws java.util.NoSuchElementException
   *   no such element
   */
  public static void main( String[] args ) throws
      at.ac.ait.lablink.core.client.ex.ClientNotReadyException,
      at.ac.ait.lablink.core.client.ex.CommInterfaceNotSupportedException,
      at.ac.ait.lablink.core.client.ex.DataTypeNotSupportedException,
      at.ac.ait.lablink.core.client.ex.InvalidCastForServiceValueException,
      at.ac.ait.lablink.core.client.ex.NoServicesInClientLogicException,
      at.ac.ait.lablink.core.client.ex.NoSuchCommInterfaceException,
      at.ac.ait.lablink.core.client.ex.ServiceIsNotRegisteredWithClientException,
      at.ac.ait.lablink.core.client.ex.ServiceTypeDoesNotMatchClientType,
      org.apache.commons.cli.ParseException,
      org.apache.commons.configuration.ConfigurationException,
      java.io.IOException,
      java.net.MalformedURLException,
      java.util.NoSuchElementException {

    // Retrieve configuration URL.
    URL configUrl = getConfigUrl(args);

    // Instantiate CSV client.
    CsvClient csvClient = new CsvClient(configUrl);

    if (true == getWriteConfigAndExitFlag()) { // Run a test (write client config and exit).
      writeConfigAndExit(csvClient);
    } else { // Start the default event loop for the Lablink CSV client.
      startEventLoop(csvClient);
    }
  }

  /**
   * Parse the command line arguments to retrieve the configuration.
   * @param args arguments to main method
   * @return configuration URL
   * @throws org.apache.commons.cli.ParseException parse exception
   * @throws org.apache.commons.configuration.ConfigurationException configuration error
   * @throws java.io.IOException IO error
   * @throws java.net.MalformedURLException malformed URL
   * @throws java.util.NoSuchElementException no such element
   */
  protected static URL getConfigUrl(String[] args) throws
      org.apache.commons.cli.ParseException,
      org.apache.commons.configuration.ConfigurationException,
      java.io.IOException,
      java.net.MalformedURLException,
      java.util.NoSuchElementException {

    // Define command line option.
    Options cliOptions = new Options();
    cliOptions.addOption(CLI_CONF_FLAG, CLI_CONF_LONG_FLAG, true, "CSV client configuration URI");
    cliOptions.addOption(CLI_TEST_FLAG, false, "write config and exit");

    // Parse command line options.
    CommandLineParser parser = new BasicParser();
    CommandLine commandLine = parser.parse(cliOptions, args);

    // Set flag for testing (write config and exit).
    writeConfigAndExitFlag = commandLine.hasOption(CLI_TEST_FLAG);

    // Retrieve configuration URI from command line.
    String configUri = commandLine.getOptionValue(CLI_CONF_FLAG);

    // Get configuration URL, resolve environment variables if necessary.
    return new URL(Utility.parseWithEnvironmentVariable(configUri));
  }


  /**
   * Start the main event loop for the Lablink CSV client.
   * @param csvClient Lablink CSV client instance
   */
  protected static void startEventLoop(CsvClient csvClient) {

    CompletableFuture<CsvClient> future = new CompletableFuture<>();

    try {
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        logger.info("shutting down CSV client");
        future.complete(csvClient);
      }));

      future.get();

    } catch (Throwable th) {
      logger.error("Error running client: {}", th.getMessage(), th);
      future.completeExceptionally(th);
    }
  }

  /**
   * Returns true if this is a test run.
   * @return test flag
   */
  protected static boolean getWriteConfigAndExitFlag() {
    return writeConfigAndExitFlag;
  }

  /**
   * Run a test (write config and exit).
   * @param csvClient a CSV client instance
   */
  protected static void writeConfigAndExit( CsvClient csvClient ) {

    logger.info("run a test (write config and exit)");

    String clientConfig = csvClient.getYellowPageJson();

    try {
      Files.write(Paths.get("client_config.json"), clientConfig.getBytes());
    } catch (IOException ex) {
      logger.error(ex);
    }

    System.exit(0);
  }

  /**
   * Returns the yellow pages info (JSON format) of the plotter' Lablink client.
   * @return title of the plotter window
   */
  public String getYellowPageJson() {
    return this.client.getYellowPageJson();
  }

  /**
   * Constructor.
   * @param configUrl configuration URL
   * @throws at.ac.ait.lablink.core.client.ex.ClientNotReadyException
   *   client not ready
   * @throws at.ac.ait.lablink.core.client.ex.CommInterfaceNotSupportedException
   *   comm interface not supported
   * @throws at.ac.ait.lablink.core.client.ex.DataTypeNotSupportedException
   *   data type not supported
   * @throws at.ac.ait.lablink.core.client.ex.InvalidCastForServiceValueException
   *   invalid cast for service value
   * @throws at.ac.ait.lablink.core.client.ex.NoServicesInClientLogicException
   *   no services in client logic
   * @throws at.ac.ait.lablink.core.client.ex.NoSuchCommInterfaceException
   *   no such comm interface
   * @throws at.ac.ait.lablink.core.client.ex.ServiceIsNotRegisteredWithClientException
   *   service is not registered with client
   * @throws at.ac.ait.lablink.core.client.ex.ServiceTypeDoesNotMatchClientType
   *   service type does not match client type
   * @throws org.apache.commons.configuration.ConfigurationException
   *   configuration error
   * @throws java.io.IOException
   *   IO error
   * @throws java.util.NoSuchElementException
   *   no such element
   */
  public CsvClient(URL configUrl) throws
      at.ac.ait.lablink.core.client.ex.ClientNotReadyException,
      at.ac.ait.lablink.core.client.ex.CommInterfaceNotSupportedException,
      at.ac.ait.lablink.core.client.ex.DataTypeNotSupportedException,
      at.ac.ait.lablink.core.client.ex.InvalidCastForServiceValueException,
      at.ac.ait.lablink.core.client.ex.NoServicesInClientLogicException,
      at.ac.ait.lablink.core.client.ex.NoSuchCommInterfaceException,
      at.ac.ait.lablink.core.client.ex.ServiceIsNotRegisteredWithClientException,
      at.ac.ait.lablink.core.client.ex.ServiceTypeDoesNotMatchClientType,
      org.apache.commons.configuration.ConfigurationException,
      java.io.IOException,
      java.util.NoSuchElementException {

    // Parse configuration.
    logger.info("Reading configuration...");
    ObjectMapper jsonObjectMapper = new ObjectMapper();
    this.config = jsonObjectMapper.readValue(configUrl, CsvConfig.class);

    // Basic client configuration (incl. data service definition).
    configureLablinkClient();

    // Create the client.
    client.create();

    // Initialize the client.
    client.init();

    // Start the client.
    client.start();

    // Configure the CSV outputs (read CSV data and associate it to data services).
    configureCsvOutputs();
  }

  /**
   * Configure the Lablink client.
   * @throws at.ac.ait.lablink.core.client.ex.CommInterfaceNotSupportedException
   *   comm interface not supported
   */
  private void configureLablinkClient() throws
      at.ac.ait.lablink.core.client.ex.CommInterfaceNotSupportedException {

    logger.info("Basic cient configuration ...");

    // General Lablink properties configuration.
    String llPropUri = this.config.getClientConfig().getLablinkPropertiesUrl();

    // Sync properties configuration.
    String llSyncUri = this.config.getClientConfig().getSyncHostPropertiesUrl();

    // Scenario name.
    String scenarioName = this.config.getClientConfig().getScenarioName();

    // Group name.
    String groupName = this.config.getClientConfig().getGroupName();

    // Client name.
    String clientName = this.config.getClientConfig().getClientName();

    // Client description (optional).
    String clientDesc = this.config.getClientConfig().getClientDescription();

    // Activate shell (optional).
    boolean giveShell = this.config.getClientConfig().getClientShell();

    boolean isPseudo = false;

    // Declare the client with required interface.
    client = new LlClient( clientName,
        MqttCommInterfaceUtility.SP_ACCESS_NAME, giveShell, isPseudo );

    // Specify client configuration (no sync host).
    MqttCommInterfaceUtility.addClientProperties( client, clientDesc,
        scenarioName, groupName, clientName, llPropUri, llSyncUri, null );

    Arrays.stream(this.config.getMeasurementConfig()).forEach((measurement) -> {
      addDataService(
          measurement.getDpName(), measurement.getDatatype(), measurement.getUnit()
      );
    });
  }

  /**
   * Read the CSV data and associate it to the client's output data services.
   * @throws IOException signals that an I/O exception has occurred.
   */
  private void configureCsvOutputs() throws
      java.io.IOException {

    Arrays.stream(this.config.getMeasurementConfig()).forEach((measurement) -> {
      logger.info("Creating Map for [{}]", measurement.getColname());
      this.columns.put(measurement.getColname(), new CsvColumn(measurement));
    });

    CsvDispatchTypes dispatchType = this.config.getClientConfig().getDispatchType();
    if (dispatchType.equals(CsvDispatchTypes.FIXED)) {
      loadDataFixed();
    } else if (dispatchType.equals(CsvDispatchTypes.TIME_VARIANT)) {
      loadDataTimeVariant();
    } else {
      throw new IllegalArgumentException("Configuration error: dispatch type not supported.");
    }

    this.columns.forEach((id, column) -> {
      logger.info("Step size: {}, Scale Factor: {}", column.getStep(),
          column.getMeasure().getScaleFactor());
      String dpName = column.getMeasure().getDpName();

      // Retrieve implemented output data service from Lablink client.
      IImplementedService dataService = client.getImplementedServices().get(dpName);

      this.tasks.put(id, CsvDispatcherFactory.getDispatcher(
          dispatchType, column, dataService, column.getMeasure().getScaleFactor()
      ));
    });
  }

  /**
   * Create a new data service for this client.
   * @param serviceId name of input signal
   * @param dataType type of data associated to service (boolean, double, long, string)
   * @param unit unit associated to input signal
   * @return reference to the new service added to the Lablink client
   */
  protected LlService addDataService(String serviceId, CsvDataTypes dataType, String unit) {

    // Create new data service.
    LlService dataService = null;

    switch (dataType) {
      case DOUBLE:
        dataService = new DataServiceDouble();
        break;
      case INT:
      case LONG:
        dataService = new DataServiceLong();
        break;
      case BOOLEAN:
        dataService = new DataServiceBoolean();
        break;
      case STRING:
        dataService = new DataServiceString();
        break;
      default:
        throw new IllegalArgumentException(
            String.format("Data service type not supported: '%1$d'", dataType)
        );
    }

    // Set data service name.
    dataService.setName(serviceId);

    // Data service description.
    String serviceDesc = String.format("CSV client data service %1$s (%2$s)",
        serviceId, dataType.getId());

    // Specify data service properties.
    MqttCommInterfaceUtility.addDataPointProperties(dataService,
        serviceId, serviceDesc, serviceId, unit);

    try { // Add service to the client.
      this.client.addService(dataService);
    } catch ( ServiceTypeDoesNotMatchClientType ex ) {
      logger.error(ex.getMessage());
      return null;
    }

    return dataService;
  }

  /**
   * Load CSV data (fixed format).
   * @throws IOException signals that an I/O exception has occurred.
   */
  private void loadDataFixed() throws IOException {

    Iterable<CSVRecord> records = read();

    records.forEach((record) -> {
      this.columns.forEach((id, column) -> {
        column.setRow(record.get(id));
      });
    });
  }

  /**
   * Load CSV data (time-variant format).
   * @throws IOException signals that an I/O exception has occurred.
   */
  private void loadDataTimeVariant() throws IOException {

    // Read raw CSV data.
    Iterable<CSVRecord> records = read();

    long numRecords = 0;

    // Load data into columns
    for (CSVRecord record : records) {
      ++numRecords;
      String row = record.get(0);
      String[] data = row.split(";");
      String key = data[0] + "." + data[1];
      if (this.columns.containsKey(key)) {
        CsvColumn col = this.columns.get(key);
        col.setRow(data[3], Long.parseLong(data[2]));
      }
    }

    logger.info(numRecords + " rows loaded from CSV source.");
  }

  /**
   * Read raw CSV data from file.
   * @return iterable of CSV records
   * @throws IOException signals that an I/O exception has occurred.
   */
  private Iterable<CSVRecord> read() throws IOException {
    // Get URL to CSV data and open input stream reader.
    URL csv = new URL(this.config.getClientConfig().getCsvUrl());
    InputStreamReader isr = new InputStreamReader(csv.openStream());

    // Convert format to capitalized string.
    String strFormat = this.config.getClientConfig().getCsvFormat().toLowerCase();
    strFormat = strFormat.substring(0, 1).toUpperCase() + strFormat.substring(1);

    // Instantiate CSV formatter.
    CSVFormat csvFormat = CSVFormat.valueOf(strFormat).withHeader();
    
    // Parse and return CSV data.
    return csvFormat.parse(isr);
  }
}
