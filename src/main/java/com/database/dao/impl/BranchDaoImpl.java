package com.database.dao.impl;

import com.database.dao.BranchDao;
import com.database.object.Branch;
import com.database.utils.JDBCUtil;

import java.util.List;

public class BranchDaoImpl implements BranchDao {
    @Override
    public int insert(Branch branch) {
        String sql = "insert into branch (branchName, repositoryId) values (?, ?);";
        return JDBCUtil.update(sql, branch.getBranchName(), branch.getRepositoryId());
    }

    @Override
    public Branch queryById(int id) {
        String sql = "select branchId, branchName, repositoryId from branch where branchId = ?";
        return JDBCUtil.queryOne(Branch.class, sql, id);
    }

    @Override
    public Branch queryByNameAndRepoId(String name, int repository_id) {
        String sql = "select branchId, branchName, repositoryId from branch where branchName = ? and repositoryId = ?";
        return JDBCUtil.queryOne(Branch.class, sql, name, repository_id);
    }

    @Override
    public List<Branch> queryByRepoId(int repository_id) {
        String sql = "select branchId, branchName, repositoryId from branch where repositoryId = ?";
        return JDBCUtil.query(Branch.class, sql, repository_id);
    }
}
