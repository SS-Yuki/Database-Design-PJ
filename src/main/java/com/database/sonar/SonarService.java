package com.database.sonar;

import java.util.Date;

public interface SonarService {

    /**
     * 按照commit版本，展示该版本下的总缺陷详细列表，按照type分类的结果，以及按照存在时间排序的结果
     * @param commit_id
     */
    void showInstInfoByCommit(int commit_id);

    /**
     * 展示当前某分支最新版本下的总缺陷详细列表，按照type分类的结果，以及按照存在时间排序的结果
     * @param repositoryId
     * @param branchName
     */
    void showLatestInstInfo(int repositoryId, String branchName);

    /**
     * 展示某一版本中静态缺陷引入和解决情况
     * @param commit
     */
    void showCaseInfoByCommit(int commit);

    /**
     * 展示某一时间段中的静态缺陷引入和解决情况
     * @param begin
     * @param end
     */
    void showCaseInfoByTime(Date begin, Date end);

    /**
     * 展示某一开发人员的缺陷引入和解决情况
     * @param commiter
     */
    void showCaseInfoByCommiter(String commiter);

    /**
     * 现存的静态缺陷中，已经存续超过指定时间的缺陷情况
     */
    void showCaseInfoByDuration();

    // TODO: 静态缺陷的前后追踪关系

    /**
     * 导入仓库信息
     * @param baseDir
     * @param pjName
     * @return
     */
    boolean importRepository(String baseDir, String pjName);

}
