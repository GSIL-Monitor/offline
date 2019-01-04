package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VmTrainOrderOperate {

    //备注信息
    List<VmRemarks> operates;

    //按钮信息
    VmTrainButton buttonsFlag;

}
