package com.database.dao;

import com.database.common.IssueCaseType;
import com.database.object.IssueInstance;

import java.util.Date;
import java.util.List;

public interface IssueInstanceDao {

    int insert(IssueInstance instance);

    IssueInstance queryById(int id);

    List<IssueInstance> queryByCommit(int commit);

    Date queryAppearTimeById(int id);

    Date querySolveTimeById(int id);

    IssueCaseType queryTypeById(int id);
}
