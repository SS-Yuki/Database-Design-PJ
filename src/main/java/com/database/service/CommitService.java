package com.database.service;

import com.database.object.Commit;

public interface CommitService {

    /**
     * 插入一条commit记录，并返回id
     * @param commit
     * @return
     */
    int insert(Commit commit);

}
