package com.database.service.impl;

import com.database.dao.IssueCaseDao;
import com.database.dao.impl.IssueCaseDaoImpl;
import com.database.object.IssueCase;
import com.database.service.IssueCaseService;

import java.util.Date;
import java.util.List;

public class IssueCaseServiceImpl implements IssueCaseService {

    private IssueCaseDao caseDao = new IssueCaseDaoImpl();

    @Override
    public int insert(IssueCase issueCase) {
        return caseDao.insert(issueCase);
    }

    @Override
    public IssueCase getCaseById(int case_id) {
        return caseDao.queryById(case_id);
    }

    @Override
    public void update(IssueCase issueCase) {
        caseDao.update(issueCase);
    }

    @Override
    public List<IssueCase> getCaseByAppearCommiter(String appearCommiter) {
        return caseDao.queryByAppearCommiter(appearCommiter);
    }

    @Override
    public List<IssueCase> getCaseBySolveCommiter(String solveCommiter) {
        return caseDao.queryBySolveCommiter(solveCommiter);
    }

    @Override
    public List<IssueCase> getCaseByAppearTime(Date begin, Date end) {
        return caseDao.queryByAppearTime(begin, end);
    }

    @Override
    public List<IssueCase> getCaseBySolveTime(Date begin, Date end) {
        return caseDao.queryBySolveTime(begin, end);
    }
}
