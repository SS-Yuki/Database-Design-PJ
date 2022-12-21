package com.database.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class InitUtil {
    //相对地址
    private static final String InitSqlFile = "src/main/resources/create_table.sql";

    public static void createTable(){
        try{
            ArrayList<String> sqlList=new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(InitSqlFile), "UTF-8"));
            String sqlString = null;
            while ((sqlString = reader.readLine()) != null) {
                sqlList.add(sqlString);
            }
            reader.close();
            batchDate(sqlList);
        } catch (Exception e){
            System.out.println("建表失败");
            return;
        }
        System.out.println("建表成功");
    }


    private static void batchDate(ArrayList<String> sqlList) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Statement st = connection.createStatement();
        for (String sql : sqlList) {
            st.addBatch(sql);
            System.out.println(sql);
        }
        st.executeBatch();
    }

    public static void main(String[] args) {
        createTable();
    }


}