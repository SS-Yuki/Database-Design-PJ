package com.database.service;

import com.database.object.IssueCase;

import java.util.Date;
import java.util.List;

public interface IssueCaseService {

    int insert(IssueCase issueCase);

    IssueCase getCaseById(int case_id);

    List<IssueCase> getCaseByTime(Date time);

    void update(IssueCase issueCase);
}
