package com.database.service.impl;

import com.database.dao.IssueInstanceDao;
import com.database.dao.impl.IssueInstanceDaoImpl;
import com.database.object.IssueInstance;
import com.database.service.IssueInstanceService;

import java.util.Date;
import java.util.List;

public class IssueInstanceServiceImpl implements IssueInstanceService {

    private IssueInstanceDao issueInstanceDao = new IssueInstanceDaoImpl();

    @Override
    public int insert(IssueInstance issueInstance) {
        return issueInstanceDao.insert(issueInstance);
    }

    @Override
    public IssueInstance getInstById(int inst_id) {
        return issueInstanceDao.queryById(inst_id);
    }

    @Override
    public List<IssueInstance> getInstByCommit(int commit) {
        return issueInstanceDao.queryByCommit(commit);
    }

    @Override
    public Date getAppearTimeById(int inst_id) {
        return issueInstanceDao.queryAppearTimeById(inst_id);
    }

}
