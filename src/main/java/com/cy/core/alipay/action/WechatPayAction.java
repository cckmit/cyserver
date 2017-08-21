package com.cy.core.alipay.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.alipay.service.WechatPayService;
import com.cy.core.donation.entity.Donation;
import com.cy.core.donation.service.DonationService;
import com.cy.core.project.entity.Project;
import com.cy.core.project.service.ProjectService;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.service.WeiXinAccountService;
import com.cy.smscloud.http.FounHttpUtils;
import com.cy.system.Global;
import com.cy.util.DateUtils;
import com.cy.util.MoneyUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信支付
 *
 * @author niu
 * @create 2017-03-30 上午 11:00
 **/
@Namespace("/alipay")
@Action(value = "wechatPayAction", results = {
        @Result(name = "pay", location = "/mobile/services/donate/pay.jsp", type = "redirect", params = {
                "payDate", "${payDate}"}),
        @Result(name = "pcPay", location = "/alipay/wechatPay/pcPay.jsp", type = "redirect", params = {
                "code_url", "${code_url}", "out_trade_no", "${out_trade_no}", "total_fee", "${total_fee}", "body", "${body}"}),
        @Result(name = "pc_fail", location = "/alipay/wechatPay/pay_fail.jsp", type = "redirect"),
        @Result(name = "pay_fail", location = "/mobile/services/donate/alipay_fail.html", type = "redirect")

}
)
public class WechatPayAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(WechatPayAction.class);
    //商户相关资料
