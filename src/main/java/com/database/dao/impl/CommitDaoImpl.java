package com.database.dao.impl;

import com.database.dao.CommitDao;
import com.database.object.Commit;
import com.database.utils.JDBCUtil;

import java.util.List;

public class CommitDaoImpl implements CommitDao {
    @Override
    public void insert(Commit commit) {
        String sql = "insert into commit (commit_hash, commit_time, commiter, branch_id) values (?, ?, ?, ?)";
        JDBCUtil.update(sql, commit.getCommitHash(), commit.getCommitTime(), commit.getCommiter(), commit.getBranchId());
    }

    @Override
    public Commit queryById(int id) {
        String sql = "select commit_id commitId, commit_hash commitHash, commit_time commitTime, commiter, branch_id branchId from commit where commit_id = ?";
        return JDBCUtil.queryOne(Commit.class, sql, id);
    }

    @Override
    public Commit queryByHashAndBranchId(String hash, int branch_id) {
        String sql = "select commit_id commitId, commit_hash commitHash, commit_time commitTime, commiter, branch_id branchId from commit where commit_hash = ? and branch_id = ?";
        return JDBCUtil.queryOne(Commit.class, sql, hash, branch_id);
    }

    @Override
    public List<Commit> queryByBranchId(int branch_id) {
        String sql = "select commit_id commitId, commit_hash commitHash, commit_time commitTime, commiter, branch_id branchId from commit where branch_id = ?";
        return JDBCUtil.query(Commit.class, sql, branch_id);
    }
}
