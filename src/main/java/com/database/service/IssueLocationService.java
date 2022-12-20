package com.database.service;

import com.database.object.IssueLocation;

import java.util.List;

public interface IssueLocationService {

    void insert(IssueLocation issueLocation);

    List<IssueLocation> getLocationByInstId(int inst_id);

}
