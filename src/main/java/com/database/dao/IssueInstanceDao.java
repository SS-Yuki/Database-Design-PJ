package com.database.dao;

import com.database.common.IssueCaseType;
import com.database.object.IssueInstance;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface IssueInstanceDao {

    int insert(Connection connection, IssueInstance instance);

    IssueInstance queryById(Connection connection, int id);

    List<IssueInstance> queryByCommit(Connection connection, int commit);

    Date queryAppearTimeById(Connection connection, int id);

    Date querySolveTimeById(Connection connection, int id);

    IssueCaseType queryTypeById(Connection connection, int id);
}
