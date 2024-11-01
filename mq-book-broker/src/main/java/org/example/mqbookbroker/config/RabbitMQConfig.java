package org.example.mqbookbroker.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Queue orderRequest() {
        return new Queue("order-request-queue");
    }
    @Bean
    public FanoutExchange orderRequestExchange() {
        return new FanoutExchange("order-request-exchange");
    }
    @Bean
    public Binding bindingOrderRequest() {
        return BindingBuilder.bind(orderRequest()).to(orderRequestExchange());
    }

    @Bean
    public Queue orderResponseSuccess() {
        return new Queue("order-response-success-queue");
    }
    @Bean
    public FanoutExchange orderResponseSuccessExchange() {
        return new FanoutExchange("order-response-success-exchange");
    }
    @Bean
    public Binding bindingOrderSuccess() {
        return BindingBuilder.bind(orderResponseSuccess()).to(orderResponseSuccessExchange());
    }
}
