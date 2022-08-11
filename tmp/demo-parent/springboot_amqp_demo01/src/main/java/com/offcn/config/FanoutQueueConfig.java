package com.offcn.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//广播形式，所有消息到达广播交换机，广播交换机向每个队列分发一份消息
@Configuration
public class FanoutQueueConfig {
    /**
     * 声明队列名
     */
    private final String fanout1 = "fanout_queue_1";

    private final String fanout2 = "fanout_queue_2";

    @Bean
    public Queue fanOutQueue1(){
        return new Queue(fanout1);
    }
    @Bean
    public Queue fanOutQueue2(){
        return new Queue(fanout2);
    }

    /**
     * 声明交换机的名字.
     */
    private final String fanoutExchange = "fanoutExchange";

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(fanoutExchange);
    }
    /**
     * 队列绑定交换机，也可在可视化工具中进行绑定.
     *
     * @return
     */
    //自动完成注入
    @Bean
    public Binding bindingFanoutQueue1(Queue fanOutQueue1,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanOutQueue1).to(fanoutExchange);
    }
    @Bean
    public Binding bindingFanoutQueue2(Queue fanOutQueue2,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanOutQueue2).to(fanoutExchange);
    }
}
