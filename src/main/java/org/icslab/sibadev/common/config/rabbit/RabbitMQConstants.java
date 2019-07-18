package org.icslab.sibadev.common.config.rabbit;

public class RabbitMQConstants {

    //exchange

    public static final String KEEP_ALIVE_EXCHANGE = "keepalive";

    public static final String ESTABLISH_EXCHANGE = "establish";

    public static final String DEVICE_ESTABLISH_EXCHANGE = "device.establish";


    //route

    public static final String KEEP_ALIVE_ROUTE_KEY = "keepalive.route";

    public static final String ESTABLISH_ROUTE_KEY = "establish.route";

    public static final String DEVICE_ESTABLISH_ROUTE_KEY = "device.establish.route";

    //queue

    public static final String KEEP_ALIVE_QUEUE = "keepalive.queue";

    public static final String ESTABLISH_QUEUE = "establish.queue";

    public static final String DEVICE_ESTABLISH_QUEUE = "device.establish.queue";
}
