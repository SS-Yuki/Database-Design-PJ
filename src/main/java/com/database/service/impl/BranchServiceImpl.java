package com.database.service.impl;

import com.database.dao.BranchDao;
import com.database.dao.impl.BranchDaoImpl;
import com.database.object.Branch;
import com.database.service.BranchService;

public class BranchServiceImpl implements BranchService {

    private BranchDao branchDao = new BranchDaoImpl();

    @Override
    public int insert(Branch branch) {
        // 插入
        branchDao.insert(branch);
        // 查找
        Branch queryBranch = branchDao.queryByNameAndRepoId(branch.getBranchName(), branch.getRepositoryId());
        return queryBranch.getBranchId();
    }

    @Override
    public int getNumByRepoId(int repository_id) {
        return branchDao.queryByRepoId(repository_id).size();
    }

    @Override
    public int getIdByNameAndRepoId(int repository_id, String name) {
        Branch branch = branchDao.queryByNameAndRepoId(name, repository_id);
        if (branch == null) return 0;
        return branch.getBranchId();
    }

}
