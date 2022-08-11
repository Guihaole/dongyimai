package com.offcn.seckill.com.offcn.seckill.consumer;

import com.alibaba.fastjson.JSON;
import com.offcn.seckill.service.SeckillOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener(queues = "${mq.pay.queue.seckillorder}")
public class SeckillOrderPayMessageListener {
    /**
     * 监听消费者消息
     */
//    @RabbitListener
//    public void consumerMessage(@Payload String message){
//        System.out.println(message);
//        //将消息转换成Map对象
//        Map<String,String> resultMap = JSON.parseObject(message, Map.class);
//        System.out.println("监听到的消息:"+resultMap);
//    }
    @Autowired
    private SeckillOrderService seckillOrderService;

    /**
     * 监听消费消息
     *
     * @param message
     */
    @RabbitHandler
    public void consumeMessage(@Payload String message) {
        System.out.println(message);
        //将消息转换成Map对象
        Map<String, String> resultMap = JSON.parseObject(message, Map.class);
        System.out.println("监听到的消息:" + resultMap);
        //获取交易状态
        String trade_status = resultMap.get("trade_status");
        //判断交易状态是否等于成功
        if (trade_status != null && trade_status.equalsIgnoreCase("TRADE_SUCCESS")) {
            String body = resultMap.get("body");
            Map<String, String> bodyMap = new HashMap<>();
            if (resultMap.get("body") != null) {
                String[] splits = body.split("&");
                for (String split : splits) {
                    String[] vs = split.split("=");
                    bodyMap.put(vs[0], vs[1]);
                }
            }
            seckillOrderService.updatePayStatus(resultMap.get("out_trade_no"),
                    resultMap.get("trade_no"),
                    bodyMap.get("username"));
        } else {
            //支付失败，删除订单
            seckillOrderService.closeOrder(resultMap.get("username"));
        }
    }
}
