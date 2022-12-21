package com.database.service.impl;

import com.database.dao.BranchDao;
import com.database.dao.impl.BranchDaoImpl;
import com.database.object.Branch;
import com.database.service.BranchService;

import java.util.List;

public class BranchServiceImpl implements BranchService {

    private BranchDao branchDao = new BranchDaoImpl();

    @Override
    public int insert(Branch branch) {
        // 插入
        return branchDao.insert(branch);
    }

    @Override
    public int getNumByRepoId(int repository_id) {
        return branchDao.queryByRepoId(repository_id).size();
    }

    @Override
    public int[] getIdByRepoId(int repository_id) {
        List<Branch> branches = branchDao.queryByRepoId(repository_id);
        int[] branchIds = new int[branches.size()];
        for (int i = 0; i < branches.size(); i++) {
            branchIds[i] = branches.get(i).getBranchId();
        }
        return branchIds;
    }

    @Override
    public int getIdByNameAndRepoId(int repository_id, String name) {
        Branch branch = branchDao.queryByNameAndRepoId(name, repository_id);
        if (branch == null) return 0;
        return branch.getBranchId();
    }

}
