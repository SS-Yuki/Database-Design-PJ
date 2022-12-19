package com.database.dao;

import com.database.object.IssueInstance;

import java.util.List;

public interface IssueInstanceDao {

    int insert(IssueInstance instance);

    IssueInstance queryById(int id);

    List<IssueInstance> queryByCommit(int commit);

}
