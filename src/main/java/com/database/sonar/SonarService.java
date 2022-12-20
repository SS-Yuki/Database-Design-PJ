package com.database.sonar;

import java.util.Date;

public interface SonarService {

    void showInfoByCommit(int commit_id);

    void showLatestInfo(int repositoryId, String branchName);

    void showCaseByTime(Date begin, Date end);

    void showCaseByCommiter(String commiter);

    boolean importRepository(String baseDir, String pjName);

}
