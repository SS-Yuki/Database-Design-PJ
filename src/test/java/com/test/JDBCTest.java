package com.test;

import com.database.utils.JDBCUtil;
import org.junit.Test;

public class JDBCTest {

    @Test
    public void testInsert() {
        String sql = "insert into repository (repository_name, base_dir) values (?, ?);";
        int id = JDBCUtil.update(sql, "hhh", "dddd");
        System.out.println(id);
    }

}
