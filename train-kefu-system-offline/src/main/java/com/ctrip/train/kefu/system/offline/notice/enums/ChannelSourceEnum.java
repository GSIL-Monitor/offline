package com.ctrip.train.kefu.system.offline.notice.enums;

import java.util.Arrays;

/**
 * 渠道枚举
 */
public enum ChannelSourceEnum {
    Ctrip(77,"携程","Ctrip.Train"){
        @Override
        public String getOutBounfNum() {return "#9921";}
    },
    Tieyou(78,"铁友","tieyou"){
        @Override
        public String getOutBounfNum() {return "#9923";}
    },
    Zhixing(2070,"智行","zhixing"){
        @Override
        public String getOutBounfNum() {return "#9920";}
    },
    Qunar(4886,"去哪儿","qunar"){
        @Override
        public String getOutBounfNum() {return "#9922";}
    },
    Default(79,"其他","other"){
        @Override
        public String getOutBounfNum() {return "#9921";}
    };
    /**
     * datasource stored in scm_small_enum table
     * and the field_type=datasource
     */
    private int value;
    private String name;
    private String partnerName;

    ChannelSourceEnum(int value, String name, String partnerName) {
        this.value = value;
        this.name = name;
        this.partnerName = partnerName;
    }

    public static ChannelSourceEnum convertDatasource(String partnerName) {
        switch (partnerName) {
            case "Ctrip.Train":
                return Ctrip;
            case "tieyou":
                return Tieyou;
            case "zhixing":
                return Zhixing;
            case "qunar":
            case "qunarsync":
                return Qunar;
            default:
                return Default;
        }
    }
    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getPartnerName() {
        return partnerName;
    }
    public static ChannelSourceEnum convertChannelSourceEnum(int channel){
        return Arrays.stream(ChannelSourceEnum.values()).filter(it->it.getValue()==channel).findFirst().orElse(null);
    }

    public abstract String getOutBounfNum();

}
