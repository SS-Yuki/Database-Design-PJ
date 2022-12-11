package com.database.service.impl;

import com.database.dao.IssueCaseDao;
import com.database.dao.impl.IssueCaseDaoImpl;
import com.database.object.IssueCase;
import com.database.service.IssueCaseService;

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
}
