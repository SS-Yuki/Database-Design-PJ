package com.database.service.impl;

import com.database.dao.RepositoryDao;
import com.database.dao.impl.RepositoryDayImpl;
import com.database.object.Repository;
import com.database.service.RepositoryService;

public class RepositoryServiceImpl implements RepositoryService {

    private RepositoryDao repositoryDao = new RepositoryDayImpl();

    @Override
    public int insert(Repository repository) {
        // 插入记录
        repositoryDao.insert(repository);
        // 根据name和dir确定id
        Repository queryRepository = repositoryDao.queryByNameAndDir(repository.getRepositoryName(), repository.getBaseDir());
        return queryRepository.getRepositoryId();
    }
}
