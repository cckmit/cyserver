package ltd.moore.ctravel.customer.controller;

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.common.page.Page;
import ltd.moore.ctravel.comment.model.CommentMessageVO;
import ltd.moore.ctravel.comment.service.CommentMessageService;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.customer.mapper.CustomerAccountMapper;
import ltd.moore.ctravel.customer.mapper.CustomerDetailMapper;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.customer.model.CustomerDetailVO;
import ltd.moore.ctravel.customer.service.CustomerAccountService;
import ltd.moore.ctravel.customer.service.CustomerDetailService;
import ltd.moore.ctravel.experience.mapper.ExperienceMapper;
import ltd.moore.ctravel.experience.model.ExperienceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/7/3 0003.
 */
@Controller
@RequestMapping("/api/customer")
public class StarCheckController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CustomerDetailService customerDetailService;
    @Autowired
    private CustomerDetailMapper customerDetailMapper;
    @Autowired
    private CustomerAccountMapper customerAccountMapper;
    /**
     * 申请审核
     *
     * @return
     */
    @RequestMapping(value = "/becomeStar", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  commitCheck(@RequestParam String customerId, @RequestParam String token) {
        CustomerDetailVO customerDetailVO2 = customerDetailService.readCustomerDetailById(customerId);
        if(customerDetailVO2.getIdcardCheckStatus()==0 ||customerDetailVO2.getIdcardCheckStatus()==2) {
            CustomerDetailVO customerDetailVO =new CustomerDetailVO();
            customerDetailVO.setCustomerId(customerId);
            customerDetailVO.setIdcardCheckStatus(9);
        }else if(customerDetailVO2.getIdcardCheckStatus()==1 ){
            return ResultGen(ResultCodeInfo.E200020);
        }else if(customerDetailVO2.getIdcardCheckStatus()==9 ){
            return ResultGen(ResultCodeInfo.E200021);
        }
        return ResultGen(ResultCodeInfo.E000000);
    }

    /**
     * 审核状态查询
     *
     * @return
     */
    @RequestMapping(value = "/checkStatus", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  checkStatus(@RequestParam String customerId, @RequestParam String token) {
        CustomerDetailVO customerDetailVO = customerDetailService.readCustomerDetailById(customerId);
        JSONObject rst = new JSONObject();
        rst.put("resultCode", ResultCodeInfo.E000000);
        rst.put("idcardCheckStatus", customerDetailVO.getIdcardCheckStatus());
        return rst.toJSONString();
    }
    /**
     * 生成返回信息
     * @param e000000
     * @return
     */
    private String ResultGen(ResultCodeInfo e000000) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resultCode",e000000.getResultCode());
        result.put("errorMsg",e000000.getErrorMsg());
        return JSONObject.toJSONString(result);
    }
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str.trim());
        return match.matches();
    }
    public static boolean isBigDecimal(String str) {
        Matcher match =null;
        if(isNumber(str)==true){
            Pattern pattern = Pattern.compile("[0-9]*");
            match = pattern.matcher(str.trim());
        }else{
            if(str.trim().indexOf(".")==-1){
                Pattern pattern = Pattern.compile("^[+-]?[0-9]*");
                match = pattern.matcher(str.trim());
            }else{
                Pattern pattern = Pattern.compile("/\\d{1,8}\\.{0,1}\\d{0,2}/");
                match = pattern.matcher(str.trim());
            }
        }
        return match.matches();
    }
}
