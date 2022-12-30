package com.database.service;

import com.database.object.Branch;

import java.sql.Connection;

public interface BranchService {

    /**
     * 插入一条branch，并返回插入得到的id
     * @param branch
     * @return
     */
    int insert(Connection connection, Branch branch);

    int[] getIdByRepoId(Connection connection, int repository_id);

    int getIdByNameAndRepoId(Connection connection, int repository_id, String name);

    String getNameById(Connection connection, int branch_id);
}
