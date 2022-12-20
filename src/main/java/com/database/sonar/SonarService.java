package com.database.sonar;

import java.util.Date;

public interface SonarService {

    /**
     * 按照commit版本，展示该版本下的总缺陷详细列表，按照type分类的结果，以及按照存在时间排序的结果
     * @param commit_id
     */
    void showInfoByCommit(int commit_id);

    /**
     * 展示当前某分支最新版本下的总缺陷详细列表，按照type分类的结果，以及按照存在时间排序的结果
     * @param repositoryId
     * @param branchName
     */
    void showLatestInfo(int repositoryId, String branchName);

    // TODO: showCaseByCommit:展示给定版本下的缺陷引入和解决情况

    /**
     * 展示某一时间段中的静态缺陷引入和解决情况（均显示对应缺陷引入版本下的instance信息）
     * TODO: 对解决的数据信息进行统计，包括缺陷的解决率，已解决缺陷所用的时间，按照总体和各个type的issue进行统计
     * @param begin
     * @param end
     */
    void showCaseByTime(Date begin, Date end);

    /**
     * 展示某一开发人员的缺陷引入和解决情况（均显示对应缺陷引入版本下的instance信息）
     * @param commiter
     */
    void showCaseByCommiter(String commiter);

    // TODO: 现存缺陷中，已经存续超过指定时长的分类数量统计及详细信息

    // TODO: 静态缺陷的前后追踪关系

    /**
     * 导入仓库信息
     * @param baseDir
     * @param pjName
     * @return
     */
    boolean importRepository(String baseDir, String pjName);

}
