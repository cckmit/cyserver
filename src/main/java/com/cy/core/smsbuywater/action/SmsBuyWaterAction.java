package com.cy.core.smsbuywater.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.smsAccount.entity.SmsAccount;
import com.cy.core.smsAccount.service.SmsAccountService;
import com.cy.core.smsbuywater.entity.SmsBuyWater;
import com.cy.core.smsbuywater.service.SmsBuyWaterService;
import com.cy.core.user.entity.User;
import com.cy.smscloud.http.SmsCloudHttpUtils;

import com.cy.system.Global;
import com.cy.util.WebUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/17.
 */
@Namespace("/smsBuyWater")
@Action("smsBuyWaterAction")
public class SmsBuyWaterAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(SmsBuyWaterAction.class);

    private SmsBuyWater smsBuyWater;
    private String smsBuyWaterId;
    private String orderNo;
    private String price;
    private String buyNum;
    @Autowired
    private SmsBuyWaterService smsBuyWaterService;
    @Autowired
    private SmsAccountService smsAccountService;
    //得到列表并且分页
    public void dataGraidSmsBuyWater(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", rows);
        map.put("page", page);
        if(smsBuyWater != null && !smsBuyWater.equals("")){
            map.put("account",smsBuyWater.getAccount());
        }
        DataGrid<SmsBuyWater> data = smsBuyWaterService.dataGrid(map);
        super.writeJson(data);
    }
    //批量删除流水
    public void deleteSmsBuyWater(){
        Message message=new Message();
        try {
            smsBuyWaterService.deleteSmsBuyWater(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    //新增流水
    public void saveSmsBuyWater(){
        Message message=new Message();

        User user = getUser();
        if(user != null && user.getDeptId() > 0 )
        {
            SmsAccount smsAccount = smsAccountService.getByAlumniId(String.valueOf(user.getDeptId()));
            if(smsAccount != null && StringUtils.isNotBlank(smsAccount.getId()))
            {
                HttpServletRequest request = this.getRequest();
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

                //basePath = "http://192.168.0.39:8080/cy/";
                String resultUrl = basePath + "smsBuyWater/smsBuyWaterAction!doNotNeedSessionAndSecurity_resultSuccess.action";
                String notifyUrl = basePath + "smsBuyWater/smsBuyWaterAction!doNotNeedSessionAndSecurity_notifySuccess.action";
                Map<String, String> map = SmsCloudHttpUtils.saveAnOrder(Global.smsUrl, smsAccount.getAccount(), smsAccount.getPassword(), smsBuyWater.getBuyPrice(), resultUrl, notifyUrl );

                if(map != null && map.get("orderNo") != null){
                    smsBuyWater.preInsert();
                    smsBuyWater.setId(map.get("id"));
                    smsBuyWater.setAccountId(smsAccount.getId());
                    smsBuyWater.setOrderNo(map.get("orderNo"));
                    smsBuyWater.setBuyNum(map.get("buyNum"));
                    smsBuyWater.setBuyPrice(map.get("buyPrice"));
                    smsBuyWater.setSinglePrice(map.get("singlePrice"));
                    smsBuyWater.setSurplusNum(map.get("buyNum"));
                    smsBuyWater.setSurplusPrice(map.get("buyPrice"));
                    smsBuyWater.setBuyTime(map.get("buyTime"));
                    smsBuyWater.setPayStatus("0");
                    smsBuyWater.setNotifyUrl(notifyUrl);
                    smsBuyWater.setResultUrl(resultUrl);
                    smsBuyWater.setFlowPacketDetailId(map.get("flowPacketDetailId"));

                    if(StringUtils.isNotBlank(Global.SMS_CLOUD_ORDER_URL)){

                        String payUrl = Global.SMS_CLOUD_ORDER_URL + "?WIDout_trade_no="+ smsBuyWater.getOrderNo() +"&WIDsubject=充值&WIDtotal_fee=" + smsBuyWater.getBuyPrice() + "&payType=1" ;
                        smsBuyWater.setRemarks(payUrl);

                        smsBuyWaterService.saveSmsBuyWater(smsBuyWater);
                        message.setObj(smsBuyWater);
                        message.setMsg("下单成功");
                        message.setSuccess(true);
                    }else{
                        message.setMsg("支付地址未配置");
                        message.setSuccess(false);
                    }

                }else{
                    message.setMsg("下单失败，请联系管理员");
                    message.setSuccess(false);
                }
            }
            else
            {
                message.setMsg("未查询到短信账号，无法下单");
                message.setSuccess(false);
            }
        }
        else
        {
            message.setMsg("查詢不到此用戶");
            message.setSuccess(false);
        }

        super.writeJson(message);
    }
    //修改流水
    public void updateSmsBuyWater(){
        Message message=new Message();
        try {
            smsBuyWater.preUpdate();
            smsBuyWaterService.updateSmsBuyWater(smsBuyWater);
            message.setMsg("修改成功");
            message.setSuccess(true);

        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("修改失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    //查看某一天新闻的详情
    public void getSmsBuyWaterById(){
        SmsBuyWater smsBuyWater=smsBuyWaterService.getSmsBuyWaterById(smsBuyWaterId);
        super.writeJson(smsBuyWater);
    }

    //查詢用戶可用流量包
    public void doNotNeedSecurity_getUseAblePackage()
    {
        HttpServletRequest request = this.getRequest();
        String type = request.getParameter("type");// 0：账号、1：主键、2：标识
        String except = request.getParameter("except");

        User user = getUser();
        List<SmsBuyWater> list = new ArrayList<>();
        if(user != null && user.getDeptId() > 0)
        {
            SmsAccount smsAccount = smsAccountService.getByAlumniId(String.valueOf(user.getDeptId()));
            if(smsAccount != null && StringUtils.isNotBlank(smsAccount.getId()))
            {
                Map<String , Object> map = new HashMap<>();
                map.put("accountId",smsAccount.getId());
                map.put("useAble", "1");
                map.put("payStatus", "1");

                if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(except)) {
                    switch (type) {
                        case "0":// 账号
                            map.put("except",  orderNo);
                            break;
                        case "1":// 主键
                            SmsBuyWater sbw1 = smsBuyWaterService.getSmsBuyWaterById(except);
                            if (sbw1 != null) {
                                map.put("except",  sbw1.getOrderNo());
                            }
                            break;
                        case "2":// 标识
                            if ("notNext".equals(except)) {
                                SmsBuyWater sbw2 = smsBuyWaterService.getSmsBuyWaterById(smsAccount.getNextBuyWaterId());
                                if (sbw2 != null) {
                                    map.put("except",  sbw2.getOrderNo());
                                }
                            }
                            else if ("notCurr".equals(except)) {
                                SmsBuyWater sbw2 = smsBuyWaterService.getSmsBuyWaterById(smsAccount.getCurrBuyWaterId());
                                if (sbw2 != null) {
                                    map.put("except",  sbw2.getOrderNo());
                                }
                            }
                            break;
                    }
                }

                list = smsBuyWaterService.findList(map);
            }
        }
        super.writeJson(list);
    }

    // 根据购买条数，以及短信云后台设置的梯度单价，计算出总金额
    public void doNotNeedSecurity_countTotalAmount() {
        User user = getUser();

        if (user != null && user.getDeptId() > 0) {
            SmsAccount smsAccount = smsAccountService.getByAlumniId(String.valueOf(user.getDeptId()));

            if (smsAccount != null && StringUtils.isNotBlank(smsAccount.getAccount()) && StringUtils.isNotBlank(smsAccount.getPassword())) {
                Map<String, String> resultMap = SmsCloudHttpUtils.findCloudFlowPacketOrderByNum(SmsCloudHttpUtils.SMS_CLOUD_URL, smsAccount.getAccount(), smsAccount.getPassword(), buyNum);
                if (resultMap != null) {
                    super.writeJson(resultMap);
                    return;
                }
            }
        }

        super.writeJson(null);
    }

    //查詢當前包明細來計算付費金額的對應的短信條數
    public void doNotNeedSecurity_getTotal()
    {
        User user = getUser();

        if(user != null && user.getDeptId() > 0)
        {
            SmsAccount smsAccount = smsAccountService.getByAlumniId(String.valueOf(user.getDeptId()));
            if( smsAccount != null && StringUtils.isNotBlank(smsAccount.getAccount()) && StringUtils.isNotBlank(smsAccount.getPassword()))
            {
                Map<String, String> resultMap = SmsCloudHttpUtils.findCurrentCloudFlowPacketDetail(SmsCloudHttpUtils.SMS_CLOUD_URL, smsAccount.getAccount(), smsAccount.getPassword(), price);
                if(resultMap != null)
                {
                    super.writeJson(resultMap);
                    return;
                }
            }
        }
        super.writeJson(null);
    }

    //同步回調
    public String doNotNeedSessionAndSecurity_resultSuccess(){
        String code = "0";
        HttpServletRequest request = this.getRequest();
        String orderNo = request.getParameter("orderNo");
        String success = request.getParameter("success");
        if(StringUtils.isNotBlank(orderNo) && StringUtils.isNotBlank(orderNo) && "1".equals(success)){
            SmsBuyWater smsBuyWater = smsBuyWaterService.getSmsBuyWaterByOrderNum(orderNo);
            if(smsBuyWater != null){
                smsBuyWater.setPayStatus("1");
                smsBuyWater.preUpdate();
                smsBuyWaterService.updateSmsBuyWater(smsBuyWater);
                code = "1";
            }
        }
        System.out.println(orderNo+"支付状态"+code);
        return code;
    }

    //異步回調
    public void doNotNeedSessionAndSecurity_notifySuccess(){
        HttpServletRequest request = this.getRequest();
        String orderNo = request.getParameter("orderNo");
        String success = request.getParameter("success");
        if(StringUtils.isNotBlank(orderNo) && StringUtils.isNotBlank(orderNo) && "1".equals(success)){
            SmsBuyWater smsBuyWater = smsBuyWaterService.getSmsBuyWaterByOrderNum(orderNo);
            if(smsBuyWater != null){
                smsBuyWater.setPayStatus("1");
                smsBuyWater.preUpdate();
                smsBuyWaterService.updateSmsBuyWater(smsBuyWater);
                System.out.println(orderNo+"支付状态成功");
            }
        }
    }

    public SmsBuyWater getSmsBuyWater() {
        return smsBuyWater;
    }

    public void setSmsBuyWater(SmsBuyWater smsBuyWater) {
        this.smsBuyWater = smsBuyWater;
    }

    public String getSmsBuyWaterId() {
        return smsBuyWaterId;
    }

    public void setSmsBuyWaterId(String smsBuyWaterId) {
        this.smsBuyWaterId = smsBuyWaterId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }
}
