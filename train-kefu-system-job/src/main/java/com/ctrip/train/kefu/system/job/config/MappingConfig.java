package com.ctrip.train.kefu.system.job.config;


import com.ctrip.train.kefu.system.job.worker.domain.StaffPriority;
import com.ctrip.train.kefu.system.job.worker.domain.StaffPriorityResult;
import org.modelmapper.PropertyMap;

public class MappingConfig {

    public static PropertyMap<StaffPriorityResult,StaffPriority> MapLimit=new  PropertyMap<StaffPriorityResult,StaffPriority>(){
        @Override
        protected void configure(){
            map(source.getComplainWaitLimit(),destination.getComplainWaitLimit());
            map(source.getNoticeWaitLimit(),destination.getNoticeWaitLimit());
        }
    };
}


