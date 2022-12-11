package Utils;

import common.IssueCaseType;

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
}
