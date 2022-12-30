package com.database.utils;

import com.database.object.IssueCase;
import com.database.object.IssueInstance;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtil {

    private static int batchNumber = 500;

    /**
     * 判断表tableName是否存在
     * @param connection
     * @param tableName
     * @return
     */
    public static boolean existTable(Connection connection, String tableName) {
        boolean flag = false;
        ResultSet rs = null;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String type [] = {"TABLE"};
            rs = metaData.getTables(null, null, tableName, type);
            flag = rs.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionUtil.closeResource(null, null, rs);
        }
        return flag;
    }

    /**
     * 根据sql语句创建一个数据表
     * @param connection
     * @param sql
     */
    public static void createTable(Connection connection, String sql) {
        PreparedStatement statement = null;
        try {
            // 2、预编译sql
            statement = connection.prepareStatement(sql);
            // 3、没有占位符要填充，直接执行
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeResource(null, statement);
        }
    }

    /**
     * 对数据库的表进行增删改操作
     * @param connection
     * @param sql
     * @param args
     */
    public static int update(Connection connection, String sql, Object... args) {
        PreparedStatement statement = null;
        boolean getConnection = false;
        int id = -1;
        try {
            if (connection == null) {
                connection = ConnectionUtil.getConnection();
                getConnection = true;
            }
            // 2、预编译sql语句
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // 3、填充sql占位符
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
//                System.out.println("--" + args[i]);
            }
            // 4、执行sql语句
            statement.executeUpdate();
            // 5、检索由于执行此 Statement 对象而创建的所有自动生成的键
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (getConnection) {
                ConnectionUtil.closeResource(connection, statement);
            } else {
                ConnectionUtil.closeResource(null, statement);
            }
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
    public static void insertBatch(Connection connection, String sql, List<Object[]> data) {
        PreparedStatement statement = null;
        try {
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
            ConnectionUtil.closeResource(null, statement);
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
    public static <T> ArrayList<T> query(Connection connection, Class<T> clazz, String sql, Object... args) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean getConnection = false;
        try {
            if (connection == null) {
                connection = ConnectionUtil.getConnection();
                getConnection = true;
            }
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            rs = statement.executeQuery();
//            ResultSet rs = querySql(sql, args);
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
            e.printStackTrace();
        } finally {
            if (getConnection) {
                ConnectionUtil.closeResource(connection, statement, rs);
            } else {
                ConnectionUtil.closeResource(null, statement, rs);
            }
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
    public static <T> T queryOne(Connection connection, Class<T> clazz, String sql, Object... args) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean getConnection = false;
        try {
            if (connection == null) {
                connection = ConnectionUtil.getConnection();
                getConnection = true;
            }
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
            e.printStackTrace();
        } finally {
            if (getConnection) {
                ConnectionUtil.closeResource(connection, statement, rs);
            } else {
                ConnectionUtil.closeResource(null, statement, rs);
            }
        }
        return null;
    }

    public static IssueCase queryOneForIssueCase(Connection connection, String sql, Object... args) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean getConnection = false;
        try {
            if (connection == null) {
                connection = ConnectionUtil.getConnection();
                getConnection = true;
            }
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            rs = statement.executeQuery();
            if (rs.next()) {
                return new IssueCase(
                        rs.getInt("issueCaseId"),
                        EnumUtil.IssueCaseStatusString2Enum(rs.getString("issueCaseStatus")),
                        EnumUtil.IssueCaseTypeString2Enum(rs.getString("issueCaseType")),
                        rs.getInt("appearCommitId"),
                        rs.getInt("solveCommitId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (getConnection) {
                ConnectionUtil.closeResource(connection, statement, rs);
            } else {
                ConnectionUtil.closeResource(null, statement, rs);
            }
        }
        return null;
    }

    public static List<IssueCase> queryForIssueCase(Connection connection, String sql, Object... args) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean getConnection = false;
        try {
            if (connection == null) {
                connection = ConnectionUtil.getConnection();
                getConnection = true;
            }
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            rs = statement.executeQuery();
            List<IssueCase> list = new ArrayList<>();
            while (rs.next()) {
                IssueCase issueCase = new IssueCase(
                        rs.getInt("issueCaseId"),
                        EnumUtil.IssueCaseStatusString2Enum(rs.getString("issueCaseStatus")),
                        EnumUtil.IssueCaseTypeString2Enum(rs.getString("issueCaseType")),
                        rs.getInt("appearCommitId"),
                        rs.getInt("solveCommitId"));
                list.add(issueCase);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (getConnection) {
                ConnectionUtil.closeResource(connection, statement, rs);
            } else {
                ConnectionUtil.closeResource(null, statement, rs);
            }
        }
        return null;
    }


    public static IssueInstance queryOneForIssueInstance(Connection connection, String sql, Object... args) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean getConnection = false;
        try {
            if (connection == null) {
                connection = ConnectionUtil.getConnection();
                getConnection = true;
            }
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            rs = statement.executeQuery();
            if (rs.next()) {
                return new IssueInstance(
                        rs.getInt("issueInstanceId"),
                        rs.getInt("issueCaseId"),
                        rs.getInt("commitId"),
                        EnumUtil.IssueInstanceStatusString2Enum(rs.getString("issueInstanceStatus")),
                        rs.getString("fileName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (getConnection) {
                ConnectionUtil.closeResource(connection, statement, rs);
            } else {
                ConnectionUtil.closeResource(null, statement, rs);
            }
        }
        return null;
    }

    public static List<IssueInstance> queryForIssueInstance(Connection connection, String sql, Object... args) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean getConnection = false;
        try {
            if (connection == null) {
                connection = ConnectionUtil.getConnection();
                getConnection = true;
            }
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            rs = statement.executeQuery();
            List<IssueInstance> list = new ArrayList<>();
            while (rs.next()) {
                IssueInstance issueInstance = new IssueInstance(
                        rs.getInt("issueInstanceId"),
                        rs.getInt("issueCaseId"),
                        rs.getInt("commitId"),
                        EnumUtil.IssueInstanceStatusString2Enum(rs.getString("issueInstanceStatus")),
                        rs.getString("fileName"));
                list.add(issueInstance);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (getConnection) {
                ConnectionUtil.closeResource(connection, statement, rs);
            } else {
                ConnectionUtil.closeResource(null, statement, rs);
            }
        }
        return null;
    }

    /**
     * 查询某一单个数据
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    public static <E> E getValue(Connection connection, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean getConnection = false;
        try {
            if (connection == null) {
                connection = ConnectionUtil.getConnection();
                getConnection = true;
            }
            ps = connection.prepareStatement(sql);
            // 填充占位符
            for(int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 执行sql语句
            rs = ps.executeQuery();
            // 得到结果
            if(rs.next()) {
                return (E) rs.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (getConnection) {
                ConnectionUtil.closeResource(connection, ps, rs);
            } else {
                ConnectionUtil.closeResource(null, ps, rs);
            }
        }
        return null;
    }
}
