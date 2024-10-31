package org.example.mqbookbroker;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class MqBookBrokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqBookBrokerApplication.class, args);
    }

}
