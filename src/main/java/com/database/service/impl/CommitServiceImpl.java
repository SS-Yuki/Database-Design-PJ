package com.database.service.impl;

import com.database.dao.CommitDao;
import com.database.dao.impl.CommitDaoImpl;
import com.database.object.Commit;
import com.database.service.CommitService;

import java.sql.Connection;
import java.util.List;

public class CommitServiceImpl implements CommitService {

    private CommitDao commitDao = new CommitDaoImpl();

    @Override
    public int insert(Connection connection, Commit commit) {
        // 插入
        return commitDao.insert(connection, commit);
    }

    @Override
    public int[] getIdByBranchId(Connection connection, int branch_id) {
        List<Commit> commits = commitDao.queryByBranchId(connection, branch_id);
        int[] ids = new int[commits.size()];
        for (int i = 0; i < commits.size(); i++) {
            ids[i] = commits.get(i).getCommitId();
        }
        return ids;
    }

    @Override
    public String getHashById(Connection connection, int commit_id) {
        Commit commit = commitDao.queryById(connection, commit_id);
        return commit.getCommitHash();
    }

    @Override
    public Commit getLatestByBranchId(Connection connection, int branch_id) {
        return commitDao.queryLatestCommitByBranchId(connection, branch_id);
    }

    @Override
    public List<Commit> getAllChildren(Connection connection, int branch_id, int commit_id) {
        return commitDao.queryAllChildrenByBranchIdAndCommitId(connection, branch_id, commit_id);
    }

}