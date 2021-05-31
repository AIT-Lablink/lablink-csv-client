//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient.dispatcher;

public class CsvTypeUtil {

  public static Object identity(Object obj) {
    return obj;
  }

  public static Boolean stringToBoolean(String str) {
    return Boolean.valueOf(str);
  }

  public static Double stringToDouble(String str) {
    return Double.valueOf(str);
  }

  public static Long stringToLong(String str) {
    return Long.valueOf(str);
  }
}
