package com.database.service;

import com.database.object.IssueCase;

public interface IssueCaseService {

    int insert(IssueCase issueCase);

    IssueCase getCaseById(int case_id);

}
