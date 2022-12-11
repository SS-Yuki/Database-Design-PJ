package com.database.dao.impl;

import com.database.dao.BranchDao;
import com.database.object.Branch;
import com.database.utils.JDBCUtil;

import java.util.List;

public class BranchDaoImpl implements BranchDao {
    @Override
    public void insert(Branch branch) {
        String sql = "insert into branch (branch_name, repository_id) values (?, ?);";
        JDBCUtil.update(sql, branch.getBranchName(), branch.getRepositoryId());
    }

    @Override
    public Branch queryById(int id) {
        String sql = "select branch_id branchId, branch_name branchName, repository_id repositoryId from branch where branch_id = ?";
        return JDBCUtil.queryOne(Branch.class, sql, id);
    }

    @Override
    public Branch queryByNameAndRepoId(String name, int repository_id) {
        String sql = "select branch_id branchId, branch_name branchName, repository_id repositoryId from branch where branch_name = ? and repository_id = ?";
        return JDBCUtil.queryOne(Branch.class, sql, name, repository_id);
    }

    @Override
    public List<Branch> queryByRepoId(int repository_id) {
        String sql = "select branch_id branchId, branch_name branchName, repository_id repositoryId from branch where repository_id = ?";
        return JDBCUtil.query(Branch.class, sql, repository_id);
    }
}
