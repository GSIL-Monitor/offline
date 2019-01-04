package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SmallEnumResponse {
    private List<Map<String, Object>> noticeEnums;
    private List<Map<String, Object>> complainEnums;
    private List<Map<String, Object>> leaderEnums;
}
