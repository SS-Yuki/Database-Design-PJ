package com.database.service;

import com.database.object.IssueLocation;

import java.sql.Connection;
import java.util.List;

public interface IssueLocationService {

    void insert(Connection connection, IssueLocation issueLocation);

    List<IssueLocation> getLocationByInstId(Connection connection, int inst_id);

}
