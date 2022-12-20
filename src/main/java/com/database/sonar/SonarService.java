package com.database.sonar;

import java.util.Date;

public interface SonarService {

    void showInstanceByCommit(int commit_id);

    void showCaseByCommiter(String commiter);

    void showCaseByTime(Date begin, Date end);

    boolean importRepository(String baseDir, String pjName);

}
