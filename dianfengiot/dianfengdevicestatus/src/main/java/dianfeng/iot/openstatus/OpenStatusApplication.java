package dianfeng.iot.openstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;

@SpringBootApplication
@EnableDiscoveryClient
public class OpenStatusApplication {

    @Autowired
    Runnable MessageListener;

    public static void main(String[] args) {
        SpringApplication.run(OpenStatusApplication.class, args);
    }

    @Bean
    public CommandLineRunner schedulingRunner(TaskExecutor executor) {
        return new CommandLineRunner() {
            public void run(String... args) throws Exception {
                executor.execute(MessageListener);
            }
        };
    }
}
