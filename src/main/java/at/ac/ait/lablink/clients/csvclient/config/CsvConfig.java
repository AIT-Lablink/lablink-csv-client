//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient.config;

import at.ac.ait.lablink.clients.csvclient.dispatcher.CsvDispatchTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data structure for CSV client configuration.
 */
public class CsvConfig {

  /** The client configuration. */
  private ClientConfig clientConfig;

  /** The measurement configurations. */
  private MeasurementConfig[] measurementConfig;

  /**
   * Instantiates a new CSV configuration.
   * @param clientConfig client configuration
   * @param measurementConfig measurement configurations
   */
  public CsvConfig(ClientConfig clientConfig, MeasurementConfig[] measurementConfig) {
    this.setClientConfig(clientConfig);
    this.setMeasurementConfig(measurementConfig);
  }

  /**
   * Instantiates a new CSV configuration (default).
   */
  public CsvConfig() {}

  /**
   * Get the client configuration.
   * @return measurement configurations
   */
  @JsonProperty("Client")
  public ClientConfig getClientConfig() {
    return clientConfig;
  }

  /**
   * Sets the client configuration.
   * @param clientConfig client configuration
   */
  @JsonProperty("Client")
  public void setClientConfig(ClientConfig clientConfig) {
    this.clientConfig = clientConfig;
  }

  /**
   * Get the measurement configurations.
   * @return measurement configurations
   */
  @JsonProperty("Measurements")
  public MeasurementConfig[] getMeasurementConfig() {
    return measurementConfig;
  }

  /**
   * Sets the measurement configurations.
   * @param measurementConfig measurement configurations
   */
  @JsonProperty("Measurements")
  public void setMeasurementConfig(MeasurementConfig[] measurementConfig) {
    this.measurementConfig = measurementConfig;
  }
}
