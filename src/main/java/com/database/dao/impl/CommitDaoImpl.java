package com.database.dao.impl;

import com.database.dao.CommitDao;
import com.database.object.Commit;
import com.database.utils.JDBCUtil;

import java.util.List;

public class CommitDaoImpl implements CommitDao {
    @Override
    public int insert(Commit commit) {
        String sql = "insert into git_commit (commitHash, commitTime, commiter, branchId) values (?, ?, ?, ?)";
        return JDBCUtil.update(sql, commit.getCommitHash(), commit.getCommitTime(), commit.getCommiter(), commit.getBranchId());
    }

    @Override
    public Commit queryById(int id) {
        String sql = "select commitId, commitHash, commitTime, commiter, branchId from git_commit where commitId = ?";
        return JDBCUtil.queryOne(Commit.class, sql, id);
    }

    @Override
    public Commit queryByHashAndBranchId(String hash, int branch_id) {
        String sql = "select commitId, commitHash, commitTime, commiter, branchId from git_commit where commitHash = ? and branchId = ?";
        return JDBCUtil.queryOne(Commit.class, sql, hash, branch_id);
    }

    @Override
    public List<Commit> queryByBranchId(int branch_id) {
        String sql = "select commitId, commitHash, commitTime, commiter, branchId from git_commit where branchId = ?";
        return JDBCUtil.query(Commit.class, sql, branch_id);
    }

    @Override
    public Commit queryLatestCommitByBranchId(int branch_id) {
        String sql = "select commitId, commitHash, commitTime, commiter, branchId from git_commit where commitId = (select max(commitId) from git_commit where branchId = ?)";
        return JDBCUtil.queryOne(Commit.class, sql, branch_id);
    }

}