package com.database.service.impl;

import com.database.dao.BranchDao;
import com.database.dao.impl.BranchDaoImpl;
import com.database.object.Branch;
import com.database.service.BranchService;

import java.sql.Connection;
import java.util.List;

public class BranchServiceImpl implements BranchService {

    private BranchDao branchDao = new BranchDaoImpl();

    @Override
    public int insert(Connection connection, Branch branch) {
        // 插入
        return branchDao.insert(connection, branch);
    }

    @Override
    public int[] getIdByRepoId(Connection connection, int repository_id) {
        List<Branch> branches = branchDao.queryByRepoId(connection, repository_id);
        int[] branchIds = new int[branches.size()];
        for (int i = 0; i < branches.size(); i++) {
            branchIds[i] = branches.get(i).getBranchId();
        }
        return branchIds;
    }

    @Override
    public int getIdByNameAndRepoId(Connection connection, int repository_id, String name) {
        Branch branch = branchDao.queryByNameAndRepoId(connection, name, repository_id);
        if (branch == null) return 0;
        return branch.getBranchId();
    }

    @Override
    public String getNameById(Connection connection, int branch_id) {
        return branchDao.queryById(connection, branch_id).getBranchName();
    }

}
