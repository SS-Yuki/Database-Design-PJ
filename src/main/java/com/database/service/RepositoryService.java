package com.database.service;

import com.database.object.Repository;

public interface RepositoryService {

    /**
     * 插入一条repository记录，并返回插入生成的id
     * @param repository
     * @return
     */
    int insert(Repository repository);

}
