package com.database.service.impl;

import com.database.dao.CommitDao;
import com.database.dao.impl.CommitDaoImpl;
import com.database.object.Commit;
import com.database.service.CommitService;

public class CommitServiceImpl implements CommitService {

    private CommitDao commitDao = new CommitDaoImpl();

    @Override
    public int insert(Commit commit) {
        // 插入
        commitDao.insert(commit);
        // 按hash和branch查找
        Commit queryCommit = commitDao.queryByHashAndBranchId(commit.getCommitHash(), commit.getBranchId());
        return queryCommit.getCommitId();
    }
}
