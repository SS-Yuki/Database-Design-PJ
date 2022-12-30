package com.database.service.impl;

import com.database.dao.RepositoryDao;
import com.database.dao.impl.RepositoryDaoImpl;
import com.database.object.Repository;
import com.database.service.RepositoryService;

import java.sql.Connection;

public class RepositoryServiceImpl implements RepositoryService {

    private RepositoryDao repositoryDao = new RepositoryDaoImpl();

    @Override
    public int insert(Connection connection, Repository repository) {
        // 插入记录
        return repositoryDao.insert(connection, repository);
    }
}
