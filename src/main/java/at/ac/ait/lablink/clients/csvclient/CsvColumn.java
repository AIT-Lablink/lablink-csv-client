//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient;

import at.ac.ait.lablink.clients.csvclient.config.CsvConfig;
import at.ac.ait.lablink.clients.csvclient.config.MeasurementConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Class CsvColumn represents a single column in a CSV file. It contains the data and settings
 * for that column.
 */
public class CsvColumn {

  /** The data. */
  private Map<Long, CsvData> data = new HashMap<Long, CsvData>();

  /** The settings. */
  private MeasurementConfig settings = new MeasurementConfig();

  /** The row in index. */
  private long rowInIndex = 1;

  /** The row out index. */
  private long rowOutIndex = 1;

  /** The delay. */
  private int delay;

  /**
   * Instantiates a new csv column.
   */
  public CsvColumn() {

  }

  /**
   * Instantiates a new csv column.
   *
   * @param data the data
   * @param settings the settings
   */
  public CsvColumn(Map<Long, CsvData> data, MeasurementConfig settings) {
    this.data = data;
    this.settings = settings;
  }

  /**
   * Instantiates a new csv column.
   *
   * @param settings the settings
   */
  public CsvColumn(MeasurementConfig settings) {
    this.settings = settings;

  }

  /**
   * Gets the start delay.
   *
   * @return the start delay
   */
  public long getStartDelay() {
    return this.settings.getStartDelay();
  }

  /**
   * Gets the step.
   *
   * @return the step
   */
  public long getStep() {
    return this.settings.getStep();
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return this.settings.getColname();
  }

  /**
   * Gets the total rows.
   *
   * @return the total rows
   */
  public long getTotalRows() {
    return this.data.size();
  }

  /**
   * Gets the row data a the specified index.
   *
   * @param index the index
   * @return the row data
   */
  public String getRow(long index) {
    String value = "";
    if (index < this.getTotalRows()) {
      value = this.data.get(index).getValue();
    } else {
      throw new IllegalArgumentException("The row index is too high!");
    }
    return value;
  }


  /**
   * Gets the row.
   *
   * @return the row
   */
  public String getRow() {
    this.setDelay(this.data.get(rowOutIndex).getStep());
    return this.data.get(rowOutIndex++).getValue();
  }

  /**
   * Sets the row (fixed data).
   *
   * @param val the new row
   */
  public void setRow(String val) {
    this.data.put(rowInIndex++, new CsvData(val, 0));
  }

  /**
   * Sets the row (time variant data).
   *
   * @param val the val
   * @param ts the ts
   */
  public void setRow(String val, long ts) {
    this.data.put(rowInIndex++, new CsvData(val, ts));
  }

  /**
   * Gets the measure.
   *
   * @return the measure
   */
  public MeasurementConfig getMeasure() {
    return settings;
  }

  /**
   * Gets the delay.
   *
   * @param index the index
   * @return the delay
   */
  public long getDelay(long index) {
    long value = 0;
    if (index < this.getTotalRows() && index > 0) {
      if (index == 1) {
        value = 0;
      } else {
        value = this.data.get(index).getTimestamp() - this.data.get(index - 1).getTimestamp();
      }

    } else {
      throw new IllegalArgumentException("The row index is not valid.");
    }

    return value;
  }

  /**
   * Gets the delay.
   *
   * @return the delay
   */
  public int getDelay() {
    return delay;
  }

  /**
   * Sets the delay.
   *
   * @param delay the delay to set
   */
  public void setDelay(int delay) {
    this.delay = delay;
  }
}

