package ltd.moore.ctravel.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.base.component.service.JsmsService;
import com.hdos.platform.base.user.model.AccountInfoVO;
import com.hdos.platform.common.util.CacheUtils;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.sms.mapper.SmsSendMapper;
import ltd.moore.ctravel.sms.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhutail on 2017/6/5
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsSendMapper smsSendMapper;

    @Autowired
    private JsmsService jsmsService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public final String sendRegisterCode(String phoneNo) {

        Map<String, Object> result = new HashMap<>();

        AccountInfoVO accountInfoVO = smsSendMapper.queryAccountByPhoneNo(phoneNo);

        //验证号码有效性
        if (accountInfoVO != null) {
            result.put("resultCode", ResultCodeInfo.E100007);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E100007));
            return JSONObject.toJSONString(result);
        }
        return this.sendSms(phoneNo);
    }

    @Override
    public final String sendFindBackPwdCode(String phoneNo) {

        Map<String, Object> result = new HashMap<>();
        //验证号码有效性
        if (smsSendMapper.queryAccountByPhoneNo(phoneNo) == null) {
            result.put("resultCode", ResultCodeInfo.E100008);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E100008));
            return JSONObject.toJSONString(result);
        }
        return this.sendSms(phoneNo);
    }

    /**
     * @param phoneNo
     * @return
     */
    private String sendSms(String phoneNo) {
        Map<String, Object> result = new HashMap<>();
        try {
           result = jsmsService.sendMessage(phoneNo, "1", new ArrayList<String>());
            CacheUtils.put("messageId",result.get("messageId").toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            result.put("resultCode", ResultCodeInfo.E999999);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E999999));
        }
        return JSONObject.toJSONString(result);
    }

}