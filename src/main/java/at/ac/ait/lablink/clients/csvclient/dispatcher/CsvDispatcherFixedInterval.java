//
// Copyright (c) AIT Austrian Institute of Technology GmbH.
// Distributed under the terms of the Modified BSD License.
//

package at.ac.ait.lablink.clients.csvclient.dispatcher;

import at.ac.ait.lablink.clients.csvclient.CsvColumn;

import at.ac.ait.lablink.core.service.IImplementedService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Class CsvDispatcherFixedInterval.
 */
public class CsvDispatcherFixedInterval implements IColumnDispatcher {

  /** The column. */
  private CsvColumn column;

  /** The service. */
  private ScheduledExecutorService service;

  /** The implemented data service. */
  private IImplementedService dataService;

  /** Type casting function (from CSV string to correct data type). */
  protected Function<String, Object> dataTypeCaster;

  /** The task. */
  private Runnable task;

  /** The current row. */
  private long curRow = 0;

  /** The maximum row. */
  private long maxRows = 0;

  /** Logger. */
  private static final Logger logger = LogManager.getLogger("CsvDispatcherFixedInterval");

  /**
   * Instantiates a new CSV dispatcher.
   * @param col CSV column containing the data and the settings
   * @param ids implemented data service
   */
  public CsvDispatcherFixedInterval(CsvColumn col, IImplementedService ids) {
    this.column = col;
    this.maxRows = this.column.getTotalRows();
    this.setDataService(ids);
    this.createTask();
    this.createService();
  }

  /**
   * Create task.
   */
  private void createTask() {
    this.task = new Runnable() {
      public void run() {
        runJob();
      }
    };
  }

  /**
   * Run job.
   */
  private void runJob() {

    ++this.curRow;

    if (this.curRow > this.maxRows) {
      logger.info(
          "All rows processed; shuting down service for column '" + this.column.getName() + "'");
      service.shutdownNow();
    }
    String val = this.column.getRow(curRow);
    logger.debug("Sending Row # " + (curRow) + "/" + this.maxRows + ", data < " + val + " >, for "
        + this.column.getName());
    this.setValue(val);
  }

  @SuppressWarnings("unchecked")
  private void setValue(String val) {
    this.dataService.setValue(dataTypeCaster.apply(val));
  }

  /**
   * Create service.
   */
  private void createService() {
    service = Executors.newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(task, this.column.getStartDelay(), this.column.getStep(),
        TimeUnit.MILLISECONDS);
  }

  /**
   * Sets the data data service.
   *
   * @param ids implemented data service
   */
  private void setDataService(IImplementedService ids) {
    this.dataService = ids;

    Class dataTypeClass = ids.getServiceDataTypeClass();
    if (dataTypeClass.equals(Double.class)) {
      dataTypeCaster = CsvTypeUtil::stringToDouble;
    } else if (dataTypeClass.equals(Long.class)) {
      dataTypeCaster = CsvTypeUtil::stringToLong;
    } else if (dataTypeClass.equals(Boolean.class)) {
      dataTypeCaster = CsvTypeUtil::stringToBoolean;
    } else if (dataTypeClass.equals(String.class)) {
      dataTypeCaster = CsvTypeUtil::identity;
    } else {
      throw new RuntimeException("CSV data type not supported: " + dataTypeClass.toString());
    }
  }

  /**
   * @see at.ac.ait.lablink.clients.csvclient.dispatcher.IColumnDispatcher#getName()
   */
  @Override
  public String getName() {
    return this.column.getName();
  }
}
