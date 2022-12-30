package com.database.dao.impl;

import com.database.dao.IssueLocationDao;
import com.database.object.IssueLocation;
import com.database.utils.JDBCUtil;

import java.sql.Connection;
import java.util.List;

public class IssueLocationDaoImpl implements IssueLocationDao {

    @Override
    public void insert(Connection connection, IssueLocation issueLocation) {
        String sql = "insert into issue_location (issueInstanceId, locationOrder, startLine, endLine) values (?, ?, ?, ?)";
        JDBCUtil.update(connection, sql, issueLocation.getIssueInstanceId(), issueLocation.getLocationOrder(), issueLocation.getStartLine(), issueLocation.getEndLine());
    }

    @Override
    public List<IssueLocation> queryByInstId(Connection connection, int inst_id) {
        String sql = "select issueInstanceId, locationOrder, startLine, endLine from issue_location where issueInstanceId = ?";
        return JDBCUtil.query(connection, IssueLocation.class, sql, inst_id);
    }


}
