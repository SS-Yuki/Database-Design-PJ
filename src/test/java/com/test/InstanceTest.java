package com.test;

import com.database.dao.IssueInstanceDao;
import com.database.dao.impl.IssueInstanceDaoImpl;
import org.junit.Test;

import java.util.Date;

public class InstanceTest {

    private IssueInstanceDao issueInstanceDao = new IssueInstanceDaoImpl();

    @Test
    public void testQueryTime() {
        Date date = issueInstanceDao.queryAppearTimeById(1);
        System.out.println(date);
    }

}
