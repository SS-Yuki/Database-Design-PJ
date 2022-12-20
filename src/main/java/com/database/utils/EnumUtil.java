package com.database.utils;

import com.database.common.IssueCaseType;

public class EnumUtil {
    //RawIssueType -> IssueCaseType
    public static IssueCaseType RawIssueType2IssueCaseType(String type) {
        IssueCaseType issueCaseType = IssueCaseType.BUG;
        switch(type){
            case "BUG" :
                issueCaseType = IssueCaseType.BUG;
                break;
            case "VULNERABILITY" :
                issueCaseType = IssueCaseType.VULNERABILITY;
                break;
            case "CODE_SMELL" :
                issueCaseType = IssueCaseType.CODE_SMELL;
                break;
            default :
                break;
        }
        return issueCaseType;
    }

    public static IssueCaseType Int2IssueCaseType(int type) {
        IssueCaseType issueCaseType = IssueCaseType.BUG;
        switch (type) {
            case 1:
                issueCaseType = IssueCaseType.BUG;
                break;
            case 2:
                issueCaseType = IssueCaseType.CODE_SMELL;
                break;
            case 3:
                issueCaseType = IssueCaseType.VULNERABILITY;
                break;
            default:
                break;
        }
        return issueCaseType;
    }
}
