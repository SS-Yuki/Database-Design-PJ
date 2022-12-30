package com.database.service.impl;

import com.database.dao.IssueCaseDao;
import com.database.dao.impl.IssueCaseDaoImpl;
import com.database.object.Commit;
import com.database.object.IssueCase;
import com.database.service.IssueCaseService;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class IssueCaseServiceImpl implements IssueCaseService {

    private IssueCaseDao caseDao = new IssueCaseDaoImpl();

    @Override
    public int insert(Connection connection, IssueCase issueCase) {
        return caseDao.insert(connection, issueCase);
    }

    @Override
    public IssueCase getCaseById(Connection connection, int case_id) {
        return caseDao.queryById(connection, case_id);
    }

    @Override
    public void update(Connection connection, IssueCase issueCase) {
        caseDao.update(connection, issueCase);
    }

    @Override
    public List<IssueCase> getCaseByAppearCommitId(Connection connection, int commitId) {
        return caseDao.queryByAppearCommitId(connection, commitId);
    }

    @Override
    public List<IssueCase> getCaseBySolveCommitId(Connection connection, int commitId) {
        return caseDao.queryBySolveCommitId(connection, commitId);
    }

    @Override
    public List<IssueCase> getCaseByAppearCommiter(Connection connection, String appearCommiter) {
        return caseDao.queryByAppearCommiter(connection, appearCommiter);
    }

    @Override
    public List<IssueCase> getCaseBySolveCommiter(Connection connection, String solveCommiter) {
        return caseDao.queryBySolveCommiter(connection, solveCommiter);
    }

    @Override
    public List<IssueCase> getCaseByAppearTime(Connection connection, Date begin, Date end) {
        return caseDao.queryByAppearTime(connection, begin, end);
    }

    @Override
    public List<IssueCase> getCaseBySolveTime(Connection connection, Date begin, Date end) {
        return caseDao.queryBySolveTime(connection, begin, end);
    }

    @Override
    public List<IssueCase> getCaseByBranchId(Connection connection, int branchId) {
        return caseDao.queryByBranchId(connection, branchId);
    }

    @Override
    public Commit getAppearCommitById(Connection connection, int caseId) {
        return caseDao.queryAppearCommitById(connection, caseId);
    }

    @Override
    public Commit getSolveCommitById(Connection connection, int caseId) {
        return caseDao.querySolveCommitById(connection, caseId);
    }

}