//   private  String appid = "wxb618afcd782df25c";
    /* private static String partner = "1455043202";
    private static String paternerKey = "wuhanchuangyoukejiyoukejigongsi1";*/
    private static String notify_url = Global.cy_server_url + "alipay/wechatPayAction!doNotNeedSessionAndSecurity_wap_direct_pay_result.action";
    private static String pay_notify_url = Global.cy_server_url + "alipay/wechatPayAction!doNotNeedSessionAndSecurity_direct_pay_result.action";
    @Autowired
    private WeiXinAccountService weiXinAccountService;

    @Autowired
    private DonationService donationService;
    @Autowired
    private WechatPayService wechatPayService;
    @Autowired
    private ProjectService projectService;


    private String appid;
    private String openId;
    private String body;
    private String out_trade_no;
    private String total_fee;
    private String payDate;
    private String callBackUrl;
    private WeiXinAccount weiXinAccount;
    private String code_url;
    private String payType;    // 入口类型:15:校友会网站;25:基金会网站
    private String payMethod;   //支付途经：10：手机；20 网站

    /**
     * 方法findWinXinAccount 的功能描述：根据appId 获取公众号
     *
     * @param
     * @return com.cy.core.weiXin.entity.WeiXinAccount
     * @createAuthor niu
     * @createDate 2017-03-31 11:01:38
     * @throw
     */
    public void findWinXinAccount() {

        Map<String, Object> map = Maps.newHashMap();
        List<WeiXinAccount> weiXinAccountList = null;
        //根据appid获取公众号
        if (StringUtils.isNotBlank(appid)) {
            map.put("appId", appid);
            weiXinAccountList = weiXinAccountService.getList(map);
            if (weiXinAccountList != null && !weiXinAccountList.isEmpty()) {
                weiXinAccount = weiXinAccountList.get(0);
            }
        }
        if (weiXinAccount == null) {
            //如果appid为空则获取基金会公众号
            map.put("accountType", "20");
            weiXinAccountList = weiXinAccountService.getList(map);
            if (weiXinAccountList != null && !weiXinAccountList.isEmpty()) {
                weiXinAccount = weiXinAccountList.get(0);
            }
        }
        if (weiXinAccount == null) {
            //如果基金会公众号不存在，则获取校友会
            map.put("accountType", "10");
            weiXinAccountList = weiXinAccountService.getList(map);
            if (weiXinAccountList != null && !weiXinAccountList.isEmpty()) {
                weiXinAccount = weiXinAccountList.get(0);
            }
        }
    }

    //微信支付
    public String doNotNeedSessionAndSecurity_index() {

        try {
            // openId，采用 网页授权获取 access_token API：SnsAccessTokenApi获取
            //判断是否为微信公众号支付
            if (StringUtils.isNotBlank(appid) && StringUtils.isNotBlank(openId)) {
                //获取公众号信息
                findWinXinAccount();

                if (weiXinAccount != null && StringUtils.isNotBlank(weiXinAccount.getPartner()) && StringUtils.isNotBlank(weiXinAccount.getPartnerKey())) {
                    // 统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("appid", appid);
                    params.put("mch_id", weiXinAccount.getPartner());
                    params.put("body", "JFinal2.0极速开发");
                    params.put("out_trade_no", "977773682116");
                    params.put("total_fee", "1");

                    String ip = IpKit.getRealIp(getRequest());
                    if (StrKit.isBlank(ip)) {
                        ip = "127.0.0.1";
                    }

                    params.put("spbill_create_ip", ip);
                    params.put("trade_type", PaymentApi.TradeType.JSAPI.name());
                    params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
                    params.put("notify_url", notify_url);
                    params.put("openid", openId);

                    String sign = PaymentKit.createSign(params, weiXinAccount.getPartnerKey());
                    params.put("sign", sign);
                    String xmlResult = PaymentApi.pushOrder(params);

                    System.out.println(xmlResult);
                    Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

                    String return_code = result.get("return_code");
                    String return_msg = result.get("return_msg");
                    if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {

                        return "pay_fail";
                    }
                    String result_code = result.get("result_code");
                    if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {

                        return "pay_fail";
                    }
                    // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
                    String prepay_id = result.get("prepay_id");

                    Map<String, String> packageParams = new HashMap<String, String>();
                    packageParams.put("appId", appid);
                    packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
                    packageParams.put("nonceStr", System.currentTimeMillis() + "");
                    packageParams.put("package", "prepay_id=" + prepay_id);
                    packageParams.put("signType", "MD5");
                    String packageSign = PaymentKit.createSign(packageParams, weiXinAccount.getPartnerKey());
                    packageParams.put("paySign", packageSign);
                    String jsonStr = JsonUtils.toJson(packageParams);
                    System.out.println(jsonStr);
                    payDate = jsonStr;

                    return "pay";
                }
            }
            return "pay_fail";
        } catch (Exception e) {
            logger.error(e);
            return "pay_fail";
        }
    }

    //微信支付统一下单
    public void doNotNeedSessionAndSecurity_getBrandWCPayDate() {
        // openId，采用 网页授权获取 access_token API：SnsAccessTokenApi获取
        Message message = new Message();
        try {
            //获取订单详情
            Donation donation = donationService.selectByOrderNo(out_trade_no);
            //判断订单是否存在
            if (donation != null) {
                //判断订单是否已支付
                if (0 == donation.getPayStatus()) {

                    //更新入口类型
                    if (StringUtils.isNotBlank(getOut_trade_no()) && StringUtils.isNotBlank(payType)) {
                        donationService.updatePayType(getOut_trade_no(), payType, null);
                    }

                    //获取公众号信息
                    findWinXinAccount();
                    if (weiXinAccount != null && StringUtils.isNotBlank(weiXinAccount.getPartner()) && StringUtils.isNotBlank(weiXinAccount.getPartnerKey())) {
                        // 统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("appid", appid);
                        params.put("mch_id", weiXinAccount.getPartner());
                        params.put("body", body);
                        params.put("out_trade_no", out_trade_no);
                        params.put("total_fee", MoneyUtil.yuanToFen(total_fee));

                        String ip = IpKit.getRealIp(getRequest());
                        if (StrKit.isBlank(ip)) {
                            ip = "127.0.0.1";
                        } else {
                            ip = ip.split(",")[0].trim();
                        }

                        params.put("spbill_create_ip", ip);
                        params.put("trade_type", PaymentApi.TradeType.JSAPI.name());
                        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
                        params.put("notify_url", notify_url);
                        params.put("openid", openId);
                        logger.info(params);

                        String sign = PaymentKit.createSign(params, weiXinAccount.getPartnerKey());
                        params.put("sign", sign);
                        logger.info("----------> 支付参数：" + PaymentKit.toXml(params));
                        String xmlResult = PaymentApi.pushOrder(params);

                        System.out.println(xmlResult);
                        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

                        String return_code = result.get("return_code");
                        String return_msg = result.get("return_msg");
                        String err_code_des = result.get("err_code_des");
                        System.out.println("----------------------------------------");
                        System.out.println("return_code:" + return_code + "\nreturn_msg:" + return_msg + "\nerr_code_des:" + err_code_des);
                        System.out.println("----------------------------------------");
                        if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {

                            message.init(false, err_code_des, null);
                            super.writeJson(message);

                        }
                        String result_code = result.get("result_code");
                        if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {

                            String err_code = result.get("err_code");
                            if ("ORDERPAID".equals(err_code)) {
                                Donation donationPayBack = donationService.wechatDonationPayBack(out_trade_no);
                                message.init(true, err_code_des, null, String.valueOf(donationPayBack.getDonationId()));
                                super.writeJson(message);
                            } else {
                                message.init(false, err_code_des, null);
                                super.writeJson(message);
                            }

                        }
                        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
                        String prepay_id = result.get("prepay_id");

                        Map<String, String> packageParams = new HashMap<String, String>();
                        packageParams.put("appId", appid);
                        packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
                        packageParams.put("nonceStr", System.currentTimeMillis() + "");
                        packageParams.put("package", "prepay_id=" + prepay_id);
                        packageParams.put("signType", "MD5");
                        String packageSign = PaymentKit.createSign(packageParams, weiXinAccount.getPartnerKey());
                        packageParams.put("paySign", packageSign);
                        String jsonStr = JsonUtils.toJson(packageParams);
                        System.out.println(jsonStr);
                        message.init(true, "下单成功", jsonStr);
                    }
                } else {
                    message.init(true, "该订单已支付", null, String.valueOf(donation.getDonationId()));
                }
            } else {
                message.init(false, "该订单不存在", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            message.init(false, "下单异常", null);
        }
        super.writeJson(message);
    }

    /**
     * 方法doNotNeedSessionAndSecurity_wap_direct_pay_result 的功能描述：支付成功通知
     *
     * @param
     * @return java.lang.String
     * @createAuthor niu
     * @createDate 2017-03-31 18:33:12
     * @throw
     */
    public String doNotNeedSessionAndSecurity_wap_direct_pay_result() {


        // 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
        String xmlMsg = HttpKit.readData(getRequest());
        logger.info("支付通知=" + xmlMsg);
        Map<String, String> xml = new HashMap<String, String>();
        try {

            Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);


            String result_code = params.get("result_code");
            // 总金额
            String totalFee = params.get("total_fee");
            // 商户订单号
            String orderNo = params.get("out_trade_no");
            // 微信支付订单号
            String transId = params.get("transaction_id");
            // 支付完成时间，格式为yyyyMMddHHmmss
            String timeEnd = params.get("time_end");
            appid = params.get("appid");
            // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
            // 避免已经成功、关闭、退款的订单被再次更新

            //获取公众号信息
            findWinXinAccount();

            if (PaymentKit.verifyNotify(params, weiXinAccount.getPartnerKey())) {
                if (("SUCCESS").equals(result_code)) {
                    //更新订单信息
                    System.out.println("更新订单信息");
                    Donation donation = donationService.wechatDonationPayBack(orderNo);
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    xml.put("return_code", "SUCCESS");
                    xml.put("return_msg", "OK");

                } else {
                    xml.put("return_code", "FAIL");
                    xml.put("return_msg", "报文为空");
                }
            } else {
                xml.put("return_code", "FAIL");
                xml.put("return_msg", "报文为空");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return PaymentKit.toXml(xml);
    }

    /**
     * 方法doNotNeedSessionAndSecurity_app_direct_pay_result 的功能描述：手机微信支付成功通知
     *
     * @param
     * @return java.lang.String
     * @createAuthor wurencheng
     * @createDate 2017-06-23 09:00:00
     * @throw
     */
    public String doNotNeedSessionAndSecurity_app_direct_pay_result() {
        // 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
        String xmlMsg = HttpKit.readData(getRequest());
        logger.info("支付通知=" + xmlMsg);
        Map<String, String> xml = new HashMap<String, String>();
        try {

            Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
            //商户key
            String partnerKey = "CpG0ddtMf0dlkeMae1Qg31SCjyBXXatY";
            String result_code = params.get("result_code");
            // 总金额
            String totalFee = params.get("total_fee");
            // 商户订单号
            String orderNo = params.get("out_trade_no");
            // 微信支付订单号
            String transId = params.get("transaction_id");
            // 支付完成时间，格式为yyyyMMddHHmmss
            String timeEnd = params.get("time_end");
            String appid = params.get("appid");
            // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
            // 避免已经成功、关闭、退款的订单被再次更新


            if (PaymentKit.verifyNotify(params, partnerKey)) {
                if (("SUCCESS").equals(result_code)) {
                    //更新订单信息
                    System.out.println("更新订单信息");
                    wechatPayService.wechatAppDonationPayBack(orderNo);
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    xml.put("return_code", "SUCCESS");
                    xml.put("return_msg", "OK");

                } else {
                    xml.put("return_code", "FAIL");
                    xml.put("return_msg", "报文为空");
                }
            } else {
                xml.put("return_code", "FAIL");
                xml.put("return_msg", "报文为空");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return PaymentKit.toXml(xml);
    }

    /**
     * 方法doNotNeedSessionAndSecurity_pcModelTwo 的功能描述：PC支付模式二，PC支付不需要openid
     *
     * @param
     * @return java.lang.String
     * @createAuthor niu
     * @createDate 2017-04-13 09:44:44
     * @throw
     */

    public String doNotNeedSessionAndSecurity_pcModelTwo() {

        //获取订单详情
        Donation donation = donationService.selectByOrderNo(out_trade_no);
        //判断订单是否存在
        if (donation != null) {
            //判断订单是否已支付
            if (0 == donation.getPayStatus()) {

                findWinXinAccount();
                // 统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
                Map<String, String> params = new HashMap<String, String>();
                if (weiXinAccount != null && StringUtils.isNotBlank(weiXinAccount.getPartner()) && StringUtils.isNotBlank(weiXinAccount.getPartnerKey())) {

                    //更新入口类型
                    if (StringUtils.isNotBlank(getOut_trade_no()) && StringUtils.isNotBlank(payType)) {
                        donationService.updatePayType(getOut_trade_no(), payType, payMethod);
                    }

                    params.put("appid", weiXinAccount.getAccountAppId());
                    params.put("mch_id", weiXinAccount.getPartner());
                    params.put("body", body);

                    // 商品ID trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
                    params.put("product_id", "1");
                    // 商户订单号 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
                    params.put("out_trade_no", out_trade_no);
                    params.put("total_fee", MoneyUtil.yuanToFen(total_fee));

                    String ip = IpKit.getRealIp(getRequest());
                    if (StrKit.isBlank(ip)) {
                        ip = "127.0.0.1";
                    }

                    params.put("spbill_create_ip", ip);
                    params.put("trade_type", PaymentApi.TradeType.NATIVE.name());
                    params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
                    params.put("notify_url", notify_url);

                    String sign = PaymentKit.createSign(params, weiXinAccount.getPartnerKey());
                    params.put("sign", sign);
                    String xmlResult = PaymentApi.pushOrder(params);

                    System.out.println(xmlResult);
                    Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

                    String return_code = result.get("return_code");
                    String return_msg = result.get("return_msg");
                    if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
                        return "pc_fail";
                    }
                    String result_code = result.get("result_code");
                    String result_msg = result.get("result_msg");

                    if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
                        String err_code = result.get("err_code");
                        if ("ORDERPAID".equals(err_code)) {
                            Donation donationPayBack = donationService.wechatDonationPayBack(out_trade_no);
                            return "pcPay";
                        } else {
                            return "pc_fail";
                        }
                    }
                    // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
                    String prepay_id = result.get("prepay_id");
                    // trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付
                    code_url = result.get("code_url");
                    return "pcPay";
                } else {
                    return "pc_fail";
                }

            }else if (1 == donation.getPayStatus()){
                return "pcPay";
            }
        }
        return "pc_fail";
    }

    public void doNotNeedSessionAndSecurity_pcModelTwo1() {
        Message message = new Message();
        // 统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", "wxb618afcd782df25c");
        params.put("mch_id", "1455043202");
        params.put("body", "JFinal2.2极速开发");

        // 商品ID trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
        params.put("product_id", "1");
        // 商户订单号 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
        params.put("out_trade_no", "97777368226");
        params.put("total_fee", "1");

        String ip = IpKit.getRealIp(getRequest());
        if (StrKit.isBlank(ip)) {
            ip = "127.0.0.1";
        }

        params.put("spbill_create_ip", ip);
        params.put("trade_type", PaymentApi.TradeType.NATIVE.name());
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        params.put("notify_url", notify_url);

        String sign = PaymentKit.createSign(params, "wuhanchuangyoukejiyoukejigongsi1");
        params.put("sign", sign);
        String xmlResult = PaymentApi.pushOrder(params);

        System.out.println(xmlResult);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
            message.init(false, return_msg, null);
            super.writeJson(message);
        }
        String result_code = result.get("result_code");
        String result_msg = result.get("result_msg");

        if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
            message.init(false, result_msg, null);
            super.writeJson(message);
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String prepay_id = result.get("prepay_id");
        // trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付
        code_url = result.get("code_url");

        message.init(false, "下单成功", code_url);
        super.writeJson(message);
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
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
}
