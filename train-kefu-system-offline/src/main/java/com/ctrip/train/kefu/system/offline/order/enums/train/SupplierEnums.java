package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

/**
 * 供应商枚举
 */
public enum SupplierEnums {
    Ctrip{
        @Override public String getValue() { return "ctrip"; }
        @Override public String getName() { return "携程"; }
    },
    Qunar{
        @Override public String getValue() { return "qunar"; }
        @Override public String getName() { return "去哪儿"; }
    },
    E19{
        @Override public String getValue() { return "19e"; }
        @Override public String getName() { return "19E"; }
    },
    Jiulx{
        @Override public String getValue() { return "jiulx"; }
        @Override public String getName() { return "就旅行"; }
    };
    public static SupplierEnums convertSupplier(String state){
        return Arrays.stream(SupplierEnums.values()).filter(it->state.equals(it.getValue())).findFirst().orElse(null);
    }
    public abstract String getValue();
    public abstract String getName();
}
