package com.database.service;

import com.database.object.IssueCase;

import java.util.Date;
import java.util.List;

public interface IssueCaseService {

    int insert(IssueCase issueCase);

    IssueCase getCaseById(int case_id);

    void update(IssueCase issueCase);

    List<IssueCase> getCaseByAppearCommiter(String appearCommiter);

    List<IssueCase> getCaseBySolveCommiter(String solveCommiter);

    List<IssueCase> getCaseByAppearTime(Date begin, Date end);

    List<IssueCase> getCaseBySolveTime(Date begin, Date end);
}
