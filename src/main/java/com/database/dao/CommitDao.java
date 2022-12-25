package com.database.dao;

import com.database.object.Commit;

import java.util.List;

public interface CommitDao {

    /**
     * 根据commit信息插入一个字段
     * @param commit
     */
    int insert(Commit commit);

    /**
     * 根据commit的id查找一个commit
     * @param id
     * @return
     */
    Commit queryById(int id);

    /**
     * 根据commit的hash和分支id查找一个commit
     * @param hash
     * @param branch_id
     * @return
     */
    Commit queryByHashAndBranchId(String hash, int branch_id);

    /**
     * 根据branch的id查找所有的commit
     * @param branch_id
     * @return
     */
    List<Commit> queryByBranchId(int branch_id);

    Commit queryLatestCommitByBranchId(int branch_id);

}