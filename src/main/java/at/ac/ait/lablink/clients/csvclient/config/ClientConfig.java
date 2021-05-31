//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient.config;

import at.ac.ait.lablink.clients.csvclient.dispatcher.CsvDispatchTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;

/**
 * Helper class for configuring a CSV client.
 */
public class ClientConfig {

  /** The group name. */
  private String groupName;

  /** The client name. */
  private String clientName;

  /** The scenario name. */
  private String scenarioName;

  /** The client description. */
  private String clientDesc;

  /** The client shell flag. */
  private Boolean clientShell;

  /** The sync host properties url. */
  private String syncHostPropertiesUrl;

  /** The lab link properties url. */
  private String lablinkPropertiesUrl;

  /** The dispatch type. */
  private CsvDispatchTypes dispatchType;

  /** The CSV URL. */
  private String csvUrl;

  /** The CSV file format (for fixed interval dispatch). */
  private String csvFormat;

  /** The datetime format. */
  private String datetimeFormat;

  /** The start from data time. */
  private String startFromDataTime;

  /** The end at data time. */
  private String endAtDataTime;

  /**
   * Instantiates a new client configuration.
   * @param groupName the group name
   * @param clientName the client name
   * @param scenarioName the scenario name
   * @param clientDesc the client description
   * @param clientShell the client shell flag
   * @param csvFormat the CSV format
   * @param csvUrl the CSV URL
   * @param dt the dispatch type
   * @param dtFormat the datetime format
   * @param start the start
   * @param end the end
   */
  public ClientConfig(String groupName, String clientName, String scenarioName,
      String clientDesc, Boolean clientShell, String csvFormat, String csvUrl, 
      CsvDispatchTypes dt, String dtFormat, String start, String end) {
    this.setClientName(clientName);
    this.setGroupName(groupName);
    this.setScenarioName(scenarioName);
    this.setClientDescription(clientDesc);
    this.setClientShell(clientShell);
    this.setCsvFormat(csvFormat);
    this.setCsvUrl(csvUrl);
    this.setDispatchType(dt);
    this.setDatetimeFormat(dtFormat);
    this.setStartFromDataTime(start);
    this.setEndAtDataTime(end);
  }

  /**
   * Instantiates a new client configuration (default).
   */
  public ClientConfig() {
    this.setCsvFormat("DEFAULT");
    this.setClientDescription("CSV client (default config)");
    this.setClientShell(false);
  }

  /**
   * Gets the CSV file format.
   * @return the CSV file format
   */
  @JsonProperty("CsvFormat")
  public String getCsvFormat() {
    return csvFormat;
  }

  /**
   * Sets the format.
   * @param csvFormat the format to set
   */
  @JsonProperty("CsvFormat")
  public void setCsvFormat(String csvFormat) {
    this.csvFormat = csvFormat;
  }

  /**
   * Gets the group name.
   * @return the group name
   */
  @JsonProperty("GroupName")
  public String getGroupName() {
    return groupName;
  }

  /**
   * Sets the group name.
   * @param groupName the group name to set
   */
  @JsonProperty("GroupName")
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /**
   * Gets the client name.
   * @return the client name
   */
  @JsonProperty("ClientName")
  public String getClientName() {
    return clientName;
  }

  /**
   * Sets the client name.
   * @param clientName the client name to set
   */
  @JsonProperty("ClientName")
  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  /**
   * Gets the client description.
   * @return the client description
   */
  @JsonProperty("ClientDescription")
  public String getClientDescription() {
    return clientDesc;
  }

  /**
   * Sets the client description.
   * @param clientDesc the client description to set
   */
  @JsonProperty("ClientDescription")
  public void setClientDescription(String clientDesc) {
    this.clientDesc = clientDesc;
  }

  /**
   * Gets the client shell flag.
   * @return the client shell flag
   */
  @JsonProperty("ClientShell")
  public Boolean getClientShell() {
    return clientShell;
  }

  /**
   * Sets the client shell flag.
   * @param clientShell the client shell flag to set
   */
  @JsonProperty("ClientShell")
  public void setClientShell(Boolean clientShell) {
    this.clientShell = clientShell;
  }

  /**
   * Gets the scenario name.
   * @return the scenario name
   */
  @JsonProperty("ScenarioName")
  public String getScenarioName() {
    return scenarioName;
  }

