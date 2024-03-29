package com.database.dao;

import com.database.object.Branch;

import java.util.List;

public interface BranchDao {

    /**
     * 根据对象添加一个branch字段
     * @param branch    字段的数据，有效数据为branch name和 repository id
     */
    int insert(Branch branch);

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

    /**
     * 根据repository_id查询branch列表
     * @param repository_id
     * @return
     */
    List<Branch> queryByRepoId(int repository_id);
}
