package com.database.service.impl;

import com.database.dao.IssueLocationDao;
import com.database.dao.impl.IssueLocationDaoImpl;
import com.database.object.IssueLocation;
import com.database.service.IssueLocationService;

import java.sql.Connection;
import java.util.List;

public class IssueLocationServiceImpl implements IssueLocationService {

    private IssueLocationDao issueLocationDao = new IssueLocationDaoImpl();

    @Override
    public void insert(Connection connection, IssueLocation issueLocation) {
        issueLocationDao.insert(connection, issueLocation);
    }

    @Override
    public List<IssueLocation> getLocationByInstId(Connection connection, int inst_id) {
        return issueLocationDao.queryByInstId(connection, inst_id);
    }
}
