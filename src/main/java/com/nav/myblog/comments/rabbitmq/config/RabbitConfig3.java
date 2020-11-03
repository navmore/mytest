package com.nav.myblog.comments.rabbitmq.config;

import com.nav.myblog.comments.Constents;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig3 {

    @Bean
    public Queue sendMailQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(Constents.QUEUE_NAME1, true);
    }

    @Bean
    public Queue sendSmsQueue() {
        return new Queue(Constents.QUEUE_NAME2, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(Constents.EXCHANGE_TOPIC);
    }


    //绑定 将队列和交换器绑定，并设置路由键：
    @Bean
    public Binding bindingDirect1() {
        //  "#" 表示匹配多个
        return BindingBuilder.bind(sendMailQueue()).to(topicExchange()).with("#");
    }
    //绑定 将队列和交换器绑定，并设置路由键：
    @Bean
    public Binding bindingDirect2() {
        // "*"表示一个
        return BindingBuilder.bind(sendSmsQueue()).to(topicExchange()).with("*");
    }
}
