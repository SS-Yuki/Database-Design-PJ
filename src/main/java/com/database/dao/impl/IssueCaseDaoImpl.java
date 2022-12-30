package com.database.dao.impl;

import com.database.dao.IssueCaseDao;
import com.database.object.Commit;
import com.database.object.IssueCase;
import com.database.utils.EnumUtil;
import com.database.utils.JDBCUtil;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class IssueCaseDaoImpl implements IssueCaseDao {
    @Override
    public int insert(Connection connection, IssueCase issueCase) {
        String sql = "insert into issue_case (issueCaseStatus, issueCaseType, appearCommitId) values (?, ?, ?)";
        return JDBCUtil.update(connection, sql, EnumUtil.Enum2String(issueCase.getIssueCaseStatus()), EnumUtil.Enum2String(issueCase.getIssueCaseType()), issueCase.getAppearCommitId());
    }

    @Override
    public IssueCase queryById(Connection connection, int id) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case where issueCaseId = ?";
        return JDBCUtil.queryOneForIssueCase(connection, sql, id);
    }

    @Override
    public void update(Connection connection, IssueCase issueCase) {
        String sql = "update issue_case set issueCaseStatus = ?, issueCaseType = ?, appearCommitId = ?, solveCommitId = ? where issueCaseId = ?";
        JDBCUtil.update(connection, sql, EnumUtil.Enum2String(issueCase.getIssueCaseStatus()), EnumUtil.Enum2String(issueCase.getIssueCaseType()), issueCase.getAppearCommitId(), issueCase.getSolveCommitId(), issueCase.getIssueCaseId());
    }

    @Override
    public List<IssueCase> queryByAppearCommiter(Connection connection, String commiter) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case join git_commit c on c.commitId = issue_case.appearCommitId where commiter = ?";
        return JDBCUtil.queryForIssueCase(connection, sql, commiter);
    }

    @Override
    public List<IssueCase> queryBySolveCommiter(Connection connection, String commiter) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case join git_commit c on c.commitId = issue_case.solveCommitId where issue_case.issueCaseStatus = ? and commiter = ?";
        return JDBCUtil.queryForIssueCase(connection, sql, "SOLVED", commiter);
    }

    @Override
    public List<IssueCase> queryByAppearTime(Connection connection, Date begin, Date end) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case join git_commit c on c.commitId = issue_case.appearCommitId where commitTime > ? and commitTime < ?";
        return JDBCUtil.queryForIssueCase(connection, sql, begin, end);
    }

    @Override
    public List<IssueCase> queryBySolveTime(Connection connection, Date begin, Date end) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case join git_commit c on c.commitId = issue_case.solveCommitId where issue_case.issueCaseStatus = ? and commitTime > ? and commitTime < ?";
        return JDBCUtil.queryForIssueCase(connection, sql, "SOLVED", begin, end);
    }

    @Override
    public List<IssueCase> queryByAppearCommitId(Connection connection, int commitId) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case where appearCommitId = ?";
        return JDBCUtil.queryForIssueCase(connection, sql, commitId);
    }

    @Override
    public List<IssueCase> queryBySolveCommitId(Connection connection, int commit) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case where issueCaseStatus = ? and solveCommitId = ?";
        return JDBCUtil.queryForIssueCase(connection, sql, "SOLVED", commit);
    }

    @Override
    public List<IssueCase> queryByBranchId(Connection connection, int branchId) {
        String sql = "select issueCaseId, issueCaseStatus, issueCaseType, appearCommitId, solveCommitId from issue_case, git_commit, branch where appearCommitId = git_commit.commitId and git_commit.branchId = branch.branchId and branch.branchId = ?";
        return JDBCUtil.queryForIssueCase(connection, sql, branchId);
    }


    @Override
    public Commit queryAppearCommitById(Connection connection, int caseId) {
        String sql = "select commitId, branchId, commitHash, commitTime, commiter from git_commit join issue_case on issue_case.appearCommitId = git_commit.commitId where issue_case.issueCaseId = ?";
        return JDBCUtil.queryOne(connection, Commit.class, sql, caseId);
    }

    @Override
    public Commit querySolveCommitById(Connection connection, int caseId) {
        String sql = "select commitId, branchId, commitHash, commitTime, commiter from git_commit join issue_case on issue_case.solveCommitId = git_commit.commitId where issue_case.issueCaseStatus = ? and issue_case.issueCaseId = ?";
        return JDBCUtil.queryOne(connection, Commit.class, sql, "SOLVED", caseId);
    }

}