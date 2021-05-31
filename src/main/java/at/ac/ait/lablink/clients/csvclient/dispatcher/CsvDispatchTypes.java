//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient.dispatcher;

/**
 * Enum CsvDispatchTypes.
 */
public enum CsvDispatchTypes {

  /** The holding registers. */
  FIXED("FIXED"), TIME_VARIANT("TIME_VARIANT");

  /** The value. */
  private String value;

  /**
   * Instantiates a new simulator types.
   * @param val the val
   */
  private CsvDispatchTypes(String val) {
    this.value = val.toUpperCase();
  }

  /**
   * Gets the id.
   * @return the id
   */
  public String getId() {
    return this.value;
  }

  /**
   * From id.
   * @param id the id
   * @return the modbus item types
   */
  public static CsvDispatchTypes fromId(String id) {
    for (CsvDispatchTypes type : CsvDispatchTypes.values()) {
      if (type.getId().equals(id)) {
        return type;
      }
    }
    System.err.println("Invalid Id '" + id + "'.");
    return null;
  }
}
