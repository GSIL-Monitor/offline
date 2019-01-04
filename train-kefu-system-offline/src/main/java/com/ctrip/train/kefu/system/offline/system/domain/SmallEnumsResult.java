package com.ctrip.train.kefu.system.offline.system.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmallEnumsResult {
    private Long   tid;
    private String fieldType;
    private String fieldName;
    private String fieldValue;
    private Long   upperTid;
}
