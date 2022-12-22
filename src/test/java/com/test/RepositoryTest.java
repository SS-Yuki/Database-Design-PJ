package com.test;

import com.database.dao.RepositoryDao;
import com.database.dao.impl.RepositoryDaoImpl;
import com.database.object.Repository;
import com.database.service.RepositoryService;
import com.database.service.impl.RepositoryServiceImpl;
import org.junit.Test;

public class RepositoryTest {

    @Test
    public void testInsert() {
        Repository repository = new Repository(0, "test_!!", "test!");
        RepositoryDao repositoryDao = new RepositoryDaoImpl();
        repositoryDao.insert(repository);
    }

    @Test
    public void testQueryById() {
        int id = 3;
        RepositoryDao repositoryDao = new RepositoryDaoImpl();
        Repository repository = repositoryDao.queryById(id);
        System.out.println(repository);
    }

    @Test
    public void testQueryByNameAndDir() {
        String name = "test_!!";
        String dir = "test!";
        RepositoryDao repositoryDao = new RepositoryDaoImpl();
        Repository repository = repositoryDao.queryByNameAndDir(name, dir);
        System.out.println(repository);
    }

    @Test
    public void testServiceInsert() {
        Repository repository = new Repository(0, "insert_name2", "insert_base_dir");
        RepositoryService repositoryService = new RepositoryServiceImpl();
        int id = repositoryService.insert(repository);
        System.out.println(id);
    }

}
