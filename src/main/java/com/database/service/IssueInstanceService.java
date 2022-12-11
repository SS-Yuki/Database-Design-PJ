package com.database.service;

import com.database.object.IssueInstance;

public interface IssueInstanceService {

    int insert(IssueInstance issueInstance);

    IssueInstance getInstById(int inst_id);

}
