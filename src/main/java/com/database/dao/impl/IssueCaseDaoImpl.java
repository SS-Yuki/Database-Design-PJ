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
        String sql = "update issuecase set type = ?, appear_commit_id = ?, solve_commit_id = ? where case_id = ?";
        JDBCUtil.update(sql, issueCase.getType(), issueCase.getAppearCommitId(), issueCase.getSolveCommitId(), issueCase.getIssueCaseId());
    }

    @Override
    public List<IssueCase> queryByAppearCommiter(String commiter) {
        String sql = "select case_id caseId, type, appear_commit_id appearCommitId, solve_commit_id solveCommitId from issuecase join commit c on c.commit_id = issuecase.appear_commit_id where commiter = ?";
        return JDBCUtil.query(IssueCase.class, sql, commiter);
    }

    @Override
    public List<IssueCase> queryBySolveCommiter(String commiter) {
        String sql = "select case_id caseId, type, appear_commit_id appearCommitId, solve_commit_id solveCommitId from issuecase join commit c on c.commit_id = issuecase.solve_commit_id where commiter = ?";
        return JDBCUtil.query(IssueCase.class, sql, commiter);
    }

    @Override
    public List<IssueCase> queryByAppearTime(Date begin, Date end) {
        String sql = "select case_id caseId, type, appear_commit_id appearCommitId, solve_commit_id solveCommitId from issuecase join commit c on c.commit_id = issuecase.appear_commit_id where commit_time > ? and commit_time < ?";
        return JDBCUtil.query(IssueCase.class, sql, begin, end);
    }

    @Override
    public List<IssueCase> queryBySolveTime(Date begin, Date end) {
        String sql = "select case_id caseId, type, appear_commit_id appearCommitId, solve_commit_id solveCommitId from issuecase join commit c on c.commit_id = issuecase.solve_commit_id where commit_time > ? and commit_time < ?";
        return null;
    }
}
