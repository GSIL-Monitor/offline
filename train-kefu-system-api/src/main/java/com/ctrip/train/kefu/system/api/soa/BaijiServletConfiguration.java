package com.ctrip.train.kefu.system.api.soa;

import org.springframework.context.annotation.Configuration;

import com.ctriposs.baiji.rpc.extensions.springboot.BaijiRegistrationBean;

@Configuration
public class BaijiServletConfiguration extends BaijiRegistrationBean {

	protected BaijiServletConfiguration() {
		super("/api/*", OfflineServiceImpl.class);
	}

}
