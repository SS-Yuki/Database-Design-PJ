package com.database.service.impl;

import com.database.dao.CommitDao;
import com.database.dao.impl.CommitDaoImpl;
import com.database.object.Commit;
import com.database.service.CommitService;

import java.util.List;

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

    @Override
    public int[] getIdByBranchId(int branch_id) {
        List<Commit> commits = commitDao.queryByBranchId(branch_id);
        int[] ids = new int[commits.size()];
        for (int i = 0; i < commits.size(); i++) {
            ids[i] = commits.get(i).getCommitId();
        }
        return ids;
    }

    @Override
    public String getHashById(int commit_id) {
        Commit commit = commitDao.queryById(commit_id);
        return commit.getCommitHash();
    }
}
