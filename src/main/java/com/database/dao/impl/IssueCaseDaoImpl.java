package com.database.dao.impl;

import com.database.common.IssueCaseStatus;
import com.database.dao.IssueCaseDao;
import com.database.object.Commit;
import com.database.object.IssueCase;
import com.database.utils.EnumUtil;
import com.database.utils.JDBCUtil;

import java.util.Date;
import java.util.List;

public class IssueCaseDaoImpl implements IssueCaseDao {
    @Override
    public int insert(IssueCase issueCase) {
        String sql = "insert into issue_case (issueCaseStatus, issueCaseType, appearCommitId) values (?, ?, ?)";
        return JDBCUtil.update(sql, EnumUtil.Enum2String(issueCase.getIssueCaseStatus()), EnumUtil.Enum2String(issueCase.getIssueCaseType()), issueCase.getAppearCommitId());
    }

    @Override
    public IssueCase queryById(int id) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case where issueCaseId = ?";
        return JDBCUtil.queryOneForIssueCase(sql, id);
    }

    @Override
    public void update(IssueCase issueCase) {
        String sql = "update issue_case set issueCaseStatus = ?, issueCaseType = ?, appearCommitId = ?, solveCommitId = ? where caseId = ?";
        JDBCUtil.update(sql, EnumUtil.Enum2String(issueCase.getIssueCaseStatus()), EnumUtil.Enum2String(issueCase.getIssueCaseType()), issueCase.getAppearCommitId(), issueCase.getSolveCommitId(), issueCase.getIssueCaseId());
    }

    @Override
    public List<IssueCase> queryByAppearCommiter(String commiter) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case join git_commit c on c.commitId = issue_case.appearCommitId where commiter = ?";
        return JDBCUtil.queryForIssueCase(sql, commiter);
    }

    @Override
    public List<IssueCase> queryBySolveCommiter(String commiter) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case join git_commit c on c.commitId = issue_case.solveCommitId where issue_case.issueCaseStatus = ? and commiter = ?";
        return JDBCUtil.queryForIssueCase(sql, "SOLVED", commiter);
    }

    @Override
    public List<IssueCase> queryByAppearTime(Date begin, Date end) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case join git_commit c on c.commitId = issue_case.appearCommitId where commitTime > ? and commitTime < ?";
        return JDBCUtil.queryForIssueCase(sql, begin, end);
    }

    @Override
    public List<IssueCase> queryBySolveTime(Date begin, Date end) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case join git_commit c on c.commitId = issue_case.solveCommitId where issue_case.issueCaseStatus = ? and commitTime > ? and commitTime < ?";
        return JDBCUtil.queryForIssueCase(sql, "SOLVED", begin, end);
    }

    @Override
    public List<IssueCase> queryByAppearCommitId(int commitId) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case where appearCommitId = ?";
        return JDBCUtil.queryForIssueCase(sql, commitId);
    }

    @Override
    public List<IssueCase> queryBySolveCommitId(int commit) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case where issueCaseStatus = ? and solveCommitId = ?";
        return JDBCUtil.queryForIssueCase(sql, "SOLVED", commit);
    }

    @Override
    public List<IssueCase> queryAll() {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case";
        return JDBCUtil.queryForIssueCase(sql);
    }

    @Override
    public Commit queryAppearCommitById(int caseId) {
        String sql = "select commitId, branchId, commitHash, commitTime, commiter from git_commit join issue_case on issue_case.appearCommitId = git_commit.commitId where issue_case.issueCaseId = ?";
        return JDBCUtil.queryOne(Commit.class, sql, caseId);
    }

    @Override
    public Commit querySolveCommitById(int caseId) {
        String sql = "select commitId, branchId, commitHash, commitTime, commiter from git_commit join issue_case on issue_case.solveCommitId = git_commit.commitId where issue_case.issueCaseStatus = ? and issue_case.issueCaseId = ?";
        return JDBCUtil.queryOne(Commit.class, sql, "SOLVED", caseId);
    }

}
