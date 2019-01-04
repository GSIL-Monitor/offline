package com.ctrip.train.kefu.system.online;

import java.awt.Desktop;
import java.net.URI;

import com.ctrip.train.kefu.system.api.ServiceInitializer;
import org.springframework.boot.SpringApplication;

public class ServiceStarter {

  public static void main(String[] args) throws Exception {
    System.setProperty("java.awt.headless", "false");

    SpringApplication.run(ServiceInitializer.class);

    // port 9090 is configured in src/test/resources/application.properties(key: server.port)
    Desktop.getDesktop().browse(new URI("http://127.0.0.1:9090/api"));
  }
}
