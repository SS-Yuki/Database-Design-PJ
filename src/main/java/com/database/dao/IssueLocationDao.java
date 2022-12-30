package com.database.dao;

import com.database.object.IssueLocation;

import java.sql.Connection;
import java.util.List;

public interface IssueLocationDao {

    void insert(Connection connection, IssueLocation issueLocation);

    List<IssueLocation> queryByInstId(Connection connection, int inst_id);
}
