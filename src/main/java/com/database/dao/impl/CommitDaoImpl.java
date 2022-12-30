package com.database.dao.impl;

import com.database.dao.CommitDao;
import com.database.object.Commit;
import com.database.utils.JDBCUtil;

import java.sql.Connection;
import java.util.List;

public class CommitDaoImpl implements CommitDao {
    @Override
    public int insert(Connection connection, Commit commit) {
        String sql = "insert into git_commit (commitHash, commitTime, commiter, branchId) values (?, ?, ?, ?)";
        return JDBCUtil.update(connection, sql, commit.getCommitHash(), commit.getCommitTime(), commit.getCommiter(), commit.getBranchId());
    }

    @Override
    public Commit queryById(Connection connection, int id) {
        String sql = "select commitId, commitHash, commitTime, commiter, branchId from git_commit where commitId = ?";
        return JDBCUtil.queryOne(connection, Commit.class, sql, id);
    }

    @Override
    public List<Commit> queryByBranchId(Connection connection, int branch_id) {
        String sql = "select commitId, commitHash, commitTime, commiter, branchId from git_commit where branchId = ?";
        return JDBCUtil.query(connection, Commit.class, sql, branch_id);
    }

    @Override
    public Commit queryLatestCommitByBranchId(Connection connection, int branch_id) {
        String sql = "select commitId, commitHash, commitTime, commiter, branchId from git_commit where commitId = (select max(commitId) from git_commit where branchId = ?)";
        return JDBCUtil.queryOne(connection, Commit.class, sql, branch_id);
    }

    @Override
    public List<Commit> queryAllChildrenByBranchIdAndCommitId(Connection connection, int branch_id, int commit_id) {
        String sql = "select commitId, commitHash, commitTime, commiter, branchId from git_commit where branchId = ? and commitId >= ?";
        return JDBCUtil.query(connection, Commit.class, sql, branch_id, commit_id);
    }

}