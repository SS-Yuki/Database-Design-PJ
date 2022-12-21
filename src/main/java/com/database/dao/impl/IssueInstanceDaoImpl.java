package com.database.dao.impl;

import com.database.common.IssueCaseType;
import com.database.dao.IssueInstanceDao;
import com.database.object.IssueInstance;
import com.database.utils.JDBCUtil;

import java.util.Date;
import java.util.List;

public class IssueInstanceDaoImpl implements IssueInstanceDao {
    @Override
    public int insert(IssueInstance instance) {
        String sql = "insert into issue_instance (issueCaseId, commitId, issueInstanceStatus, fileName) values (?, ?, ?, ?)";
        return JDBCUtil.update(sql, instance.getIssueCaseId(), instance.getCommitId(), instance.getIssueInstanceStatus(), instance.getFileName());
    }

    @Override
    public IssueInstance queryById(int id) {
        String sql = "select issueInstanceId, issueCaseId, commitId, issueInstanceStatus, fileName from issue_instance where issueInstanceId = ?";
        return JDBCUtil.queryOne(IssueInstance.class, sql, id);
    }

    @Override
    public List<IssueInstance> queryByCommit(int commit) {
        String sql = "select issueInstanceId, issueCaseId, commitId, issueInstanceStatus, fileName from issue_instance where commitId = ?";
        return JDBCUtil.query(IssueInstance.class, sql, commit);
    }

    @Override
    public Date queryAppearTimeById(int id) {
        String sql = "select commitTime from git_commit\n" +
                "join issue_case i on git_commit.commitId = i.appearCommitId\n" +
                "join issue_instance i2 on i.caseId = i2.caseId\n" +
                "where i2.issueInstanceId = ?";
        return JDBCUtil.getValue(sql, id);
    }

    @Override
    public IssueCaseType queryTypeById(int id) {
        String sql = "select issueCaseType from issue_case\n" +
                "join issue_instance i on issue_case.caseId = i.caseId\n" +
                "where issueInstanceId = ?";
        return JDBCUtil.getValue(sql, id);
    }

}