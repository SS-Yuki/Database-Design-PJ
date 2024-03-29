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
        return commitDao.insert(commit);
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

    @Override
    public Commit getLatestByBranchId(int branch_id) {
        return commitDao.queryLatestCommitByBranchId(branch_id);
    }

    @Override
    public List<Commit> getAllChildren(int branch_id, int commit_id) {
        return commitDao.queryAllChildrenByBranchIdAndCommitId(branch_id, commit_id);
    }
}