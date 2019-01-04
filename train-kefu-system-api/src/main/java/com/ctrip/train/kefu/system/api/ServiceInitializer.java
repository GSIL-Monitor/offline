package com.ctrip.train.kefu.system.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// 默认只会Component Scan com.ctrip.train.kefu.system.api以及其子package，如果需要Scan更多的package可以使用@SpringBootApplication(scanBasePackages = {"com.ctrip.train.kefu.system.online", "other package"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication( scanBasePackages = {
        "com.ctrip.train.kefu.system.api",
        "com.ctrip.train.kefu.system.client"},
        exclude = {DataSourceAutoConfiguration.class})
@ServletComponentScan
public class ServiceInitializer extends SpringBootServletInitializer {

  /**
   * Configure your application when it’s launched by the servlet container
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ServiceInitializer.class);
  }
}
