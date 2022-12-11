package com.database.dao;

import com.database.object.IssueCase;

public interface IssueCaseDao {

    int insert(IssueCase issueCase);

    IssueCase queryById(int id);

    void update(IssueCase issueCase);

}
