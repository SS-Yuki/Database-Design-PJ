package com.database.dao.impl;

import com.database.dao.IssueLocationDao;
import com.database.object.IssueLocation;
import com.database.utils.JDBCUtil;

public class IssueLocationDaoImpl implements IssueLocationDao {

    @Override
    public void insert(IssueLocation issueLocation) {
        String sql = "insert into issueloaction (inst_id, order, start_line, end_line) values (?, ?, ?, ?)";
        JDBCUtil.update(sql, issueLocation.getIssueInstanceId(), issueLocation.getOrder(), issueLocation.getStartLine(), issueLocation.getEndLine());
    }


}
