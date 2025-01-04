package broker;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
public class ApplicationKafka {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationKafka.class, args);
    }

}