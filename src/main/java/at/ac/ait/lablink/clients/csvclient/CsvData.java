//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient;

/**
 * Class CsvData is used for representing data entries from CSV files.
 */
public class CsvData {

  /** The value. */
  private String value;

  /** The step. */
  private int step;

  /** The timestamp. */
  private long timestamp;

  /**
   * Instantiates a new csv data.
   */
  public CsvData() {

  }

  /**
   * Instantiates a new csv data.
   *
   * @param val the val
   * @param ts the ts
   */
  public CsvData(String val, long ts) {
    this.setTimestamp(ts);
    this.setValue(val);
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value.
   *
   * @param value the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Gets the step.
   *
   * @return the step
   */
  public int getStep() {
    return step;
  }

  /**
   * Sets the step.
   *
   * @param step the step to set
   */
  public void setStep(int step) {
    this.step = step;
  }


  /**
   * Gets the timestamp.
   *
   * @return the timestamp
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the timestamp.
   *
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

}
