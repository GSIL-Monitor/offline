package com.ctrip.train.kefu.system.offline.common.component;

import com.ctrip.offlineBase.LoginState.EmpsInformationEntity;
import com.ctrip.train.kefu.system.offline.common.domain.OfflineStaffInfo;
import org.springframework.stereotype.Component;

/**
 * A manager that save the employ's information
 */
@Component
public class StaffInfoComponent {
    ThreadLocal<OfflineStaffInfo> empThreadLocal;

    public StaffInfoComponent() {
        empThreadLocal=new ThreadLocal<>();

    }

    public OfflineStaffInfo getEmpEntity(){
        return empThreadLocal.get();
    }
    public void  removeEmpEntity(){
        empThreadLocal.remove();
    }
    public void setEmpEntity(OfflineStaffInfo empEntity){
        empThreadLocal.set(empEntity);
    }
}
