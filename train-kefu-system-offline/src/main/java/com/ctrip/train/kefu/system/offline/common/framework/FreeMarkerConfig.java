package com.ctrip.train.kefu.system.offline.common.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;



@Configuration
public class FreeMarkerConfig {
    @Autowired
    protected freemarker.template.Configuration configuration;

    @Autowired
    protected MyTag tag;

    @Autowired
    protected MyPerm myPerm;

    /**
     * 添加自定义标签
     */
    @PostConstruct
    public void setSharedVariable() {
        configuration.setSharedVariable("menuTag", tag);

        configuration.setSharedVariable("permTag", myPerm);
    }
}
