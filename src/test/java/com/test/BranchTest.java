package com.test;

import com.database.dao.BranchDao;
import com.database.dao.impl.BranchDaoImpl;
import com.database.object.Branch;
import com.database.service.BranchService;
import com.database.service.impl.BranchServiceImpl;
import org.junit.Test;

public class BranchTest {

    private BranchDao branchDao = new BranchDaoImpl();
    private BranchService branchService = new BranchServiceImpl();

    @Test
    public void testInsert() {
        Branch branch = new Branch(0, "test_branch", 1);
        branchDao.insert(branch);
    }

    @Test
    public void testQueryById() {
        int id = 1;
        Branch branch = branchDao.queryById(id);
        System.out.println(branch);
    }

    @Test
    public void testQueryByNameAndRepoId() {
        String name = "test_branch";
        int repository_id = 1;
        Branch branch = branchDao.queryByNameAndRepoId(name, repository_id);
        System.out.println(branch);
    }

    @Test
    public void testServiceInsert() {
        Branch branch = new Branch(0, "insert_test", 1);
        int id = branchService.insert(branch);
        System.out.println(id);
    }
}
