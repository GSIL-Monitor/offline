package com.ctrip.train.kefu.system.offline.common.utils;
public class TrainGrabLevel {

    public static String getGrabLevel(int level, String channel) {
        String levelName="";
            if (channel.equalsIgnoreCase("Ctrip.Train") ) {
                levelName = ctripLevel(level);
            } else if("tieyou".equalsIgnoreCase(channel)||"zhixing".equalsIgnoreCase(channel)) {
                levelName = tieyouLevel(level);
            } else if(channel.toLowerCase().contains("qunar")) {
                levelName = qunarLevel(level);
            }
        return levelName;
    }
    private static String ctripLevel(int level) {
        String levelName = "低速"; ;
        if (level >= 0 && level <= 4)
            levelName = "低速";
        else if (level >= 5 && level <= 19)
            levelName = "快速";
        else if (level >= 20 && level <= 34)
            levelName = "高速";
        else if (level >= 35 && level <= 49)
            levelName = "极速";
        else if (level >= 50 && level <= 79)
            levelName = "光速";
        else if(level>=80)
            levelName = "VIP";
        return levelName;
    }
    private static String tieyouLevel(int level) {
        String levelName = "低速";
        if (level >= 0 && level <= 9)
            levelName = "低速";
        else if (level >= 10 && level <= 19)
            levelName = "中速";
        else if (level >= 20 && level <= 29)
            levelName = "快速";
        else if (level >= 30 && level <= 49)
            levelName = "高速";
        else if (level >= 50)
            levelName = "VIP";
        return levelName;
    }
    private static String qunarLevel(int level) {
        String levelName = "";

        return levelName;
    }

}
