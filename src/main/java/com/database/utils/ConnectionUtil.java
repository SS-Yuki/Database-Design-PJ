package com.database.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * 控制数据库连接Connection的工具类
 * 原始连接，通过MySQL驱动，没有使用连接池
 */
public class ConnectionUtil {

    // 获取单个数据库连接
    public static Connection getConnection() throws Exception {
        // 1、读取配置文件
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mysql.properties");
        Properties properties = new Properties();
        properties.load(is);

        String url = properties.getProperty("mysql.url");
        String user = properties.getProperty("mysql.username");
        String password = properties.getProperty("mysql.password");
        String driverClass = properties.getProperty("mysql.driverClass");

        // 2、加载并注册驱动
        Class.forName(driverClass);

        // 3、获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    // 关闭连接
    public static void closeResource(Connection connection, Statement statement) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 关闭连接
    public static void closeResource(Connection connection, Statement statement, ResultSet rs) {
        closeResource(connection, statement);
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
