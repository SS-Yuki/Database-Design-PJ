package com.database.service;

import com.database.object.Commit;
import com.database.object.IssueCase;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IssueCaseService {

    int insert(Connection connection, IssueCase issueCase);

    IssueCase getCaseById(Connection connection, int case_id);

    void update(Connection connection, IssueCase issueCase);

    List<IssueCase> getCaseByAppearCommitId(Connection connection, int commitId);

    List<IssueCase> getCaseBySolveCommitId(Connection connection, int commitId);

    List<IssueCase> getCaseByAppearCommiter(Connection connection, String appearCommiter);

    List<IssueCase> getCaseBySolveCommiter(Connection connection, String solveCommiter);

    List<IssueCase> getCaseByAppearTime(Connection connection, Date begin, Date end);

    List<IssueCase> getCaseBySolveTime(Connection connection, Date begin, Date end);

    List<IssueCase> getCaseByBranchId(Connection connection, int branchId);

    Commit getAppearCommitById(Connection connection, int caseId);

    Commit getSolveCommitById(Connection connection, int caseId);

}