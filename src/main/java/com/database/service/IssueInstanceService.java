package com.database.service;

import com.database.common.IssueCaseType;
import com.database.object.IssueInstance;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface IssueInstanceService {

    int insert(Connection connection, IssueInstance issueInstance);

    List<IssueInstance> getInstByCommit(Connection connection, int commit);

    Date getAppearTimeById(Connection connection, int inst_id);

    Date getSolveTimeById(Connection connection, int inst_id);

    IssueCaseType getTypeById(Connection connection, int inst_id);

}
