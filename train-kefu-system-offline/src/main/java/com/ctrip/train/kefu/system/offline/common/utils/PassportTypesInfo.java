package com.ctrip.train.kefu.system.offline.common.utils;

public class PassportTypesInfo {
    public static String GetPassportType(String passportType)
    {
        String passport = "";
        switch (passportType)
        {
            case "01":
                passport = "身份证";
                break;
            case "02":
                passport = "护照";
                break;
            case "03":
                passport = "学生证";
                break;
            case "04":
                passport = "军人证";
                break;
            case "06":
                passport = "驾驶证";
                break;
            case "07":
                passport = "回乡证";
                break;
            case "08":
                passport = "台胞证";
                break;
            case "10":
                passport = "港澳通行证";
                break;
            case "11":
                passport = "国际海员证";
                break;
            case "20":
                passport = "外国人永久居留证";
                break;
            case "21":
                passport = "旅行证";
                break;
            case "22":
                passport = "台湾通行证";
                break;
            case "23":
                passport = "士兵证";
                break;
            case "24":
                passport = "临时身份证";
                break;
            case "25":
                passport = "户口簿";
                break;
            case "26":
                passport = "警官证";
                break;
            case "99":
                passport = "其它";
                break;
            default:
                passport = "其它";
                break;
        }
        return passport;
    }
}
