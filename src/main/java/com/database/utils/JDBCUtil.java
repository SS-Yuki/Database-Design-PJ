package com.database.utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtil {

    private static int batchNumber = 500;

    /**
     * 判断表tableName是否存在
     * @param tableName
     * @return
     */
    public static boolean existTable(String tableName) {
        boolean flag = false;
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = ConnectionUtil.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            String type [] = {"TABLE"};
            rs = metaData.getTables(null, null, tableName, type);
            flag = rs.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionUtil.closeResource(connection, null, rs);
        }
        return flag;
    }

    /**
     * 根据sql语句创建一个数据表
     * @param sql
     */
    public static void createTable(String sql) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 1、获取连接
            connection = ConnectionUtil.getConnection();
            // 2、预编译sql
            statement = connection.prepareStatement(sql);
            // 3、没有占位符要填充，直接执行
            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionUtil.closeResource(connection, statement);
        }
    }

    /**
     * 对数据库的表进行增删改操作
     * @param sql
     * @param args
     */
    public static int update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement statement = null;
        int id = -1;
        try {
            // 1、获取连接
            connection = ConnectionUtil.getConnection();
            // 2、预编译sql语句
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // 3、填充sql占位符
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            // 4、执行sql语句
            statement.executeUpdate();
            // 5、检索由于执行此 Statement 对象而创建的所有自动生成的键
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionUtil.closeResource(connection, statement);
        }
        return id;
    }

    /**
     * 真正实现对数据表的一次批处理插入
     * @param data  要插入的记录列表，每项为一条要插入的记录
     * @param start 此次批处理开始的下标
     * @param length    此次批处理插入的记录条数
     */
    private static void insertBatch(PreparedStatement statement, List<Object[]> data, int start, int length) throws SQLException {
        // 插入length条记录，需要完成length次占位符填充
        for (int i = 0; i < length; i++) {
            Object[] objects = data.get(start + i);
            for (int j = 0; j < objects.length; j++) {
                statement.setObject(j + 1, objects[j]);
            }
            statement.addBatch();
        }
        statement.executeBatch();
        statement.clearBatch();
    }

    /**
     * 实现对数据库的表批量插入，优化执行时间
     * @param sql
     * @param data 要插入的数据，类型为List<>，每项代表一条记录的数据
     */
    public static void insertBatch(String sql, List<Object[]> data) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);

            int left = data.size() % batchNumber;
            // 将不能组成一个batch的部分处理掉
            insertBatch(statement, data, 0, left);
            // 插入后续各个batch
            for (int i = left; i < data.size(); i += batchNumber) {
                insertBatch(statement, data, i, batchNumber);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionUtil.closeResource(connection, statement);
        }
    }

    /**
     * 查询数据库，并将查询结果封装为bean
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> query(Class<T> clazz, String sql, Object... args) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            // 处理查询结果
            ArrayList<T> queryList = new ArrayList<>();
            while (rs.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = resultSetMetaData.getColumnLabel(i + 1);
                    // 通过反射设置对象参数
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                queryList.add(t);
            }
            return queryList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionUtil.closeResource(connection, statement, rs);
        }
        return null;
    }

    /**
     * 查询数据库，查询单一结果
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T queryOne(Class<T> clazz, String sql, Object... args) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            rs = statement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (rs.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionUtil.closeResource(connection, statement, rs);
        }
        return null;
    }
}
