package com.database.dao;

import com.database.object.Commit;
import com.database.object.IssueCase;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface IssueCaseDao {

    int insert(Connection connection, IssueCase issueCase);

    IssueCase queryById(Connection connection, int id);

    void update(Connection connection, IssueCase issueCase);

    List<IssueCase> queryByAppearCommiter(Connection connection, String commiter);

    List<IssueCase> queryBySolveCommiter(Connection connection, String commiter);

    List<IssueCase> queryByAppearTime(Connection connection, Date begin, Date end);

    List<IssueCase> queryBySolveTime(Connection connection, Date begin, Date end);

    List<IssueCase> queryByAppearCommitId(Connection connection, int commitId);

    List<IssueCase> queryBySolveCommitId(Connection connection, int commit);

    List<IssueCase> queryByBranchId(Connection connection, int branchId);

    Commit queryAppearCommitById(Connection connection, int caseId);

    Commit querySolveCommitById(Connection connection, int caseId);
}