package com.database.dao;

import com.database.object.Commit;
import com.database.object.IssueCase;

import java.util.Date;
import java.util.List;

public interface IssueCaseDao {

    int insert(IssueCase issueCase);

    IssueCase queryById(int id);

    void update(IssueCase issueCase);

    List<IssueCase> queryByAppearCommiter(String commiter);

    List<IssueCase> queryBySolveCommiter(String commiter);

    List<IssueCase> queryByAppearTime(Date begin, Date end);

    List<IssueCase> queryBySolveTime(Date begin, Date end);

    List<IssueCase> queryByAppearCommitId(int commitId);

    List<IssueCase> queryBySolveCommitId(int commit);

    List<IssueCase> queryByBranchId(int branchId);

    Commit queryAppearCommitById(int caseId);

    Commit querySolveCommitById(int caseId);
}