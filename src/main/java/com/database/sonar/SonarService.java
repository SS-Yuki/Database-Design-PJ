package com.database.sonar;

import java.util.Date;

public interface SonarService {

    void showInstanceByCommit(int commit_id);

    void showCaseByCommiter(String commiter);

    void showCaseByTime(Date time);

    boolean importRepository(String baseDir, String pjName);

}
