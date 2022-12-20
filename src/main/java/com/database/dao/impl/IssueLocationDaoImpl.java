package com.database.dao.impl;

import com.database.dao.IssueLocationDao;
import com.database.object.IssueLocation;
import com.database.utils.JDBCUtil;

import java.util.List;

public class IssueLocationDaoImpl implements IssueLocationDao {

    @Override
    public void insert(IssueLocation issueLocation) {
        String sql = "insert into issueloaction (inst_id, order, start_line, end_line) values (?, ?, ?, ?)";
        JDBCUtil.update(sql, issueLocation.getIssueInstanceId(), issueLocation.getOrder(), issueLocation.getStartLine(), issueLocation.getEndLine());
    }

    @Override
    public List<IssueLocation> queryByInstId(int inst_id) {
        String sql = "select inst_id issueInstanceId, order, start_line startLine, end_line endLine from issuelocation where inst_id = ?";
        return JDBCUtil.query(IssueLocation.class, sql, inst_id);
    }


}
