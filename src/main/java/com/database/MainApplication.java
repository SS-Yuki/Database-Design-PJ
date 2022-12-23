package com.database;

import com.database.sonar.SonarService;
import com.database.sonar.impl.SonarServiceImpl;
import com.database.utils.InitUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication {

    public static void main(String[] args) throws Exception {
        InitUtil.createTable();
        SonarService sonarService = new SonarServiceImpl();
        printHelp();
        Scanner scanner = new Scanner(System.in);
        String command = "";
        boolean flag = true;
        while (flag) {
            command = scanner.nextLine();
            switch (command) {
                case "help": printHelp(); break;
                case "latest": sonarService.showLatestInstInfo(1, "main");
                case "commit": sonarService.showInstInfoByCommit(111); break;
                case "time": sonarService.showCaseInfoByTime(new Date(1670591160), new Date()); break;
                case "commiter": sonarService.showCaseInfoByCommiter("xxx"); break;
                case "import": sonarService.importRepository("C:\\Users\\yuki\\Desktop\\1", "SonarTest"); break;
                case "exit": flag = false; break;
            }
        }
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
