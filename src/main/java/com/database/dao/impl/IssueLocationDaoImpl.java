package com.database.dao.impl;

import com.database.dao.IssueLocationDao;
import com.database.object.IssueLocation;
import com.database.utils.JDBCUtil;

import java.util.List;

public class IssueLocationDaoImpl implements IssueLocationDao {

    @Override
    public void insert(IssueLocation issueLocation) {
        String sql = "insert into issue_loaction (issueInstanceId, locationOrder, startLine, endLine) values (?, ?, ?, ?)";
        JDBCUtil.update(sql, issueLocation.getIssueInstanceId(), issueLocation.getLocationOrder(), issueLocation.getStartLine(), issueLocation.getEndLine());
    }

    @Override
    public List<IssueLocation> queryByInstId(int inst_id) {
        String sql = "select issueInstanceId, locationOrder, startLine, endLine from issue_location where issueInstanceId = ?";
        return JDBCUtil.query(IssueLocation.class, sql, inst_id);
    }


}
