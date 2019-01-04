package com.ctrip.train.kefu.system.offline.common.utils;

import common.util.StringUtils;

/**
 * 订单相关帮助类
 */
public class OrderHelper {

    /**
     * 隐藏用户身份证显示4位尾号
     */
    public static String SafeUserPassportNo(String raw, Boolean isOutSource){
        if (!isOutSource)
            return raw;
        if (!StringUtils.isEmpty(raw))
        {
            raw = raw.trim();
            if (raw.length() > 8)
            {
                return raw.substring(0,4)+"****" + raw.substring(raw.length() - 4);
            }
            return raw;
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取外呼码
     * @param partnerName
     * @return
     */
    public static String getOutboundNum(String partnerName)
    {
        if(partnerName.contains("Ctrip.Train"))
        {
            return "#9921";
        }
        else if (partnerName.contains("tieyou"))
        {
            return "#9923";
        }
        else if (partnerName.contains("zhixing"))
        {
            return "#9920";
        }
        else if (partnerName.contains("qunar"))
        {
            return "#9922";
        }
        return "";
    }
}
