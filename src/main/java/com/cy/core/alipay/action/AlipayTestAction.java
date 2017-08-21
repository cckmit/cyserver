package com.cy.core.alipay.action;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.cy.base.action.AdminBaseAction;
import com.cy.core.alipay.service.AlipayService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

/**
 * <p>Title: AlipayAction</p>
 * <p>Description: </p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-10-10 11:28
 */
@Namespace("/alipay")
@Action(value = "alipayTestAction", results = {
        @Result(name="alipay",location="/alipay/alipay.jsp"),
        @Result(name="index",location="/alipay/index.jsp")
    }
)
public class AlipayTestAction extends AdminBaseAction {

    @Autowired
    public AlipayService alipayService ;

    public String sHtmlText ;
    public String msg ;

    public String getSHtmlText() {
        return sHtmlText;
    }

    public void setSHtmlText(String sHtmlText) {
        this.sHtmlText = sHtmlText;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String doNotNeedSessionAndSecurity_index() {
        return "index" ;
    }


    public void doNotNeedSessionAndSecurity_alipay() throws Exception {
        String app_id = "2016091000478555" ;
        String charset = "utf-8" ;
        String alipay_public_key =
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfFhSq1XvoEEgZY8twdsTgo2FZ\n" +
                "Fbs+/m7oYCgWnXVgFDxBxx3AM5dL5EiNoskZsFW7A/Lz7Jjk0jniwKFACl6RPEIs\n" +
                "9q8+pa7S0x2PB3VAKa2efAtZsxOzcPvMhqsk13BKY03M0u0my2mgOWJccm5HAH56\n" +
                "kUh496ebEeatGKYOsQIDAQAB" ;
        String app_private_key =
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN8WFKrVe+gQSBlj\n" +
                "y3B2xOCjYVkVuz7+buhgKBaddWAUPEHHHcAzl0vkSI2iyRmwVbsD8vPsmOTSOeLA\n" +
                "oUAKXpE8Qiz2rz6lrtLTHY8HdUAprZ58C1mzE7Nw+8yGqyTXcEpjTczS7SbLaaA5\n" +
                "YlxybkcAfnqRSHj3p5sR5q0Ypg6xAgMBAAECgYEAmq00pz5eKwke9Hu9Er1GxHqD\n" +
                "hMEMkyDWxUfmg8epGnTtUq48codS38vogkvcI3Of/Ys/aOEjIYShnhbxtvV5mu4Z\n" +
                "+lgxE11xY++wKFWqKTCZPj1CIwe73rcAD0vaQGW2YNjSGQxitQ7zxMuF4evMzFy2\n" +
                "0S91jr6xw62nNcEgbgECQQD2/CuPUsU7w8Cv0PN+9K250XP1P1Jikm0wpXlkEz1I\n" +
                "ib5H6civsApUxlEKz+4pBrFC7UyIh8M8hsneuKsqJb9DAkEA5zqZo7D2Fj2h0PpD\n" +
                "hd/EZ42o9SCUPTiVpjQJJwvv2fMhS2tOmj+ggqrFFxNBqFSE67OJCoSBg/WJRVOq\n" +
                "zwHY+wJAMaXjQ75Js4fYFf+U0vJwcafu/V+rOfFhTaQV0M4lRY2a2G3gT6C9kukC\n" +
                "pX/CyjB0NZXqCo/v6RzXO5Q3pBNObQJATA7NfLd3qscpE+lODpoVK47ANak6uYyE\n" +
                "RQA2xn45rfI4UGuClmA5duGfJMDzxt/OPQ14FVqSk4pPVdt4gtDzwwJBAMpvUJ4g\n" +
                "79edRhs3Vmew5CHuLd78UgV9jQsIU8OFHm2uL7WWsHN04HR1QckbGdM2lCyWH2ej\n" +
                "0VLCCmxwVlF9Eqs=" ;
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",app_id,app_private_key,"json",charset,alipay_public_key);

        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址

        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101002\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"seller_id\":\"2088102174974449\"," +
                "    \"product_code\":\"QUICK_WAP_PAY\"" +
                "  }");//填充业务参数

        String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        this.getResponse().setContentType("text/html;charset=utf-8");
        System.out.println("-----> form : " + form);
        this.getResponse().getWriter().write(form);//直接将完整的表单html输出到页面
        this.getResponse().getWriter().flush();
    }

    /**
     * 支付宝服务器异步通知页面
     * @return
     */
    public String doNotNeedSessionAndSecurity_alipayApi() throws UnsupportedEncodingException {
        ////////////////////////////////////请求参数//////////////////////////////////////
        return "alipay";
    }


}
