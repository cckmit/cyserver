package ltd.moore.ctravel.pay;

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.common.page.Page;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.experience.model.ServiceTimeVO;
import ltd.moore.ctravel.experience.service.ExperienceDetailService;
import ltd.moore.ctravel.experience.service.ServiceTimeService;
import ltd.moore.ctravel.pay.model.OrderInfoVO;
import ltd.moore.ctravel.pay.service.TradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cocouzx on 2017-7-5 0005.
 */
@Controller
@RequestMapping(value = "/pay")
public class payController {
    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private ServiceTimeService serviceTimeService;
    @Autowired
    private ExperienceDetailService experienceDetailService;
    /**
     * 后台管理订单
     */
    @RequestMapping(value = "/init")
    public String init(){
        return "pay/testPay";
    }

    /**
     * 订单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(String account, int page, int rows){
        Map<String, Object> queryCondition = new HashMap<String,Object>();
        queryCondition.put("customerId", account);
        Page<OrderInfoVO> orderInfoVOPage = tradeOrderService.findPage(queryCondition, page, rows);
        for(OrderInfoVO orderInfoVO :orderInfoVOPage){
            ServiceTimeVO serviceTimeVO = serviceTimeService.readServiceTimeById(orderInfoVO.getServiceTimeId());
            orderInfoVO.setStartTime(serviceTimeVO.getStartTime());
            orderInfoVO.setEndTime(serviceTimeVO.getEndTime());
            orderInfoVO.setTitle(experienceDetailService.getDetailById(orderInfoVO.getExperienceId()).getTitle());
        }
        JSONObject rst = new JSONObject();
        rst.put("total", orderInfoVOPage.getTotalElements());
        rst.put("rows", orderInfoVOPage.getContent());
        return rst.toJSONString();
    }

}
