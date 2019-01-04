package com.ctrip.train.kefu.system.offline;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

// 默认只会Component Scan com.ctrip.train.kefu.system.offline以及其子package，如果需要Scan更多的package可以使用@SpringBootApplication(scanBasePackages = {"com.ctrip.train.kefu.system.offline", "other package"})
@SpringBootApplication(
        scanBasePackages = {
                "com.ctrip.train.kefu.system.offline",
                "com.ctrip.train.kefu.system.client"},
        exclude = {DataSourceAutoConfiguration.class, ErrorMvcAutoConfiguration.class})
@ServletComponentScan
public class WebInitializer extends SpringBootServletInitializer {

  /**
   * Configure your application when it’s launched by the servlet container
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(WebInitializer.class);
  }
}
