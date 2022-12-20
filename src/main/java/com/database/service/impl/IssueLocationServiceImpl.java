package com.database.service.impl;

import com.database.dao.IssueLocationDao;
import com.database.dao.impl.IssueLocationDaoImpl;
import com.database.object.IssueLocation;
import com.database.service.IssueLocationService;

import java.util.List;

public class IssueLocationServiceImpl implements IssueLocationService {

    private IssueLocationDao issueLocationDao = new IssueLocationDaoImpl();

    @Override
    public void insert(IssueLocation issueLocation) {
        issueLocationDao.insert(issueLocation);
    }

    @Override
    public List<IssueLocation> getLocationByInstId(int inst_id) {
        return issueLocationDao.queryByInstId(inst_id);
    }
}
