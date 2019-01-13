package dianfeng.iot.openstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableCaching
public class OpenStatusApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenStatusApplication.class, args);
    }

}
