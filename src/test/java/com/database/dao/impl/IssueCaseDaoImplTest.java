package com.database.dao.impl;

import com.database.dao.IssueCaseDao;
import com.database.object.Commit;
import com.database.object.IssueCase;
import junit.framework.TestCase;

public class IssueCaseDaoImplTest extends TestCase {

    public void testQueryById() {
        int id = 255;
        IssueCaseDao dao = new IssueCaseDaoImpl();
        IssueCase case_ = dao.queryById(id);
        System.out.println(case_);
    }
}