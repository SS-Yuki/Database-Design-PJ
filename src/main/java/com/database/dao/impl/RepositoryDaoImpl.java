package com.database.dao.impl;

import com.database.dao.RepositoryDao;
import com.database.object.Repository;
import com.database.utils.JDBCUtil;

import java.sql.Connection;

public class RepositoryDaoImpl implements RepositoryDao {
    @Override
    public int insert(Connection connection, Repository repository) {
        String sql = "insert into repository (repositoryName, baseDir) values (?, ?);";
        return JDBCUtil.update(connection, sql, repository.getRepositoryName(), repository.getBaseDir());
    }

    @Override
    public Repository queryById(Connection connection, int id) {
        String sql = "select repositoryId, repositoryName, baseDir from repository where repositoryId = ?";
        return JDBCUtil.queryOne(connection, Repository.class, sql, id);
    }

}
