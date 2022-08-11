package com.offcn.pay.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.order.fegin.OrderFeign;
import com.offcn.order.fegin.PayLogFeign;
import com.offcn.order.pojo.PayLog;
import com.offcn.pay.service.AliPayService;
import com.offcn.utils.IdWorker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {


    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private OrderFeign orderFeign;
    @Autowired
    private PayLogFeign payLogFeign;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 支付回调接口
     * @return
     */
    @RequestMapping("/notify/url")
    public String notifyResult(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        Enumeration<String> attributeNames = request.getParameterNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            map.put(attributeName,request.getParameter(attributeName));
        }
        String jsonString = JSON.toJSONString(map);
        System.out.println("jsonStr:"+jsonString);
        String body = map.get("body");
        String[] splits = body.split("&");
        HashMap<String, String> bodyMap = new HashMap<>();
        for (String split : splits) {
            String[] vs = split.split("=");
            bodyMap.put(vs[0],vs[1]);
        }
        rabbitTemplate.convertAndSend(bodyMap.get("exchange"),bodyMap.get("routingkey"),jsonString);
        return "支付成功";
    }
    @RequestMapping("/create/native")
    public Result<Map> createNative(@RequestParam Map<String, String> parameters) {
        //获取用户名

        Map<String, String> resultMap = aliPayService.createNative(parameters);

        return new Result<Map>(true, StatusCode.OK, "二维码连接地址创建成功", resultMap);
    }

    /**
     * 生成二维码
     *url: http://localhost:8001/api/pay/createNative
     * @return
     */
    @GetMapping("/createNative")
    public Map createNative() {
        Result<PayLog> payLogResult = orderFeign.searchPayLogFromRedis();
        PayLog payLog = payLogResult.getData();
        System.out.println("payLog===="+payLog);
        if (payLog != null) {
            return aliPayService.createNative(payLog.getOutTradeNo(), payLog.getTotalFee() + "");
        } else {
            return new HashMap();
        }
    }
    /**
     * 查询支付状态
     * @param out_trade_no
     * @return
     * http://localhost:8001/api/pay/queryPayStatus?out_trade_no=#
     */
    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no){
        Result result=null;
        int x=0;
        while(true){
            //调用查询接口
            Map<String, String> map = null;
            try {
                map = aliPayService.queryPayStatus(out_trade_no);
            } catch (Exception e1) {
                /*e1.printStackTrace();*/
                System.out.println("调用查询服务出错");
            }
            if(map==null){//出错
                result=new  Result(false, StatusCode.ERROR,"支付出错");
                break;
            }
            if(map.get("tradestatus")!=null&&map.get("tradestatus").equals("TRADE_SUCCESS")){//如果成功
                result=new  Result(true,StatusCode.OK,"支付成功");
                payLogFeign.updateOrderStatus(map.get("out_trade_no"), map.get("trade_no"));
                break;
            }
            if(map.get("tradestatus")!=null&&map.get("tradestatus").equals("TRADE_CLOSED")){//如果成功
                result=new  Result(true, StatusCode.OK,"未付款交易超时关闭，或支付完成后全额退款");
                break;
            }
            if(map.get("tradestatus")!=null&&map.get("tradestatus").equals("TRADE_FINISHED")){//如果成功
                result=new  Result(true,StatusCode.OK, "交易结束，不可退款");
                break;
            }
            try {
                Thread.sleep(3000);//间隔三秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
            if(x>=100){
                result=new  Result(false, StatusCode.ERROR, "二维码超时");
                break;
            }
        }
        return result;
    }
}
