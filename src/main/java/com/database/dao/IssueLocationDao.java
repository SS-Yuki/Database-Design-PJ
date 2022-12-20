package com.database.dao;

import com.database.object.IssueLocation;

import java.util.List;

public interface IssueLocationDao {

    void insert(IssueLocation issueLocation);

    List<IssueLocation> queryByInstId(int inst_id);
}
