package com.cy.core.alipay.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.alipay.action.WechatPayAction;
import com.cy.core.donation.dao.DonationMapper;
import com.cy.core.donation.entity.Donation;
import com.cy.core.donation.service.DonationService;
import com.cy.core.donation.utils.Certificate;
import com.cy.core.project.dao.ProjectMapper;
import com.cy.core.project.entity.Project;
import com.cy.smscloud.http.FounHttpUtils;
import com.cy.system.Global;
import com.cy.util.MoneyUtil;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wurencheng
 * @Company chuangyou
 * @data 2017-06-22 11:00
 */
@Service("wechatPayService")
public class WechatpayServiceImpl extends AdminBaseAction implements WechatPayService {
    @Autowired
    private DonationService donationService;
    @Autowired
    private DonationMapper donationMapper;
    @Autowired
    private ProjectMapper projectMapper;
    private static String notify_url = Global.cy_server_url + "alipay/wechatPayAction!doNotNeedSessionAndSecurity_app_direct_pay_result.action";
    private static final Logger logger = Logger.getLogger(WechatPayAction.class);

    //手机APP微信支付统一下单
    public void getAppPayDate(Message message ,String content) {

        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String, String> map = JSON.parseObject(content, Map.class);

        //捐赠ID
        String donationId = map.get("donationId");
        //商户号
        String partner = "1483637472";
        //商户Key
        String partnerKey = "CpG0ddtMf0dlkeMae1Qg31SCjyBXXatY";
        //标价金额
        String total_fee =  map.get("total_fee");
        //公众账号ID
        String appid = map.get("appid");
        //商品描述
        String body =  map.get("body");

        if(StringUtils.isBlank(donationId)){
            message.setMsg("捐赠ID不能为空");
            message.setSuccess(false);
            return;
        }

//        if(StringUtils.isBlank(partner)){
//            message.setMsg("商户号不能为空");
//            message.setSuccess(false);
//            return;
//        }
//
//        if(StringUtils.isBlank(partnerKey)){
//            message.setMsg("商户Key不能为空");
//            message.setSuccess(false);
//            return;
//        }

        if(StringUtils.isBlank(total_fee)){
            message.setMsg("金额不能为空");
            message.setSuccess(false);
            return;
        }

        if(StringUtils.isBlank(appid)){
            message.setMsg("公众账号ID不能为空");
            message.setSuccess(false);
            return;
        }

        if(StringUtils.isBlank(body)){
            message.setMsg("商品描述不能为空");
            message.setSuccess(false);
            return;
        }

        try {
            //根据捐赠ID查询订单编号
            String orderNo = "";
            Donation donationById = donationMapper.selectById(Long.parseLong(donationId));
            if (donationById != null) {
                orderNo = donationById.getOrderNo();
            }
            //获取订单详情
            Donation donation = donationService.selectByOrderNo(orderNo);
            //判断订单是否存在
            if (donation != null) {
                //判断订单是否已支付
                if (0 == donation.getPayStatus()) {

//                    //更新入口类型
//                    if (StringUtils.isNotBlank(getOut_trade_no()) && StringUtils.isNotBlank(payType)) {
//                        donationService.updatePayType(getOut_trade_no(), payType, null);
//                    }

                    // 统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("appid", appid);
                    params.put("mch_id", partner);
                    params.put("body", body);
                    params.put("out_trade_no", orderNo);
                    params.put("total_fee", MoneyUtil.yuanToFen(total_fee));
                    String ip = IpKit.getRealIp(getRequest());
                    if (StrKit.isBlank(ip)) {
                        ip = "127.0.0.1";
                    } else {
                        ip = ip.split(",")[0].trim();
                    }

                    params.put("spbill_create_ip", ip);
                    params.put("trade_type", PaymentApi.TradeType.APP.name());
                    params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
                    params.put("notify_url", notify_url);
                    //params.put("openid", openId);
                    logger.info(params);
                    String sign = PaymentKit.createSign(params, partnerKey);
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
                            Donation donationPayBack = donationService.wechatDonationPayBack(orderNo);
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
                    packageParams.put("appid", appid);
                    packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
                    packageParams.put("noncestr", System.currentTimeMillis() + "");
                    //packageParams.put("package", "prepay_id=" + prepay_id);
                    packageParams.put("package", "Sign=WXPay");
                    packageParams.put("prepayid",prepay_id);
                    packageParams.put("partnerid",partner);
                    //packageParams.put("signType", "MD5");
                    String packageSign = PaymentKit.createSign(packageParams, partnerKey);
                    packageParams.put("paySign", packageSign);
                    //String jsonStr = JsonUtils.toJson(packageParams);
                    Object jsonStr = packageParams;
                    System.out.println(jsonStr);
                    message.init(true, "下单成功", jsonStr);

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
        //super.writeJson(message);
    }


    public void wechatAppDonationPayBack(String orderNo){
        Message message = new Message();
        Donation donation = selectByOrderNo(orderNo);
        if (donation != null && donation.getPayStatus() != 1) {

            //支付状态： 1 已支付
            donation.setPayStatus(1);

            //支付方式（10:支付宝支付；20:微信支付）
            donation.setPayMode("20");
            //支付途径（10:手机APP；20:网站；30:微信）
            donation.setPayMethod("10");

            donation.setPayTime(new Date());

            donation.setPayMoney(donation.getMoney());
            donation.setConfirmStatus(30);
            donation.setConfirmId(0);
            donation.setConfirmTime(new Date());
            update(donation);
            if(org.apache.commons.lang3.StringUtils.isNotBlank(Global.founUrl)){
                Map<String, Object> map = new HashMap();
                Long projectId =donation.getProject().getProjectId();
                Project project= projectMapper.selectById(projectId);
                String projectIncome = project.getFounProject();
                if(!("").equals(projectIncome) && projectIncome != null){
                    map.put("projectIncome",projectIncome);
                }
                map.put("personLiable", donation.getX_name());
                map.put("personNum", donation.getX_phone());
                map.put("paymentCount", String.valueOf(donation.getMoney()));
                map.put("createDate", TimeZoneUtils.getFormatDate().substring(0,10) );
                map.put("incomeType", "微信");
                map.put("orderNum", donation.getOrderNo());

                FounHttpUtils.saveFounIncome(map);
            }

            //生成证书
            //donationService.createCertificate(donation.getDonationId());
        }
        message.init(true, "支付成功", donation);
    }


    public Donation selectByOrderNo(String orderNo) {
        Donation donation = donationMapper.selectByOrderNo(orderNo);
        donation.setProject(projectMapper.selectById(donation.getProjectId()));
        return donation;
    }
    public void update(Donation donation) {

        logger.info("-------> donation : " + donation.toString());
        if(donation.getConfirmStatus() >= 30){
            donation.setPayStatus(1);
            donation.setPayTime(TimeZoneUtils.getDate());
        }
        donationMapper.update(donation);


        //生成证书并保存
        if(donation.getConfirmStatus() >= 30){
            try{
                Donation donationTmp = selectById(donation.getDonationId());
                if(org.apache.commons.lang3.StringUtils.isBlank(donationTmp.getCertificatePicUrl())){
                    createCertificate(donation.getDonationId());
                }
            }catch (Exception e) {
                logger.error(e, e);
            }
        }
    }

    public Donation selectById(long id) {
        return donationMapper.selectById(id);
    }

    public int createCertificate(long donationId){
        try{
            Donation donation  = selectById(donationId);
            if(donation == null){
                return 1;
            }

            String certificateImage =  Certificate.createCertificate(donation);
            donation.setCertificatePicUrl(certificateImage);

            donationMapper.update(donation);
            return 0;
        }catch (Exception e){
            logger.error(e, e);
            return -1;
        }

    }

    public HttpServletRequest getRequest()
    {
        return ServletActionContext.getRequest();
    }
}
