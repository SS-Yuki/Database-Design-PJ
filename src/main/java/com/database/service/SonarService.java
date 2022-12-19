package com.database.service;

import java.util.Date;

public interface SonarService {

    void showInstanceByCommit(int commit_id);

    void showCaseByCommiter(String commiter);

    void showCaseByTime(Date time);

    boolean importRepository();

}
