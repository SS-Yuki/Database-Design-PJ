package com.database.dao.impl;

import com.database.dao.RepositoryDao;
import com.database.object.Repository;
import com.database.utils.JDBCUtil;

public class RepositoryDaoImpl implements RepositoryDao {
    @Override
    public int insert(Repository repository) {
        String sql = "insert into repository (repositoryName, baseDir) values (?, ?);";
        return JDBCUtil.update(sql, repository.getRepositoryName(), repository.getBaseDir());
    }

    @Override
    public Repository queryById(int id) {
        String sql = "select repositoryId, repositoryName, baseDir from repository where repositoryId = ?";
        return JDBCUtil.queryOne(Repository.class, sql, id);
    }

    @Override
    public Repository queryByNameAndDir(String name, String dir) {
        String sql = "select repositoryId, repositoryName, baseDir from repository where repositoryName = ? and baseDir = ?";
        return JDBCUtil.queryOne(Repository.class, sql, name, dir);
    }

}
