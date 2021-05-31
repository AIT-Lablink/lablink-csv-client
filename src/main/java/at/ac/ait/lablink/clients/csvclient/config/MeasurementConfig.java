//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient.config;

import at.ac.ait.lablink.clients.csvclient.CsvDataTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class MeasurementConfig.
 */
public class MeasurementConfig {

  /** The interval. */
  private Duration interval;

  /** The start after. */
  private Duration startAfter;

  /** The colname. */
  private String colname;

  /** The datatype. */
  private CsvDataTypes datatype;

  /** The dp name. */
  private String dpName;

  /** The scale factor. */
  private double scaleFactor = 1.0;

  /** The unit. */
  private String unit;

  /**
   * Instantiates a new csv measurements.
   *
   * @param colname CSV column name
   * @param dpname data point name
   * @param inter time interval
   * @param startafter delay
   * @param dtype data point type
   * @param sf scale factor
   * @param unit data point unit
   */
  public MeasurementConfig(String colname, String dpname, String inter, String startafter,
      CsvDataTypes dtype, double sf, String unit) {
    this.setColname(colname);
    this.setDatatype(dtype);
    this.setDpName(dpname);
    this.setScaleFactor(sf);
    this.setUnit(unit);
    this.interval = new Duration(inter);
    this.startAfter = new Duration(startafter);
  }

  /**
   * Instantiates a new csv measurement.
   */
  public MeasurementConfig() {
    this.setUnit("NONE");
    this.interval = new Duration();
    this.startAfter = new Duration();
  }

  /**
   * Gets the interval.
   * @return the interval
   */
  @JsonProperty("Interval")
  public String getInterval() {
    return interval.getValue();
  }

  /**
   * Sets the interval.
   * @param inter the new interval
   */
  @JsonProperty("Interval")
  public void setInterval(String inter) {
    this.interval.setValue(inter);
  }

  /**
   * Gets the colname.
   * @return the colname
   */
  @JsonProperty("ColName")
  public String getColname() {
    return colname;
  }

  /**
   * Sets the colname.
   * @param colname the colname to set
   */
  @JsonProperty("ColName")
  public void setColname(String colname) {
    this.colname = colname;
  }

  /**
   * Gets the data type.
   * @return the data type
   */
  @JsonProperty("DataType")
  public CsvDataTypes getDatatype() {
    return datatype;
  }

  /**
   * Sets the data type.
   * @param datatype the data type to set
   */
  @JsonProperty("DataType")
  public void setDatatype(CsvDataTypes datatype) {
    this.datatype = datatype;
  }

  /**
   * Gets the data type unit.
   * @return the data type unit
   */
  @JsonProperty("Unit")
  public String getUnit() {
    return unit;
  }

  /**
   * Sets the data type unit.
   * @param unit the data type unit to set
   */
  @JsonProperty("Unit")
  public void setUnit(String unit) {
    this.unit = unit;
  }

  /**
   * Gets the start after.
   * @return the start after
   */
  @JsonProperty("StartAfter")
  public String getStartAfter() {
    return startAfter.getValue();
  }

  /**
   * Sets the start after.
   * @param start the new start after
   */
  @JsonProperty("StartAfter")
  public void setStartAfter(String start) {
    this.startAfter.setValue(start);
  }

  /**
   * Gets the start delay.
   * @return the start delay
   */
  @JsonIgnore
  public long getStartDelay() {
    return this.startAfter.getMilliseconds();
  }

  /**
   * Gets the step.
   * @return the step
   */
  @JsonIgnore
  public long getStep() {
    return this.interval.getMilliseconds();
  }

  /**
   * Gets the dp name.
   * @return the data point name
   */
  @JsonProperty("DpName")
  public String getDpName() {
    return dpName;
  }

  /**
   * Sets the dp name.
   * @param dpName the data point name to set
   */
  @JsonProperty("DpName")
  public void setDpName(String dpName) {
    this.dpName = dpName;
  }

  /**
   * Gets the scale factor.
   * @return the scale factor
   */
  @JsonProperty("ScaleFactor")
  public double getScaleFactor() {
    return scaleFactor;
  }

  /**
   * Sets the scale factor.
   * @param sf the new scale factor
   */
  @JsonProperty("ScaleFactor")
  public void setScaleFactor(double sf) {
    if (sf > 0) {
      this.scaleFactor = sf;
    } else {
      this.scaleFactor = 1.0;
    }
  }
}