  /**
   * Sets the scenario name.
   * @param scenarioName the scenario name to set
   */
  @JsonProperty("ScenarioName")
  public void setScenarioName(String scenarioName) {
    this.scenarioName = scenarioName;
  }

  /**
   * Gets the CSV URL.
   * @return the CSV URL
   */
  @JsonProperty("CsvUrl")
  public String getCsvUrl() {
    return csvUrl;
  }

  /**
   * Sets the CSV URL.
   * @param csvUrl the CSV URL to set
   */
  @JsonProperty("CsvUrl")
  public void setCsvUrl(String csvUrl) {
    this.csvUrl = csvUrl;
  }

  /**
   * Gets the dispatch type.
   * @return the dispatch type
   */
  @JsonProperty("DispatchType")
  public CsvDispatchTypes getDispatchType() {
    return dispatchType;
  }

  /**
   * Sets the dispatch type.
   * @param dispatchType the dispatch type to set
   */
  @JsonProperty("DispatchType")
  public void setDispatchType(CsvDispatchTypes dispatchType) {
    this.dispatchType = dispatchType;
  }

  /**
   * Gets the start from data time.
   * @return the start from data time
   */
  @JsonProperty("StartFromDataTime")
  public String getStartFromDataTime() {
    return startFromDataTime;
  }

  /**
   * Sets the start from data time.
   * @param startFromDataTime the start from data time to set
   */
  @JsonProperty("StartFromDataTime")
  public void setStartFromDataTime(String startFromDataTime) {
    this.startFromDataTime = startFromDataTime;
  }

  /**
   * Gets the end at data time.
   * @return the end at data time
   */
  @JsonProperty("EndAtDataTime")
  public String getEndAtDataTime() {
    return endAtDataTime;
  }

  /**
   * Sets the end at data time.
   * @param endAtDataTime the end at data time to set
   */
  @JsonProperty("EndAtDataTime")
  public void setEndAtDataTime(String endAtDataTime) {
    this.endAtDataTime = endAtDataTime;
  }

  /**
   * Gets the start timestamp.
   * @return the start timestamp
   * @throws ParseException parse exception
   */
  @JsonIgnore
  public long getStartTimestamp() throws ParseException {
    return (new java.text.SimpleDateFormat(this.getDatetimeFormat())
        .parse(this.getStartFromDataTime()).getTime());
  }


  /**
   * Gets the end timestamp.
   * @return the end timestamp
   * @throws ParseException the parse exception
   */
  @JsonIgnore
  public long getEndTimestamp() throws ParseException {
    return (new java.text.SimpleDateFormat(this.getDatetimeFormat()).parse(this.getEndAtDataTime())
        .getTime());
  }

  /**
   * Gets the datetime format.
   * @return the datetime format
   */
  @JsonProperty("DatetimeFormat")
  public String getDatetimeFormat() {
    return datetimeFormat;
  }

  /**
   * Sets the datetime format.
   * @param datetimeFormat the datetime format to set
   */
  @JsonProperty("DatetimeFormat")
  public void setDatetimeFormat(String datetimeFormat) {
    this.datetimeFormat = datetimeFormat;
  }

  /**
   * Get URL for retrieving properties of sync host.
   * @return the sync host properties URL
   */
  @JsonProperty("SyncHostPropertiesUrl")
  public String getSyncHostPropertiesUrl() {
    return syncHostPropertiesUrl;
  }

  /**
   * Set URL for retrieving properties of sync host.
   * @param syncHostPropertiesUrl the sync host properties URL to set
   */
  @JsonProperty("SyncHostPropertiesUrl")
  public void setSyncHostPropertiesUrl(String syncHostPropertiesUrl) {
    this.syncHostPropertiesUrl = syncHostPropertiesUrl;
  }

  /**
   * Get URL for retrieving properties of Lablink.
   * @return the Lablink properties URL
   */
  @JsonProperty("LablinkPropertiesUrl")
  public String getLablinkPropertiesUrl() {
    return lablinkPropertiesUrl;
  }

  /**
   * Set URL for retrieving properties of Lablink.
   * @param lablinkPropertiesUrl the Lablink properties URL to set
   */
  @JsonProperty("LablinkPropertiesUrl")
  public void setLablinkPropertiesUrl(String lablinkPropertiesUrl) {
    this.lablinkPropertiesUrl = lablinkPropertiesUrl;
  }
}
