package com.database.dao.impl;

import com.database.dao.RepositoryDao;
import com.database.object.Repository;
import com.database.utils.JDBCUtil;

public class RepositoryDaoImpl implements RepositoryDao {
    @Override
    public void insert(Repository repository) {
        String sql = "insert into repository (repository_name, base_dir) values (?, ?);";
        JDBCUtil.update(sql, repository.getRepositoryName(), repository.getBaseDir());
    }

    @Override
    public Repository queryById(int id) {
        String sql = "select repository_id repositoryId, repository_name repositoryName, base_dir baseDir from repository where repository_id = ?";
        return JDBCUtil.queryOne(Repository.class, sql, id);
    }

    @Override
    public Repository queryByNameAndDir(String name, String dir) {
        String sql = "select repository_id repositoryId, repository_name repositoryName, base_dir baseDir from repository where repository_name = ? and base_dir = ?";
        return JDBCUtil.queryOne(Repository.class, sql, name, dir);
    }

}
