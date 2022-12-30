package com.database.dao;

import com.database.object.Repository;

import java.sql.Connection;

/**
 * 处理repository的数据库操作
 */
public interface RepositoryDao {

    /**
     * 根据传入的repository对象插入数据库表
     * @param repository 封装插入的字段值，id无效，只通过name和dir插入
     */
    int insert(Connection connection, Repository repository);

    /**
     * 根据传入的id查询到repository对象
     * @param id
     * @return
     */
    Repository queryById(Connection connection, int id);

}
