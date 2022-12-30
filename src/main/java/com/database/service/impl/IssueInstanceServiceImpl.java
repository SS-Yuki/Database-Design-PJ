package com.database.service.impl;

import com.database.common.IssueCaseType;
import com.database.dao.IssueInstanceDao;
import com.database.dao.impl.IssueInstanceDaoImpl;
import com.database.object.IssueInstance;
import com.database.service.IssueInstanceService;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class IssueInstanceServiceImpl implements IssueInstanceService {

    private IssueInstanceDao issueInstanceDao = new IssueInstanceDaoImpl();

    @Override
    public int insert(Connection connection, IssueInstance issueInstance) {
        return issueInstanceDao.insert(connection, issueInstance);
    }

    @Override
    public List<IssueInstance> getInstByCommit(Connection connection, int commit) {
        return issueInstanceDao.queryByCommit(connection, commit);
    }

    @Override
    public Date getAppearTimeById(Connection connection, int inst_id) {
        return issueInstanceDao.queryAppearTimeById(connection, inst_id);
    }

    @Override
    public Date getSolveTimeById(Connection connection, int inst_id) {
        return issueInstanceDao.querySolveTimeById(connection, inst_id);
    }

    @Override
    public IssueCaseType getTypeById(Connection connection, int inst_id) {
        return issueInstanceDao.queryTypeById(connection, inst_id);
    }

}
