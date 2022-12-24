package com.database.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
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

    private static DataSource dataSource = null;

    // 通过静态代码块，初始化dataSource
    static {
        // 1、读取配置文件
        Properties properties = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(is);
            // 2、根据properties对象创建dataSource对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            System.out.println("数据库连接池初始化完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取单个数据库连接
    public static Connection getConnection() throws Exception {
        Connection connection = null;
        if (dataSource != null) {
            connection = dataSource.getConnection();
        }
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
