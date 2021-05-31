//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient.dispatcher;

import at.ac.ait.lablink.clients.csvclient.CsvColumn;

import at.ac.ait.lablink.core.service.IImplementedService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CsvDispatcherFactory {

  private static final Logger logger = LogManager.getLogger("CsvDispatcherFactory");

  /**
   * CSV dispatcher factory.
   * @param dt dispatch type
   * @param col CSV column
   * @param ids implemented data service
   * @param scalefactor scale factor
   * @return dispatcher instance
   */
  public static IColumnDispatcher getDispatcher(CsvDispatchTypes dt, CsvColumn col, 
      IImplementedService ids, double scalefactor) {

    IColumnDispatcher dispatcher = null;

    if (dt.equals(CsvDispatchTypes.FIXED)) {
      dispatcher = new CsvDispatcherFixedInterval(col, ids);
    } else if (dt.equals(CsvDispatchTypes.TIME_VARIANT)) {
      dispatcher = new CsvDispatcherTimeVariant(col, ids, scalefactor);
      logger.debug("Scale Factor: " + scalefactor);
    } else {
      throw new IllegalArgumentException("Dispatcher type " + dt + " not supported.");
    }

    return dispatcher;
  }

}
