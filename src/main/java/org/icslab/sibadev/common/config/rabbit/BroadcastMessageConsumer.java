package org.icslab.sibadev.common.config.rabbit;

import org.icslab.sibadev.common.config.rabbit.domain.EstablishMessage;
import org.icslab.sibadev.common.config.rabbit.domain.KeepAliveMessage;
import org.icslab.sibadev.common.config.rabbit.domain.DeivceEstablishMessage;
import org.icslab.sibadev.devices.device.services.DeviceEstablishService;
import org.icslab.sibadev.devices.vhub.services.HubEstablishService;
import org.icslab.sibadev.devices.vhub.services.VirtualHubKeepAliveService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BroadcastMessageConsumer {

    @Autowired
    private VirtualHubKeepAliveService virtualHubKeepAliveService;

    @Autowired
    private HubEstablishService hubEstablishService;

    @Autowired
    private DeviceEstablishService deviceEstablishService;


    @RabbitListener(queues = {RabbitMQConstants.KEEP_ALIVE_QUEUE})
    public void keepAlive(final KeepAliveMessage message) {

        //Entity chargeOrder = MapperUtil.writeStringAsObject(message, Entity.class);
        virtualHubKeepAliveService.keep(message.getId());
        System.out.println(message.getId());
    }

    @RabbitListener(queues = {RabbitMQConstants.ESTABLISH_QUEUE})
    public void establishAck(final EstablishMessage message) {
        hubEstablishService.establish(message.getId(), message.getIp(), message.getPort());
    }

    @RabbitListener(queues = {RabbitMQConstants.DEVICE_ESTABLISH_QUEUE})
    public void deviceEstablishAck(final DeivceEstablishMessage message) {
        System.out.println("device connect");
        System.out.println(message);
        deviceEstablishService.establish(message.getDevKey(), message.getMac());
    }
}