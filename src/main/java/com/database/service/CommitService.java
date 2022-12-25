package com.database.service;

import com.database.object.Commit;

public interface CommitService {

    /**
     * 插入一条commit记录，并返回id
     * @param commit
     * @return
     */
    int insert(Commit commit);

    /**
     * 根据branch_id返回所有的commit_id
     * @param branch_id
     * @return
     */
    int[] getIdByBranchId(int branch_id);

    /**
     * 根据commit_id返回对应的commit_hash
     * @param commit_id
     * @return
     */
    String getHashById(int commit_id);

    Commit getLatestByBranchId(int branch_id);
}