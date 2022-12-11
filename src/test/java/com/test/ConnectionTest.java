package com.test;

import com.database.utils.ConnectionUtil;
import org.junit.Test;

import java.sql.Connection;

public class ConnectionTest {

    @Test
    public void testGetConnection() throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        System.out.println(connection);
    }

}
