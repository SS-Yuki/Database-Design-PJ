package com.database.service.impl;

import com.database.object.IssueCase;
import com.database.object.IssueInstance;
import com.database.service.IssueCaseService;
import com.database.service.IssueInstanceService;
import com.database.service.SonarService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SonarServiceImpl implements SonarService {

    private IssueCaseService caseService = new IssueCaseServiceImpl();
    private IssueInstanceService instanceService = new IssueInstanceServiceImpl();

    @Override
    public void showInstanceByCommit(int commit) {
        List<IssueInstance> instances = instanceService.getInstByCommit(commit);
        instances.forEach(issueInstance -> System.out.println(issueInstance));
    }

    @Override
    public void showCaseByCommiter(String commiter) {

    }

    @Override
    public void showCaseByTime(Date time) {
        List<IssueCase> cases = caseService.getCaseByTime(time);
        // 指定时间后引入的
        List<IssueCase> appear = new ArrayList<>();
        // 指定时间后解决的
        List<IssueCase> solve = new ArrayList<>();
        cases.forEach(issueCase -> {
            // appear time > time
            if (issueCase.getAppearTime().after(time)) appear.add(issueCase);
            // solve time > time
            if (issueCase.getSolveTime().after(time)) solve.add(issueCase);
        });
        // 输出引入的
        System.out.println("指定时间后引入的：");
        appear.forEach(issueCase -> System.out.println(issueCase));
        // 输出解决的
        System.out.println("指定时间后解决的：");
        solve.forEach(issueCase -> System.out.println(issueCase));
    }

    @Override
    public boolean importRepository() {
        return false;
    }
}
