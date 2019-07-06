package org.icslab.sibadev.common.config.rabbit;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private String exchange = "direct.exchange";

    private String keepAliveRouteKey = "keepalive.route";

    private static final String KEEP_ALIVE_QUEUE = "keepalive.queue";


}
