package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmallEnumRequest {
    private Long   tid;
    private String fieldType;
    private String fieldName;
    private String fieldValue;
    private Long   upperTid;
    private Integer isDeleted;
}
