package com.database;

import com.database.command.SonarCommand;
import com.database.sonar.SonarService;
import com.database.sonar.impl.SonarServiceImpl;
import com.database.utils.InitUtil;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainApplication {

    public static void main(String[] args) {
        InitUtil.createTable();
        SonarService sonarService = new SonarServiceImpl();
        printHelp();
        Scanner scanner = new Scanner(System.in);
        String commandLine = "";
        SonarCommand command = null;
        List<Object> params = null;
        boolean flag = true;
        while (flag) {
            System.out.print("请输入您的指令：");
            commandLine = scanner.nextLine();
            String[] argvs = commandLine.split(" ");
            // 转化成指令
            try {
                command = SonarCommand.getSonarCommand(argvs[0], argvs);
                params = command.getParams();
                // 转化成功，正常处理
                switch (command.getCommandType()) {
                    case HELP: printHelp(); break;
                    case LATEST: sonarService.showLatestInstInfo((Integer) params.get(0), (String) params.get(1)); break;
                    case COMMIT_INST: sonarService.showInstInfoByCommit((Integer) params.get(0)); break;
                    case COMMIT_CASE: sonarService.showCaseInfoByCommit((Integer) params.get(0)); break;
                    case TIME: sonarService.showCaseInfoByTime((Date) params.get(0), (Date) params.get(1)); break;
                    case COMMITER: sonarService.showCaseInfoByCommiter((String) params.get(0)); break;
                    case DURATION: sonarService.showCaseInfoByDuration((Integer) params.get(0), (String) params.get(1), (Integer) params.get(2)); break;
                    case IMPORT: sonarService.importRepository((String) params.get(0), (String) params.get(1)); break;
                    case EXIT: flag = false; break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void printHelp() {
        System.out.println("help                                                查看所有指令");
        System.out.println("latest [repository id] [branch name]                查看指定仓库指定分支最新版本的缺陷存在情况");
        System.out.println("commit-inst [commit id]                             查看指定版本的缺陷存在情况");
        System.out.println("commit-case [commit id]                             查看指定版本的缺陷引入和解决情况");
        System.out.println("time [start] [end] (yyyy-MM-dd)                     查看指定时间段内缺陷引入和解决情况");
        System.out.println("commiter [commiter]                                 查看指定人员的缺陷引入和解决情况");
        System.out.println("duration [repository id] [branch name] [duration]   查看指定仓库中存续时间超过指定天数的缺陷情况");
        System.out.println("import [base dir] [repository name]                 导入代码仓库");
        System.out.println("exit                                                退出程序");
    }

}