package com.cy.core.alipay.action;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.cy.base.action.AdminBaseAction;
import com.cy.common.utils.MapUtils;
import com.cy.common.utils.alipay.config.AlipayConfig;
import com.cy.common.utils.alipay.entity.AlipayResult;
import com.cy.common.utils.alipay.util.AlipayNotify;
import com.cy.common.utils.alipay.util.AlipaySubmit;
import com.cy.core.alipay.service.AlipayService;
import com.cy.core.donation.entity.Donation;
import com.cy.core.donation.service.DonationService;
import com.cy.system.ConfigProperties;
import com.cy.system.Global;
import com.cy.util.PairUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>Title: AlipayAction</p>
 * <p>Description: </p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-10-10 11:28
 */
@Namespace("/alipay")
@Action(value = "alipayAction", results = {
        @Result(name="alipay",location="/alipay/alipay.jsp"),
        @Result(name="fail",location="/alipay/alipay_fail.html"),
        @Result(name="index",location="/alipay/index.jsp"),
        @Result(name="alipay_success",location="/mobile/services/donate/list_my_head.html", type = "redirect", params = {
                "accountNum", "${accountNum}" }),
        @Result(name="alipay_fail",location="/mobile/services/donate/false.html"),
        @Result(name="certificate",location="/alipay/success.jsp", type = "redirect",params = {"certificateImageUrl", "${certificateImageUrl}" })
    }
)
public class AlipayAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(AlipayAction.class);

    @Autowired
    private AlipayService alipayService ;

    @Autowired
    private DonationService donationService ;

    private String accountNum ;
    private String url = null ;

    private String msg ;

    private String out_trade_no ;
    private String subject ;
    private String total_fee ;
    private String wIDbody ;

    private String payType;
    private String payMethod;   //支付途径（10:手机APP；20:网站；30:微信;40:线下）
    private String certificateImageUrl; //捐赠证书地址

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getWIDbody() {
        return wIDbody;
    }

    public void setWIDbody(String wIDbody) {
        this.wIDbody = wIDbody;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }


    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }


    public String getCertificateImageUrl() {
        return certificateImageUrl;
    }

    public void setCertificateImageUrl(String certificateImageUrl) {
        this.certificateImageUrl = certificateImageUrl;
    }

    public String doNotNeedSessionAndSecurity_index() {
        accountNum = "446" ;
        return "alipay_fail" ;
    }

    public void doNotNeedSessionAndSecurity_index_web() {
        try {
            accountNum = "508";
            String  url  = "/cy/mobile/services/donate/list_my_head.html?accountNum="+accountNum;
            //this.getRequest().getRequestDispatcher(url).forward(this.getRequest(),this.getResponse());
           this.getResponse().sendRedirect(url);
            //this.getRequest().sendRedirect("http://www.baidu.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String doNotNeedSessionAndSecurity_alipay() throws Exception {

        return null ;
    }

    /**
     * 支付宝服务器支付页面
     * @return
     */
    public void doNotNeedSessionAndSecurity_alipayApi() throws Exception {

        try {
            //通过订单号获取订单详情
            Donation donation = donationService.selectByOrderNo(out_trade_no);
            //判断订单是否存在以及是否已支付
            if (donation !=null && 0 == donation.getPayStatus()) {

                //支付类型
                String payment_type = "1";

                //卖家支付宝帐户
                String seller_email = AlipayConfig.seller_email;
//        seller_email = new String(request.getParameter("WIDseller_email").getBytes("ISO-8859-1"),"UTF-8");
                //必填

                //商户订单号
                String out_trade_no = getOut_trade_no();
//        out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
                //商户网站订单系统中唯一订单号，必填

                //订单名称
                String subject = getSubject();
                subject = StringUtils.isNotBlank(subject) ? URLDecoder.decode(subject) : "捐赠";
//        subject = new String(subject.getBytes("ISO-8859-1"),"UTF-8");
                //必填

                //付款金额
//        String total_fee = "0.01" ;
//        String total_fee = request.getParameter("WIDtotal_fee") ;
//        total_fee = new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"),"UTF-8");
                //必填

                //订单描述
//        String body = getWIDbody() ;
                String body = this.getRequest().getParameter("WIDbody");
//        body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
                //商品展示地址
                String show_url = this.getRequest().getParameter("WIDshow_url");
//        String show_url = "http://localhost:8080/cy/project/projectAction!doNotNeedSessionAndSecurity_getById.action?id=2&accountNum=448" ;
//        show_url = new String(request.getParameter("WIDshow_url").getBytes("ISO-8859-1"),"UTF-8");
                //需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html

                //防钓鱼时间戳
                String anti_phishing_key = "";
                //若要使用请调用类文件submit中的query_timestamp函数

                //客户端的IP地址
                String exter_invoke_ip = "";
                //非局域网的外网IP地址，如：221.0.0.1


                //必填，不能修改
//        String notify_url = getRequest().getParameter("notify_url") ;
//        notify_url = "http://114.215.196.18/alipay/paynotify.jsp";
                //String notify_url = AlipayConfig.notify_url;
                //需http://格式的完整路径，不能加?id=123这类自定义参数

                //页面跳转同步通知页面路径
                //String return_url = AlipayConfig.return_url;
                //需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
                String notify_url = "";             //服务器异步通知页面路径
                String return_url = "";            //页面跳转同步通知页面路径
                if (StringUtils.isNotBlank(donation.getPayMethod()) && "10".equals(donation.getPayMethod().trim())) {
                    notify_url = Global.cy_server_url + "alipay/alipayAction!doNotNeedSessionAndSecurity_wap_direct_pay_notify.action";
                    return_url = Global.cy_server_url + "alipay/alipayAction!doNotNeedSessionAndSecurity_wap_direct_pay_result.action";
                } else {
                    notify_url = Global.cy_server_url + "alipay/alipayAction!doNotNeedSessionAndSecurity_direct_pay_notify.action";
                    return_url = Global.cy_server_url + "alipay/alipayAction!doNotNeedSessionAndSecurity_direct_pay_result.action";
                }

//            //需http://格式的完整路径，不能加?id=123这类自定义参数
//
//            //页面跳转同步通知页面路径
//            String return_url = AlipayConfig.return_url;
//            //需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
                String form = null;
                //把请求参数打包成数组
                Map<String, String> sParaTemp = new HashMap<String, String>();
                //////////////////////////////////////////////////////////////////////////////////
                AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.serverUrl, AlipayConfig.app_id,AlipayConfig.app_private_key,
                        "json", AlipayConfig.input_charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);

                AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
                //在公共参数中设置回跳和通知地址
                alipayRequest.setReturnUrl(return_url);
                alipayRequest.setNotifyUrl(notify_url);

                sParaTemp.put("out_trade_no", out_trade_no);
                sParaTemp.put("total_amount", total_fee);
                sParaTemp.put("subject", subject);

                sParaTemp.put("seller_id", AlipayConfig.partner);
                sParaTemp.put("product_code", "QUICK_WAP_PAY");
                JSONObject json = JSONObject.fromObject(sParaTemp);
                System.out.println("BizContent : " + json);
                alipayRequest.setBizContent(json.toString());
//                  alipayRequest.setBizContent("{" + "\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":" + total_fee + "," + "\"subject\":\"" + subject + "\"," + "\"timeoutExpress\":\"" + "" + "\"," + "\"body\":\"" + "" + "\"," + "\"product_code\":\"QUICK_WAP_PAY\"," + "\"quit_url\":\"https://www.baidu.com\"" + "}");

                form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单

                this.getResponse().setContentType("text/html;charset=utf-8");
                System.out.println("form : " + form);
                this.getResponse().getWriter().write(form);//直接将完整的表单html输出到页面
                this.getResponse().getWriter().flush();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付宝服务器同步通知页面(手机网站支付)
     * @return
     */
    public void doNotNeedSessionAndSecurity_wap_direct_pay_result() throws Exception {
        //通过订单号获取订单详情
        Donation donation = donationService.selectByOrderNo(out_trade_no);
        String url = null;
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = getRequest().getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }

            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.input_charset);//调用SDK验证签名
            if (signVerified) {
                // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
                AlipayResult alipayResult = (AlipayResult) MapUtils.convertMap(AlipayResult.class, params);

                PairUtil<Integer,String> pair = donationService.donationPayFeedBack(alipayResult);
                int code = pair.getOne() ;
                accountNum = pair.getTwo() ;
                String backUrl = "";
                if (code == 0 || code == 1) {
                    System.out.println("支付宝同步回调处理 success");    //请不要修改或删除
                    backUrl = Global.cy_server_url +"/mobile/services/foundation/alipay_success.html?accountNum="+accountNum + "&donationId=" + donation.getDonationId();

                } else {
                    System.out.println("-----> " + (code == 2 ? "订单不存在" : "其他错误"));
                    backUrl = Global.cy_server_url + "/mobile/services/foundation/alipay_false.html?accountNum="+accountNum + "&donationId=" + donation.getDonationId();
                }

                url = backUrl;
            } else {
                // TODO 验签失败则记录异常日志，并在response中返回failure.//验证失败
                System.out.println("支付宝同步回调处理 fail");
                url = Global.cy_server_url + "/mobile/services/foundation/alipay_false.html?accountNum="+accountNum + "&donationId=" + donation.getDonationId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            url = Global.cy_server_url + "/mobile/services/foundation/alipay_false.html?accountNum="+accountNum + "&donationId=" + donation.getDonationId();
        }
        this.getResponse().sendRedirect(url);

    }

    /**
     * 支付宝服务器异步通知页面(手机网站支付)
     * @return
     */
    public void doNotNeedSessionAndSecurity_wap_direct_pay_notify() throws Exception {
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = getRequest().getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }


            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.input_charset);//调用SDK验证签名
            if (signVerified) {
                // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
                AlipayResult alipayResult = (AlipayResult) MapUtils.convertMap(AlipayResult.class, params);

                PairUtil<Integer,String> pair = donationService.donationPayFeedBack(alipayResult);
                int code = pair.getOne() ;
                if (code == 0 || code == 1) {
                    System.out.println("支付宝异步回调处理 success");    //请不要修改或删除

                } else {
                    System.out.println("-----> " + (code == 2 ? "订单不存在" : "其他错误"));
                }

            } else {
                // TODO 验签失败则记录异常日志，并在response中返回failure.//验证失败
                System.out.println("支付宝异步回调处理 fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 支付宝服务器同步通知页面(即时到账)
     * @return
     */
    public String doNotNeedSessionAndSecurity_direct_pay_result() throws Exception {
        String url = null ;
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = getRequest().getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号

//            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//
//            //支付宝交易号
//
//            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
//
//            //交易状态
//            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //计算得出通知验证结果
            boolean verify_result = AlipayNotify.verify(params);
            if (verify_result) {
                // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
                AlipayResult alipayResult = (AlipayResult) MapUtils.convertMap(AlipayResult.class, params);

                PairUtil<Integer,String> pair = donationService.donationPayFeedBack(alipayResult);
                int code = pair.getOne() ;
                accountNum = pair.getTwo() ;
                String backUrl = Global.school_web_url + "service/donate/alipay_success.html";

                if (code == 0 || code == 1) {
                    System.out.println("支付宝同步回调处理 success");    //请不要修改或删除
//                    //更新支付类型
//                    donationService.updatePayType(alipayResult.getOut_trade_no(),"20");
                    logger.info("-------> 支付宝同步回调处理: " + certificateImageUrl);
                    return "certificate";
                } else {
                    System.out.println("-----> " + (code == 2 ? "订单不存在" : "其他错误"));
                    backUrl = Global.school_web_url + "service/donate/alipay_fail.html";
                }

                url = backUrl;
            } else {
                // TODO 验签失败则记录异常日志，并在response中返回failure.//验证失败
                System.out.println("支付宝同步回调处理 fail");
                url = Global.school_web_url + "service/donate/alipay_fail.html" ;
//                return Global.school_web_url + "/alumniweb/web/service/donate/alipay_fail.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            url  = Global.school_web_url + "service/donate/alipay_fail.html";
        }
//        this.getResponse().sendRedirect(url);
        return "fail";
    }

    /**
     * 支付宝服务器异步通知页面(即时到账)
     * @return
     */
    public void doNotNeedSessionAndSecurity_direct_pay_notify() throws Exception {
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = getRequest().getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }

            boolean verify_result = AlipayNotify.verify(params);

            if (verify_result) {
                // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
                AlipayResult alipayResult = (AlipayResult) MapUtils.convertMap(AlipayResult.class, params);

                PairUtil<Integer,String> pair = donationService.donationPayFeedBack(alipayResult);
                int code = pair.getOne() ;
                if (code == 0 || code == 1) {
                    System.out.println("支付宝异步回调处理 success");    //请不要修改或删除
                    //更新支付类型
//                    donationService.updatePayType(alipayResult.getOut_trade_no(),"10");
                } else {
                    System.out.println("-----> " + (code == 2 ? "订单不存在" : "其他错误"));
                }

            } else {
                // TODO 验签失败则记录异常日志，并在response中返回failure.//验证失败
                System.out.println("支付宝异步回调处理 fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
