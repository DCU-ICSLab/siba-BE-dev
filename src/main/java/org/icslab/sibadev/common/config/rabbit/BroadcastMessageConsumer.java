package org.icslab.sibadev.common.config.rabbit;

import org.icslab.sibadev.common.config.rabbit.domain.KeepAliveMessage;
import org.icslab.sibadev.devices.vhub.services.VirtualHubKeepAliveService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BroadcastMessageConsumer {

    @Autowired
    private VirtualHubKeepAliveService virtualHubKeepAliveService;


    @RabbitListener(queues = {RabbitMQConstants.KEEP_ALIVE_QUEUE})
    public void keepAlive(final KeepAliveMessage message) {

        //Entity chargeOrder = MapperUtil.writeStringAsObject(message, Entity.class);
        virtualHubKeepAliveService.keep(message.getId());
        System.out.println(message.getId());

    }
}