package com.offcn.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayTest {
    @Test
    public  void testMain(){
        /** 支付宝网关 **/
        String URL = "https://openapi.alipaydev.com/gateway.do";

        /** 应用id，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/190/201602493024 **/
        String APP_ID = "2021000119628156";

        /** 应用私钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602469554 **/
        String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCpA4k/9HgTYk9QGgup9sQFTpWbKudrl4GqAT26a92K4jzO6gLAGORVpV/FM1XWFw0GLzSLYO2d+E6TBZHluIr1A55BUkB8VqoNOaRjd/5YfK3kXz4xbErTHXidl1B/8ef2kImFJsHQoL9tSqICM0zGiOBsRmjYGxK/KAaT7Nt42D5VmFAbkFSibGtTCzrhw1d2IrG4HzXkqrp0Fg0sgESn2a33hSFIiFkoVbKH2Tbx9qxb58VHFIoZjAljvIXppGIar8BQnX2mRPYGXOv+a67BA7JbXfrvqU0hZMfQOqqvT6TZjsJghHGO3O+Uelc4OIJTPWiiYoOlHmYM2lxd7wP7AgMBAAECggEAbTbQwNgLo5L6Z2t9MAY6YqeGOy99PWQ4sI9+wPEkxrMs0hQbVOCA4+sqOLbUd7SLgPyN+qbWxSaIN674uTYH7Q26fMFW1EH4PvWJiZEndSyT/aeh+7PbohJAN61oolTCLOz3QGhsA5FsQYZhrARUT1JxmuXgqAzOj2Gg5oO+ypX4xazpD004K2jqW++KnHREXGuOVaCThGl5xT0Z6Bhp+FQsIRC605ewP4fTl/5eK1JIh05bSUDTR7CwaQ30conabVIJuNl/wukFdKz89yAYpgQYm7YRouyfj4m/LwosTDoUD/2GqAoTcXzJ8SVu/oSpcqzFze2AFIANwbaDr6ccgQKBgQD3c/63QUew4Ucyhu1L2IJbzFroszIG/4rkRcVAElvw2heczK24NADCk7tTUyeH4vCMPinsugJpsT7zlHHIozTVljvGoQ9Ceb3maJTi8CtDMgfHIsXDNyFgUQL5BMmTl2zYW7TR9RZ7iEMigws7Od6d+sN7zlG0MnLYl4rZDd1YQQKBgQCu2fkdp3YxJkDwitHB3i2IBiw8kYWVPjvdwc1vrm+iupnACS8HBzo+SSkvNzJq1KBdXd4ifvYJy8WamI1dSJLoBpGpf9plAL6ycL/b0GxFoaib23qxFiLl4R/3ni/KPZ9lLQzaMd1TRczIM8eMBRl1XQCtcTdDOM53i+e611htOwKBgQClFpsKEaBNro48dG4eGs0lh1njQ6INrzEviCjHzTfPoQD92EPRz8fuCHeCaMjHzKzlM5fu2k4/y0OPaCrdS501Hey1YMB4fhYmgQFLwiVqyBcOUPGVgtNemc2c6we80tqIIe6p4JRmSAtaHAlDMyGFTkZa89Pon3ularz0gF56wQKBgGOAmZp9hO4dVdFNPmbz97czxBLHwVwX6VueATmv68mQJRS6ZS+7tjJh0fUTlTZkP8pCM7ovw/X5Jz991tyiAy/VJSlvH6pgzSSgsx31MthBUcE4Gk8SSNPG1DlGpO2TOdUK2QiGXewYiZvYk5MkB8PZJByPlCJF/oDbdgjGqYSnAoGBAK8uDDYBNPQnMYqw6tWIbLRAxFfeyCbschGws2r3G/j1Khwf597bgvg7l/ADYkMGCaYofv48e2DvBqzwbWs1PTz48jFQfyFBPbkFwyVZXzhxe2jsSNDLUuQ7xLbDRsdy77RXdDklrfGsS45DRlHAb/yVdPkNsBWNMJ/YZ0VyMoAF";

        /** 支付宝公钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602487431 **/
        String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkjkDBEN/eRvJI2dQiMIY9GYjA5SOjy60ozYWIdCdqRluhAirLXTk+XcIA3wrVxqYeA26n8Zz/54IJ/l82IJxdBjX1IaoHjAFJtZVrOpWf9tPumD+wNO46QrYeWDhj18PTgyjD2gnkhcl+JyK8+RlsG0lk36DjXCOYjwaiYeGjhZmm85t5QgUYJx5hDkkFN0J+6rM2FmmX6HhHFFs7hOWeQ+4MGRNn2ZS4HONn87H0Jt2LAbM6/Tj8mPTlnS5s8/InSZs6tCnpYQTd/ov9tUJsAMkBzkUxlCMVIpL6mIcwUImNPAu2HmpmOzNc/Mhsif2AYuD8pxBxt+6x8zVzsYrAQIDAQAB";
        /** 初始化 **/
        AlipayClient alipayClient = new DefaultAlipayClient(URL,APP_ID,APP_PRIVATE_KEY,"json","UTF-8",ALIPAY_PUBLIC_KEY,"RSA2");

        /** 实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.trade.precreate（统一收单线下交易预创建（扫码支付）） **/
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        /** 设置业务参数  **/
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();

        /** 商户订单号，商户自定义，需保证在商户端不重复，如：20200612000001 **/
        model.setOutTradeNo("20200612000001");

        /** 销售产品码，固定值：FACE_TO_FACE_PAYMENT **/
        model.setProductCode("FACE_TO_FACE_PAYMENT");

        /**订单标题 **/
        model.setSubject("hahhah");

        /** 订单金额，精确到小数点后两位 **/
        model.setTotalAmount("0.01");

        /** 订单描述 **/
        model.setBody("woshiorder");

        /** 业务扩展参数 **/
        //ExtendParams extendParams = new ExtendParams();

        /** 系统商编号，填写服务商的PID用于获取返佣，返佣参数传值前提：传值账号需要签约返佣协议，用于isv商户。 **/
        //extendParams.setSysServiceProviderId("2088511****07846");

        /** 花呗分期参数传值前提：必须有该接口花呗收款准入条件，且需签约花呗分期 **/
        /** 指定可选期数，只支持3/6/12期，还款期数越长手续费越高 **/
        // extendParams.setHbFqNum("3");

        /** 指定花呗分期手续费承担方式，手续费可以由用户全承担（该值为0），也可以商户全承担（该值为100），但不可以共同承担，即不可取0和100外的其他值。 **/
        //extendParams.setHbFqSellerPercent("0");

        //model.setExtendParams(extendParams);

        /** 将业务参数传至request中 **/
        request.setBizModel(model);

        /** 异步通知地址，以http或者https开头的，商户外网可以post访问的异步地址，用于接收支付宝返回的支付结果，如果未收到该通知可参考该文档进行确认：https://opensupport.alipay.com/support/helpcenter/193/201602475759 **/
        request.setNotifyUrl("");

        /**第三方调用（服务商模式），传值app_auth_token后，会收款至授权app_auth_token对应商家账号，如何获传值app_auth_token请参考文档：https://opensupport.alipay.com/support/helpcenter/79/201602494631 **/
        //request.putOtherTextParam("app_auth_token", "传入获取到的app_auth_token值");

        AlipayTradePrecreateResponse response = null;
        try {

            /** 通过alipayClient调用API，获得对应的response类  **/
            response = alipayClient.execute(request);

        } catch (AlipayApiException e) {

            e.printStackTrace();
        }

        /** 获取接口调用结果，如果调用失败，可根据返回错误信息到该文档寻找排查方案：https://opensupport.alipay.com/support/helpcenter/101 **/
        System.out.println(response.getBody());
    }
}
