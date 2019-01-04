package com.ctrip.train.kefu.system.job;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

// 默认只会Component Scan com.ctrip.train.kefu.system.job以及其子package，如果需要Scan更多的package可以使用@SpringBootApplication(scanBasePackages = {"com.ctrip.train.kefu.system.job", "other package"})
@SpringBootApplication(scanBasePackages = {
        "com.ctrip.train.kefu.system.job",
        "com.ctrip.train.kefu.system.client"
},exclude = {DataSourceAutoConfiguration.class})
@ServletComponentScan
public class JobInitializer extends SpringBootServletInitializer {

  /**
   * Configure your application when it’s launched by the servlet container
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(JobInitializer.class);
  }
}
