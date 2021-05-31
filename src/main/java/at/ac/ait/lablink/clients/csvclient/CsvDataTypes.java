//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient;

public enum CsvDataTypes {
  /** The holding registers. */
  INT("INT"), DOUBLE("DOUBLE"), STRING("STRING"), BOOLEAN("BOOL"), LONG("LONG");

  /** The value. */
  private String value;

  /**
   * Instantiates a new simulator types.
   *
   * @param val the val
   */
  private CsvDataTypes(String val) {
    this.value = val.toUpperCase();
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return this.value;
  }

  /**
   * From id.
   *
   * @param id id
   * @return CSV data type
   */
  public static CsvDataTypes fromId(String id) {
    for (CsvDataTypes type : CsvDataTypes.values()) {
      if (type.getId().equals(id)) {
        return type;
      }
    }
    System.err.println("Invalid Id '" + id + "'.");
    return null;
  }
}
