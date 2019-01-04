package com.ctrip.train.kefu.system.offline.notice.vm.notice;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RpsImportantNotice extends VmNotice {
    private String noticeTypeStr;
    private List<VmOperateInfo> operates;
}
