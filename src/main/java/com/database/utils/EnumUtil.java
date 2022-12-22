package com.database.utils;

import com.database.common.IssueCaseStatus;
import com.database.common.IssueCaseType;
import com.database.common.IssueInstanceStatus;

public class EnumUtil {
    //IssueCaseType
    public static IssueCaseType IssueCaseTypeString2Enum(String type) {
        switch(type){
            case "BUG" :
                return IssueCaseType.BUG;
            case "VULNERABILITY" :
                return IssueCaseType.VULNERABILITY;
            case "CODE_SMELL" :
                return IssueCaseType.CODE_SMELL;
            default :
                return IssueCaseType.BUG;
        }
    }

    public static String Enum2String(IssueCaseType issueCaseType) {
        switch(issueCaseType){
            case BUG :
                return "BUG";
            case VULNERABILITY:
                return "VULNERABILITY";
            case CODE_SMELL:
                return "CODE_SMELL";
            default :
                return "";
        }
    }



    //IssueCaseStatus
    public static IssueCaseStatus IssueCaseStatusString2Enum(String status) {
        switch(status){
            case "UNSOLVED" :
                return IssueCaseStatus.UNSOLVED;
            case "SOLVED" :
                return IssueCaseStatus.SOLVED;
            default :
                return IssueCaseStatus.UNSOLVED;
        }
    }

    public static String Enum2String(IssueCaseStatus issueCaseStatus) {
        switch(issueCaseStatus){
            case UNSOLVED:
                return "UNSOLVED";
            case SOLVED:
                return "SOLVED";
            default :
                return "";
        }
    }


    //IssueInstanceStatus
    public static IssueInstanceStatus IssueInstanceStatusString2Enum(String status) {
        switch(status){
            case "APPEAR" :
                return IssueInstanceStatus.APPEAR;
            case "EVOLVE" :
                return IssueInstanceStatus.EVOLVE;
            case "DISAPPEAR" :
                return IssueInstanceStatus.DISAPPEAR;
            default :
                return IssueInstanceStatus.APPEAR;
        }
    }

    public static String Enum2String(IssueInstanceStatus issueInstanceStatus) {
        switch(issueInstanceStatus){
            case APPEAR:
                return "APPEAR";
            case EVOLVE:
                return "EVOLVE";
            case DISAPPEAR:
                return "DISAPPEAR";
            default :
                return "";
        }
    }

}
