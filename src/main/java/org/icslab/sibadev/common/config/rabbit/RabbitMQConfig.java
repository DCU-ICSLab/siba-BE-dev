package org.icslab.sibadev.common.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    //---------------------------------
    //queue
    @Bean
    Queue keepAliveQueue() {
        return new Queue(RabbitMQConstants.KEEP_ALIVE_QUEUE, false);
    }

    @Bean
    Queue establishQueue() {
        return new Queue(RabbitMQConstants.ESTABLISH_QUEUE, false);
    }

    @Bean
    Queue deviceEstablishQueue() {
        return new Queue(RabbitMQConstants.DEVICE_ESTABLISH_QUEUE, false);
    }

    @Bean
    Queue deviceControlQueue() {
        return new Queue(RabbitMQConstants.DEVICE_CONTROL_QUEUE, false);
    }

    //---------------------------------
    //exchange
    @Bean
    TopicExchange keepAliveTopicExchange() {
        return new TopicExchange(RabbitMQConstants.KEEP_ALIVE_EXCHANGE);
    }

    @Bean
    TopicExchange establishExchange() {
        return new TopicExchange(RabbitMQConstants.ESTABLISH_EXCHANGE);
    }

    @Bean
    TopicExchange deviceEstablishExchange() {
        return new TopicExchange(RabbitMQConstants.DEVICE_ESTABLISH_EXCHANGE);
    }

    @Bean
    TopicExchange deviceControlExchange() {
        return new TopicExchange(RabbitMQConstants.DEVICE_CONTROL_EXCHANGE);
    }

    //---------------------------------
    //binding
    @Bean
    Binding bindingKeepAlive(Queue keepAliveQueue, TopicExchange keepAliveTopicExchange) {
        return BindingBuilder
                .bind(keepAliveQueue)
                .to(keepAliveTopicExchange)
                .with(RabbitMQConstants.KEEP_ALIVE_ROUTE_KEY);
    }

    @Bean
    Binding bindingEstablish(Queue establishQueue, TopicExchange establishExchange) {
        return BindingBuilder
                .bind(establishQueue)
                .to(establishExchange)
                .with(RabbitMQConstants.ESTABLISH_ROUTE_KEY);
    }

    @Bean
    Binding bindingDeviceEstablish(Queue deviceEstablishQueue, TopicExchange deviceEstablishExchange) {
        return BindingBuilder
                .bind(deviceEstablishQueue)
                .to(deviceEstablishExchange)
                .with(RabbitMQConstants.DEVICE_ESTABLISH_ROUTE_KEY);
    }

    @Bean
    Binding bindingDeviceControl(Queue deviceControlQueue, TopicExchange deviceControlExchange) {
        return BindingBuilder
                .bind(deviceControlQueue)
                .to(deviceControlExchange)
                .with(RabbitMQConstants.DEVICE_CONTROL_ROUTE_KEY);
    }

    //---------------------------------
    //definition
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
