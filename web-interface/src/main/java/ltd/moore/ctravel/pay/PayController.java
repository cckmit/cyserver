package ltd.moore.ctravel.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hdos.platform.common.util.PrimaryKeyUtils;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Refund;
import com.pingplusplus.model.Webhooks;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.experience.mapper.ExperienceMapper;
import ltd.moore.ctravel.experience.model.ExperienceDetailVO;
import ltd.moore.ctravel.experience.model.ExperienceVO;
import ltd.moore.ctravel.homepage.model.HomePageHotVO;
import ltd.moore.ctravel.hotHomepage.controller.HotHomePageController;
import ltd.moore.ctravel.pay.model.OrderInfoVO;
import ltd.moore.ctravel.pay.service.TradeOrderService;
import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by Cocouzx on 2017-6-30 0030.
 */
@Controller
@RequestMapping("/pay")
public class PayController {
    private final static Logger logger = Logger.getLogger(PayController.class);
    private final static String APPID = "app_PG8WTSHa5GyHG0C8";
    private final static String APPKEY = "sk_test_DS8uTGOaPOy1PurXLOyjjr98";
    private final static String PUBKEY = "";
    private final static String PRIKEY = "";
    private final static String SUCCESSURL = "http://cocouzx.xicp.cn/web/hotAll/test";
    private final static String CANCELURL = "http://cocouzx.xicp.cn/web/hotAll/test";
    @Autowired
    private ExperienceMapper experienceMapper;
    @Autowired
    private TradeOrderService tradeOrderService;
    /**
     * 预定体验,生成订单接口
     */
    @RequestMapping(value = "/reserve/{experienceId}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotHomePage(@PathVariable(value = "experienceId") String experienceId,@RequestParam(value = "serviceTimeId") String serviceTimeId, @RequestParam(value = "userId") String userId, @RequestParam String token){
        try {
            //校验人数上限
            //初始化订单
            OrderInfoVO orderInfoVO = new OrderInfoVO();

            orderInfoVO.setExperienceId(experienceId);
            orderInfoVO.setServiceTimeId(serviceTimeId);
            orderInfoVO.setOrderId(getOrderId());
            orderInfoVO.setTradeStatus(0);
            orderInfoVO.setCustomerId(userId);
            ExperienceVO experienceVO = experienceMapper.getById(experienceId);
            orderInfoVO.setTradeAmount(Double.valueOf(experienceVO.getPrice()));
            tradeOrderService.createOrder(orderInfoVO);
            Map<String, Object> result = new HashMap<>();
            result.put("resultCode",ResultCodeInfo.E000000.getResultCode());
            result.put("errorMsg",ResultCodeInfo.E000000.getErrorMsg());
            result.put("orderInfo",orderInfoVO);
            return JSONObject.toJSONString(result);
        }catch (Exception e){
            logger.error(e.getMessage());
            Map<String, Object> result = new HashMap<>();
            result.put("resultCode",ResultCodeInfo.E999999.getResultCode());
            result.put("errorMsg",ResultCodeInfo.E999999.getErrorMsg());
            return JSONObject.toJSONString(result);
        }
    }
    public String getOrderId(){
        Date date = new Date();
        return String.valueOf(date.getTime()) + String.valueOf(((int)((Math.random()*9+1)*10000)));
    }
    /**
     * 支付订单
     */
    @RequestMapping(value = "/pay/{orderId}", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public Object pay(@PathVariable(value = "orderId") String orderId){
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        orderInfoVO = tradeOrderService.getOrderInfoById(orderId);
        Pingpp.apiKey = APPKEY;
        try{
            File file = ResourceUtils.getFile("classpath:rsa_private_key.pem");
            //linux影响？
            logger.info(file.getPath());
            Pingpp.privateKeyPath = file.getPath();
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        //开始支付
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("order_no",  orderInfoVO.getOrderId());
        chargeParams.put("amount", (int)orderInfoVO.getTradeAmount()*100);//订单总金额, 人民币单位：分（如订单总金额为 1 元，此处请填 100）
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", APPID);
        chargeParams.put("app", app);
        // 2016 年 6 月 16 日（不含当日）之前登录 Ping++ 管理平台填写支付宝手机网站的渠道参数的商户，请更新接口：https://help.pingxx.com/article/174737
        chargeParams.put("channel",  "alipay_wap");
        chargeParams.put("currency", "cny");
        chargeParams.put("client_ip",  "127.0.0.1");
        chargeParams.put("subject",  "CTRAVEL");
        chargeParams.put("body",  "PAYORDER");
        Map<String, String> extra = new HashMap<String, String>();
        //success_url 和 cancel_url 在本地测试不要写 localhost ，写 127.0.0.1，URL 后面不要加自定义参数
        extra.put("success_url", SUCCESSURL);
        extra.put("cancel_url", CANCELURL);
        chargeParams.put("extra", extra);
        try{
            Charge charge = null;
            charge = Charge.create(chargeParams);
            //更新订单状态为1
            OrderInfoVO orderInfoVO1 = new OrderInfoVO();
            orderInfoVO1.setTradeStatus(1);
            orderInfoVO1.setOrderId(orderId);
            tradeOrderService.update(orderInfoVO1);
            System.out.println(charge.toString());
            return charge;
        }catch (Exception e){
            logger.error(e.getMessage());
            Map<String, Object> result = new HashMap<>();
            result.put("resultCode",ResultCodeInfo.E999999.getResultCode());
            result.put("errorMsg",ResultCodeInfo.E999999.getErrorMsg());
            return JSONObject.toJSONString(result);
        }
    }

    /**
     * webhook
     */
    @RequestMapping(value = "/webhook", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public void webhook(HttpServletRequest request, HttpServletResponse response) {
        String hello = null;
        try {
            request.setCharacterEncoding("UTF8");
            //获取头部所有信息
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                System.out.println(key + " " + value);
            }
            // 获得 http body 内容
            BufferedReader reader = request.getReader();
            StringBuffer buffer = new StringBuffer();
            String string;
            while ((string = reader.readLine()) != null) {
                buffer.append(string);
            }
            reader.close();
            String jsonString = buffer.toString();
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String orderId = jsonObject.get("data").toString();
            jsonObject = JSON.parseObject(orderId);
            orderId = jsonObject.get("object").toString();
            jsonObject = JSON.parseObject(orderId);
            orderId = jsonObject.get("order_no").toString();
            String fourthOrderId = jsonObject.get("id").toString();
            // 解析异步通知数据
            Event event = Webhooks.eventParse(buffer.toString());
            if ("charge.succeeded".equals(event.getType())) {
                chargeSucceed(orderId, fourthOrderId);
                response.setStatus(200);
            } else if ("refund.succeeded".equals(event.getType())) {
                chargeRefund(orderId);
                response.setStatus(200);
            } else {
                response.setStatus(500);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
    void chargeSucceed(String orderId, String id){
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        orderInfoVO.setTradeStatus(2);
        orderInfoVO.setOrderId(orderId);
        orderInfoVO.setFourthOrderId(id);
        orderInfoVO.setTradeTime(new Date());
        tradeOrderService.update(orderInfoVO);
    }

    void chargeRefund(String orderId){
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        orderInfoVO.setTradeStatus(4);
        orderInfoVO.setOrderId(orderId);
        tradeOrderService.update(orderInfoVO);
    }
    /**
     * 订单退款
     */
    @RequestMapping(value = "/refund/{orderId}",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String refund(@PathVariable(value = "orderId") String orderId){
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        orderInfoVO = tradeOrderService.getOrderInfoById(orderId);
        Pingpp.apiKey = APPKEY;
        try{
            File file = ResourceUtils.getFile("classpath:rsa_private_key.pem");
            //linux影响？
            logger.info(file.getPath());
            Pingpp.privateKeyPath = file.getPath();
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        //开始退款
        try {
            Charge ch = Charge.retrieve(orderId);//ch_id 是已付款的订单号
            Map<String, Object> refundMap = new HashMap<String, Object>();
            refundMap.put("description", "refund");
            Refund re = ch.getRefunds().create(refundMap);
            System.out.println(re.toString());
            return re.toString();
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return null;
    }

    /**
     * 根据达人ID获取订单信息
     */
    @RequestMapping(value = "/orderInfoTalent/{talentId}",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String orderInfoTalent(@PathVariable(value = "talentId") String talentId){
        List<OrderInfoVO> listOrder = tradeOrderService.queryOrderInfoByCustomerId(talentId);
        Map<String, Object> result = new HashMap<>();
        result.put("resultCode",ResultCodeInfo.E000000.getResultCode());
        result.put("errorMsg",ResultCodeInfo.E000000.getErrorMsg());
        result.put("orderInfo",listOrder);
        return JSONObject.toJSONString(result);
    }

    /**
     * 根据游客ID获取订单信息
     */
    @RequestMapping(value = "/orderInfoCustomer/{customerId}",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String orderInfocUSTOMER(@PathVariable(value = "customerId") String customerId){
        List<OrderInfoVO> listOrder = tradeOrderService.queryOrderInfoByCustomerId(customerId);
        Map<String, Object> result = new HashMap<>();
        result.put("resultCode",ResultCodeInfo.E000000.getResultCode());
        result.put("errorMsg",ResultCodeInfo.E000000.getErrorMsg());
        result.put("orderInfo",listOrder);
        return JSONObject.toJSONString(result);
    }

    /**
     * 根据订单ID获取订单信息
     */
    @RequestMapping(value = "/orderInfo/{orderId}",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String orderInfo(@PathVariable(value = "orderId") String orderId){
        OrderInfoVO orderInfoVO = tradeOrderService.getOrderInfoById(orderId);
        Map<String, Object> result = new HashMap<>();
        result.put("resultCode",ResultCodeInfo.E000000.getResultCode());
        result.put("errorMsg",ResultCodeInfo.E000000.getErrorMsg());
        result.put("orderInfo",orderInfoVO);
        return JSONObject.toJSONString(result);
    }
}

