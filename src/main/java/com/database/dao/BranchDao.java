package com.database.dao;

import com.database.object.Branch;

public interface BranchDao {

    /**
     * 根据对象添加一个branch字段
     * @param branch    字段的数据，有效数据为branch name和 repository id
     */
    void insert(Branch branch);

    /**
     * 根据id查询一个branch
     * @param id
     * @return
     */
    Branch queryById(int id);

    /**
     * 根据name和repository_id查询一个branch字段
     * @param name
     * @param repository_id
     * @return
     */
    Branch queryByNameAndRepoId(String name, int repository_id);
}
