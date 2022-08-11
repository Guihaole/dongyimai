package com.offcn.sms.listener;

import com.offcn.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;
    @RabbitListener(queues = "dongyimai.sms.queue")
    public void getMessage(Map<String,String> map){
        if (map==null) {
            return;
        }
        String phone = map.get("phone");
        String code = map.get("code");
        smsUtil.sendSms(phone,code);
    }
}
