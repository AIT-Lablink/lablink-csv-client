//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient.config;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class Duration.
 */
public class Duration {

  /** The ms2sec. */
  private static final long MS2SEC = 1000;

  /** The ms2min. */
  private static final long MS2MIN = 60000;

  /** The value. */
  private long value; // value is milliseconds

  /** The unit. */
  private Units unit;

  /**
   * Units.
   */
  private enum Units {

    /** Time unit of milliseconds. */
    ms,

    /** Time unit of seconds (SI). */
    s,

    /** Time unit of seconds. */
    sec,

    /** Time unit of minutes. */
    min
  }

  /**
   * Instantiates a new duration.
   *
   * @param val the val
   */

  public Duration(String val) {
    this.setValue(val);
  }

  public Duration() {
    this.setValue("0 ms");
  }

  /**
   * Sets the value.
   *
   * @param val the new value
   * @throws IllegalArgumentException the illegal argument exception
   */
  public void setValue(String val) throws IllegalArgumentException {
    String[] part = val.split("(?=\\D)(?<=\\d)");
    Long dummyL = Long.parseLong(part[0]);
    String unit = part[1].replace(" ", "");

    try {
      this.unit = Units.valueOf(unit);
    } catch (Exception ex) {
      throw new IllegalArgumentException("The unit is not supported.");
    }

    switch (this.unit) {
      case ms:
        this.value = dummyL;
        break;
      case s:
      case sec:
        this.value = dummyL * MS2SEC;
        break;
      case min:
        this.value = dummyL * MS2MIN;
        break;
      default:
        this.value = 0;
        throw new IllegalArgumentException("The unit '" + unit + "' is not supported.");
    }
  }

  /**
   * Gets the milliseconds.
   *
   * @return the milliseconds
   */
  @JsonIgnore
  public long getMilliseconds() {
    return this.value;
  }

  /**
   * Gets the seconds.
   *
   * @return the seconds
   */
  @JsonIgnore
  public long getSeconds() {
    return this.value / MS2SEC;
  }

  /**
   * Gets the minutes.
   *
   * @return the minutes
   */
  @JsonIgnore
  public long getMinutes() {
    return this.value / MS2MIN;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public String getValue() {
    return String.valueOf(this.value) + " ms";
  }
}
