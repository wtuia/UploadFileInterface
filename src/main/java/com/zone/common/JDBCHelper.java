package com.zone.common;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class JDBCHelper {

    private static final Logger logger = LoggerFactory.getLogger(JDBCHelper.class);
    private static final Map<String, DataSource> DATA_SOURCE_MAP = new HashMap<>();
    private static final Map<String, DataSource> ALL_DATA_SOURCE_MAP = new HashMap<>();
    private static final String DATA_SOURCE_CONNECTION = "connection";
    private static PropertiesHandle HANDLE = PropertiesHandle.
            getPropertiesHandle("ziyuan.properties");

    static Connection getConnection() throws SQLException {
      DataSource ds = DATA_SOURCE_MAP.get(DATA_SOURCE_CONNECTION);
      if (ds == null) {
          synchronized (JDBCHelper.class) {
              ds = DATA_SOURCE_MAP.get(DATA_SOURCE_CONNECTION);
              if (ds == null) {
                  ds = initDataSource();
                  DATA_SOURCE_MAP.put(DATA_SOURCE_CONNECTION, ds);
              }
          }
      }
      return ds.getConnection();
  }

    static Connection getConnection(String source) throws SQLException {
        DataSource ds = ALL_DATA_SOURCE_MAP.get(source);
        if (ds == null) {
            synchronized (JDBCHelper.class) {
                ds = ALL_DATA_SOURCE_MAP.get(source);
                if (ds == null) {
                    ds = initDataSource(source);
                    ALL_DATA_SOURCE_MAP.put(source, ds);
                }
            }
        }
        return ds.getConnection();
    }

    private static  DataSource initDataSource(String source) {
        ComboPooledDataSource cbPoolDS = new ComboPooledDataSource();
        try {
            cbPoolDS.setDriverClass(HANDLE.getProperties("ipNet.DBMS.connection.driverClass"));
        } catch (Exception e) {
            logger.error("数据库驱动加载失败",e);
            throw new RuntimeException("数据库驱动加载失败");
        }
        cbPoolDS.setJdbcUrl(source);
        cbPoolDS.setUser(HANDLE.getProperties( "ipNet.DBMS.connection.user"));
        cbPoolDS.setPassword(HANDLE.getProperties("ipNet.DBMS.connection.password"));
        cbPoolDS.setInitialPoolSize(Integer.parseInt(HANDLE.getProperties( "ipNet.DBMS.connection.initialPoolSize")));
        cbPoolDS.setMinPoolSize(Integer.parseInt(HANDLE.getProperties(  "ipNet.DBMS.connection.minPoolSize")));
        cbPoolDS.setMaxPoolSize(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.maxPoolSize")));
        cbPoolDS.setMaxIdleTime(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.maxIdleTime")));
        cbPoolDS.setMaxStatements(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.maxStatements")));
        cbPoolDS.setAcquireIncrement(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.acquireIncrement")));
        cbPoolDS.setIdleConnectionTestPeriod(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.idleConnectionTestPeriod")));
        cbPoolDS.setAcquireRetryAttempts(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.acquireRetryAttempts")));
        cbPoolDS.setAcquireRetryDelay(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.acquireRetryDelay")));
        cbPoolDS.setBreakAfterAcquireFailure(Boolean.parseBoolean(HANDLE.getProperties( "ipNet.DBMS.connection.breakAfterAcquireFailure")));
        cbPoolDS.setTestConnectionOnCheckout(Boolean.parseBoolean(HANDLE.getProperties( "ipNet.DBMS.connection.testConnectionOnCheckout")));
        return cbPoolDS;
    }

      private static  DataSource initDataSource() {
        ComboPooledDataSource cbPoolDS = new ComboPooledDataSource();
        try {
            cbPoolDS.setDriverClass(HANDLE.getProperties("ipNet.DBMS.connection.driverClass"));
        } catch (Exception e) {
            logger.error("数据库驱动加载失败",e);
            throw new RuntimeException("数据库驱动加载失败");
        }
        cbPoolDS.setJdbcUrl(HANDLE.getProperties("ipNet.DBMS.connection.url"));
        cbPoolDS.setUser(HANDLE.getProperties("ipNet.DBMS.connection.user"));
        cbPoolDS.setPassword(HANDLE.getProperties("ipNet.DBMS.connection.password"));
        cbPoolDS.setInitialPoolSize(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.initialPoolSize")));
        cbPoolDS.setMinPoolSize(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.minPoolSize")));
        cbPoolDS.setMaxPoolSize(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.maxPoolSize")));
        cbPoolDS.setMaxIdleTime(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.maxIdleTime")));
        cbPoolDS.setMaxStatements(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.maxStatements")));
        cbPoolDS.setAcquireIncrement(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.acquireIncrement")));
        cbPoolDS.setIdleConnectionTestPeriod(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.idleConnectionTestPeriod")));
        cbPoolDS.setAcquireRetryAttempts(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.acquireRetryAttempts")));
        cbPoolDS.setAcquireRetryDelay(Integer.parseInt(HANDLE.getProperties("ipNet.DBMS.connection.acquireRetryDelay")));
        cbPoolDS.setBreakAfterAcquireFailure(Boolean.parseBoolean(HANDLE.getProperties("ipNet.DBMS.connection.breakAfterAcquireFailure")));
        cbPoolDS.setTestConnectionOnCheckout(Boolean.parseBoolean(HANDLE.getProperties("ipNet.DBMS.connection.testConnectionOnCheckout")));
        return cbPoolDS;
    }
}
