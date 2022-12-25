package com.database.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SonarCommand {

    private SonarCommandType commandType;
    private List<Object> params;

    private SonarCommand(SonarCommandType commandType, List<Object> params) {
        this.commandType = commandType;
        this.params = params;
    }

    public static SonarCommand getSonarCommand(String type, String[] strs) throws Exception{
        List<Object> params = new ArrayList<>();
        switch (type) {
            case "help": {
                // 不需要params
                return new SonarCommand(SonarCommandType.HELP, params);
            }
            case "latest": {
                // 从strs中得到repository id和branch name
                params.add(Integer.valueOf(strs[1]));
                params.add(strs[2]);
                return new SonarCommand(SonarCommandType.LATEST, params);
            }
            case "commit-inst": {
                // 从strs中得到commit hash
                params.add(Integer.valueOf(strs[1]));
                return new SonarCommand(SonarCommandType.COMMIT_INST, params);
            }
            case "commit-case": {
                // 从strs中得到commit hash
                params.add(Integer.valueOf(strs[1]));
                return new SonarCommand(SonarCommandType.COMMIT_CASE, params);
            }
            case "time": {
                // 从strs中得到start time和end time
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                params.add(format.parse(strs[1]));
                params.add(format.parse(strs[2]));
                return new SonarCommand(SonarCommandType.TIME, params);
            }
            case "commiter": {
                // 从strs中得到commiter
                params.add(strs[1]);
                return new SonarCommand(SonarCommandType.COMMITER, params);
            }
            case "duration": {
                // 需要仓库id、分支name以及时间限制
                params.add(Integer.valueOf(strs[1]));
                params.add(strs[2]);
                params.add(Integer.valueOf(strs[3]));
                return new SonarCommand(SonarCommandType.DURATION, params);
            }
            case "import": {
                // 从strs中得到base dir和project name
                params.add(strs[1]);
                params.add(strs[2]);
                return new SonarCommand(SonarCommandType.IMPORT, params);
            }
            case "exit": {
                // 不需要params
                return new SonarCommand(SonarCommandType.EXIT, params);
            }
            default: {
                throw new Exception("指令非法");
            }
        }
    }

    public SonarCommandType getCommandType() {
        return commandType;
    }

    public List<Object> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "SonarCommand{" +
                "commandType=" + commandType +
                ", params=" + params +
                '}';
    }

}