package com.database.service;

import com.database.object.Commit;

import java.sql.Connection;
import java.util.List;

public interface CommitService {

    /**
     * 插入一条commit记录，并返回id
     * @param commit
     * @return
     */
    int insert(Connection connection, Commit commit);

    /**
     * 根据branch_id返回所有的commit_id
     * @param branch_id
     * @return
     */
    int[] getIdByBranchId(Connection connection, int branch_id);

    /**
     * 根据commit_id返回对应的commit_hash
     * @param commit_id
     * @return
     */
    String getHashById(Connection connection, int commit_id);

    Commit getLatestByBranchId(Connection connection, int branch_id);

    List<Commit> getAllChildren(Connection connection, int branch_id, int commit_id);
}