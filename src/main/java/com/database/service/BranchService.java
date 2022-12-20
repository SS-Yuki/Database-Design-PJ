package com.database.service;

import com.database.object.Branch;

public interface BranchService {

    /**
     * 插入一条branch，并返回插入得到的id
     * @param branch
     * @return
     */
    int insert(Branch branch);

    int getNumByRepoId(int repository_id);

    int[] getIdByRepoId(int repository_id);

    int getIdByNameAndRepoId(int repository_id, String name);
}
