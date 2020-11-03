package com.nav.myblog.comments;

/**
* 配置常量
* */
public class Constents {
    //队列名字
    public static final String QUEUE_NAME1 = "send_email";
    public static final String QUEUE_NAME2 = "send_msm";

    //交换机名字
    public static final String EXCHANGE_DIRECT = "directExchange";
    public static final String EXCHANGE_TOPIC = "topicExchange";
    public static final String EXCHANGE_FANOUT = "fanoutExchange";
}
