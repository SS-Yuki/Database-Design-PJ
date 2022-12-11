package com.database.dao;

import com.database.object.IssueInstance;

public interface IssueInstanceDao {

    int insert(IssueInstance instance);

    IssueInstance queryById(int id);

}
