package com.database.dao.impl;

import com.database.dao.BranchDao;
import com.database.object.Branch;
import com.database.utils.JDBCUtil;

import java.sql.Connection;
import java.util.List;

public class BranchDaoImpl implements BranchDao {
    @Override
    public int insert(Connection connection, Branch branch) {
        String sql = "insert into branch (branchName, repositoryId) values (?, ?);";
        return JDBCUtil.update(connection, sql, branch.getBranchName(), branch.getRepositoryId());
    }

    @Override
    public Branch queryById(Connection connection, int id) {
        String sql = "select branchId, branchName, repositoryId from branch where branchId = ?";
        return JDBCUtil.queryOne(connection, Branch.class, sql, id);
    }

    @Override
    public Branch queryByNameAndRepoId(Connection connection, String name, int repository_id) {
        String sql = "select branchId, branchName, repositoryId from branch where branchName = ? and repositoryId = ?";
        return JDBCUtil.queryOne(connection, Branch.class, sql, name, repository_id);
    }

    @Override
    public List<Branch> queryByRepoId(Connection connection, int repository_id) {
        String sql = "select branchId, branchName, repositoryId from branch where repositoryId = ?";
        return JDBCUtil.query(connection, Branch.class, sql, repository_id);
    }
}
