package com.database.service;

import com.database.common.IssueCaseType;
import com.database.object.IssueInstance;

import java.util.Date;
import java.util.List;

public interface IssueInstanceService {

    int insert(IssueInstance issueInstance);

    IssueInstance getInstById(int inst_id);

    List<IssueInstance> getInstByCommit(int commit);

    Date getAppearTimeById(int inst_id);

    Date getSolveTimeById(int inst_id);

    IssueCaseType getTypeById(int inst_id);

}
