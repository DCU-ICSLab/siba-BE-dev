package org.icslab.sibadev.common.config.redis;

import org.icslab.sibadev.common.config.redis.listeners.KeepAliveKeyExpirationListener;
import org.icslab.sibadev.devices.test.domain.TestLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@PropertySource("classpath:application.yml")
public class RedisConfig{

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Autowired
    private KeepAliveKeyExpirationListener keepAliveKeyExpirationListener;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory(this.host, this.port);
    }

    //keep alive template
    @Bean
    public RedisTemplate<String, Boolean> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Boolean.class));
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    //test management template
    @Bean
    public RedisTemplate<String, Long> redisTestManageTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    //redis 데이터 만료시 발생
    @Bean
    RedisMessageListenerContainer keyExpirationListenerContainer(LettuceConnectionFactory lettuceConnectionFactory) {

        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(lettuceConnectionFactory);

        //expire event handling
        listenerContainer.addMessageListener(keepAliveKeyExpirationListener, new PatternTopic("__keyevent@*__:expired"));

        return listenerContainer;
    }
}
