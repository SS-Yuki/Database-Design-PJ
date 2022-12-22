package com.database;

import com.database.sonar.SonarService;
import com.database.sonar.impl.SonarServiceImpl;
import com.database.utils.InitUtil;

import java.util.Scanner;

public class MainApplication {

    public static void main(String[] args) throws Exception {
//        InitUtil.createTable();
//        SonarService sonarService = new SonarServiceImpl();
//        printHelp();
//        Scanner scanner = new Scanner(System.in);
//        String command = "";
//        boolean flag = true;
//        while (flag) {
//            command = scanner.nextLine();
//            switch (command) {
////                case "help": printHelp(); break;
////                case "latest": sonarService.showLatestInfo(0, "");
////                case "commit": sonarService.showInfoByCommit(0); break;
////                case "time": sonarService.showCaseByTime(null, null); break;
////                case "commiter": sonarService.showCaseByCommiter(""); break;
//                case "import": sonarService.importRepository("C:\\Users\\yuki\\Desktop\\1", "SonarTest"); break;
////                case "exit": flag = false; break;
//            }
//        }
        InitUtil.createTable();
        SonarServiceImpl s = new SonarServiceImpl();
        s.importIssue(1, "C:\\Users\\yuki\\Desktop\\1\\SonarTest");
    }

    private static void printHelp() {
        System.out.println("--help      查看所有指令");
        System.out.println("--commit    查看指定版本");
        System.out.println("--time      查看指定时间");
        System.out.println("--commiter  查看指定人员");
        System.out.println("--import    导入代码仓库");
        System.out.println("--exit      退出程序");
    }

}
