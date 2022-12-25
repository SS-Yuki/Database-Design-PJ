package com.database.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class InitUtil {
    //相对地址
    private static final String InitSqlFile = "repository.properties";

    public static void createTable(){
        try{
            // 1读取配置文件信息
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(InitSqlFile);
            Properties props = new Properties();
            props.load(is);

            ArrayList<String> sqlList=new ArrayList<>();
            sqlList.add(props.getProperty("repository"));
            sqlList.add(props.getProperty("branch"));
            sqlList.add(props.getProperty("commit"));
            sqlList.add(props.getProperty("case"));
            sqlList.add(props.getProperty("instance"));
            sqlList.add(props.getProperty("location"));

            batchDate(sqlList);
        } catch (Exception e){
            System.out.println(e);
            System.out.println("建表失败");
            return;
        }
        System.out.println("建表成功");
    }


    private static void batchDate(ArrayList<String> sqlList) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        System.out.println("...");
        Statement st = connection.createStatement();
        for (String sql : sqlList) {
            st.addBatch(sql);
            System.out.println(sql);
        }
        st.executeBatch();
    }

}