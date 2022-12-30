package com.database.dao;

import com.database.object.Commit;

import java.sql.Connection;
import java.util.List;

public interface CommitDao {

    /**
     * 根据commit信息插入一个字段
     * @param commit
     */
    int insert(Connection connection, Commit commit);

    /**
     * 根据commit的id查找一个commit
     * @param id
     * @return
     */
    Commit queryById(Connection connection, int id);

    /**
     * 根据branch的id查找所有的commit
     * @param branch_id
     * @return
     */
    List<Commit> queryByBranchId(Connection connection, int branch_id);

    Commit queryLatestCommitByBranchId(Connection connection, int branch_id);

    List<Commit> queryAllChildrenByBranchIdAndCommitId(Connection connection, int branch_id, int commit_id);
}