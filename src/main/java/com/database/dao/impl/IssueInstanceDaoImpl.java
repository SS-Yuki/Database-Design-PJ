package com.database.dao.impl;

import com.database.dao.IssueInstanceDao;
import com.database.object.IssueInstance;
import com.database.utils.JDBCUtil;

import java.util.List;

public class IssueInstanceDaoImpl implements IssueInstanceDao {
    @Override
    public int insert(IssueInstance instance) {
        String sql = "insert into issueinstance (case_id, commit_id, status, file_name) values (?, ?, ?, ?)";
        return JDBCUtil.update(sql, instance.getIssueCaseId(), instance.getCommitId(), instance.getStatus(), instance.getFileName());
    }

    @Override
    public IssueInstance queryById(int id) {
        String sql = "select inst_id issueInstanceId, case_id issueCaseId, commit_id commitId, status, file_name fileName from issueinstance where inst_id = ?";
        return JDBCUtil.queryOne(IssueInstance.class, sql, id);
    }

    @Override
    public List<IssueInstance> queryByCommit(int commit) {
        String sql = "select inst_id issueInstanceId, case_id issueCaseId, commit_id commitId, status, file_name fileName from issueinstance where commit_id = ?";
        return JDBCUtil.query(IssueInstance.class, sql, commit);
    }

}
