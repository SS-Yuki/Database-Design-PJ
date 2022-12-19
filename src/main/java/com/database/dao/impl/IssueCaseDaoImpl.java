package com.database.dao.impl;

import com.database.dao.IssueCaseDao;
import com.database.object.IssueCase;
import com.database.utils.JDBCUtil;

import java.util.Date;
import java.util.List;

public class IssueCaseDaoImpl implements IssueCaseDao {
    @Override
    public int insert(IssueCase issueCase) {
        String sql = "insert into issuecase (type, severity, appear_commit_id) values (?, ?, ?)";
        return JDBCUtil.update(sql);
    }

    @Override
    public IssueCase queryById(int id) {
        String sql = "select case_id caseId, type, severity, appear_commit_id appearCommitId, solve_commit_id solveCommitId where case_id = ?";
        return JDBCUtil.queryOne(IssueCase.class, sql, id);
    }

    @Override
    public void update(IssueCase issueCase) {
        String sql = "update issuecase set type = ?, severity = ?, appear_commit_id = ?, solve_commit_id = ? where case_id = ?";
        JDBCUtil.update(sql, issueCase.getType(), issueCase.getSeverity(), issueCase.getAppearCommitId(), issueCase.getSolveCommitId(), issueCase.getIssueCaseId());
    }

    @Override
    public List<IssueCase> queryByTime(Date time) {
        String sql = "select case_id caseId, type, severity, appear_commit_id appearCommitId, solve_commit_id solveCommitId where appear_time >= ? or solve_time >= ?";
        return JDBCUtil.query(IssueCase.class, sql, time, time);
    }
}
