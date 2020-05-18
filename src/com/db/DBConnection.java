package com.db;

import com.mchange.v2.c3p0.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    static Connection conn = null;
    static ComboPooledDataSource cpd = null;
    static Properties cfg = new Properties();

    static {
        try {
            InputStream in = DBConnection.class.getClassLoader()
                    .getResourceAsStream("db.properties");
            cfg.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DBConnection() {

    }

    /**
     * 建立连接，两种方式：C3p0和普通方式
     *
     * @param type
     * @return
     * @author Luke
     */
    public static Connection getConnect(String type) {
        if ("c3p0".equalsIgnoreCase(type)) {
            try {
                ComboPooledDataSource cpds = new ComboPooledDataSource();
                cpds.setDriverClass(cfg.getProperty("jdbc.driver"));
                cpds.setJdbcUrl(cfg.getProperty("jdbc.url"));
                cpds.setUser(cfg.getProperty("jdbc.username"));
                cpds.setPassword(cfg.getProperty("jdbc.password"));
                cpds.setMaxPoolSize(40);
                cpds.setMinPoolSize(10);
                cpds.setInitialPoolSize(10);
                cpds.setMaxStatements(180);
                conn = cpds.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class.forName(cfg.getProperty("jdbc.driver"));
                conn = DriverManager.getConnection(
                        cfg.getProperty("jdbc.url"),
                        cfg.getProperty("jdbc.username"),
                        cfg.getProperty("jdbc.password")
                );
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 关闭数据库
     */
    public static void closeConn() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        conn = null;
    }
}
