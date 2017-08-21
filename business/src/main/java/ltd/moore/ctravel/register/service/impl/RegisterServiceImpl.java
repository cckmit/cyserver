package ltd.moore.ctravel.register.service.impl;


import com.hdos.platform.common.util.CacheUtils;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.customer.model.CustomerDetailVO;
import ltd.moore.ctravel.register.dto.RegisterDTO;
import ltd.moore.ctravel.register.mapper.RegisterMapper;
import ltd.moore.ctravel.register.service.RegisterService;
import ltd.moore.ctravel.sms.mapper.SmsSendMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhutail on 2017/6/5
 */
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegisterMapper registerMapper;

    @Autowired
    private SmsSendMapper smsSendMapper;

    @Override
    public Map<String, Object> registerValidation(RegisterDTO registerDTO) {

        Map<String, Object> result = new HashMap<>();
        try {
            //验证短信验证码
            if (!CacheUtils.get(registerDTO.getMessageId()).equals(registerDTO.getSmsCaptcha())) {
                result.put("resultCode", ResultCodeInfo.E100006);
                result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E100006));
                return result;
            }
            //验证手机号
            if (smsSendMapper.queryAccountByPhoneNo(registerDTO.getMobile()) != null) {
                result.put("resultCode", ResultCodeInfo.E100005);
                result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E100005));
                return result;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            result.put("resultCode", ResultCodeInfo.E999999);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E999999));
            return result;
        }

        String registerId = UUID.randomUUID().toString();
        CacheUtils.put(registerId, registerDTO.getMobile());
        result.put("registerId", registerId);
        result.put("resultCode", ResultCodeInfo.E000000);
        return result;
    }

    @Override
    public Map<String, Object> register(RegisterDTO registerDTO) {

        Map<String, Object> result = new HashMap<>();

        try {
            //验证是否是直接发起注册请求
            if (registerDTO.getMobile().equals(CacheUtils.get(registerDTO.getRegisterId()))) {
                CustomerAccountVO customerAccountVO = new CustomerAccountVO();
                customerAccountVO.setCustomerId(UUID.randomUUID().toString());
                customerAccountVO.setAccount(registerDTO.getAccount());
                customerAccountVO.setCreateTime(new Date());
                customerAccountVO.setEmail(registerDTO.getEmail());
                //默认普通用户
                customerAccountVO.setCustomerType(1);
                customerAccountVO.setMobile(registerDTO.getMobile());
                customerAccountVO.setPassword(DigestUtils.md5Hex(registerDTO.getPassword()));
                customerAccountVO.setStatus(1);
                //插入客户账号
                registerMapper.insertCustomerAccount(customerAccountVO);

                CustomerDetailVO customerDetailVO = new CustomerDetailVO();
                customerDetailVO.setCustomerId(customerAccountVO.getCustomerId());
                customerDetailVO.setAccountNo(registerDTO.getAccountNo());
                customerDetailVO.setArea(registerDTO.getArea());
                customerDetailVO.setGender(registerDTO.getGender());
                customerDetailVO.setHeadImg(registerDTO.getHeadImg());
                customerDetailVO.setIdcardImg(registerDTO.getIdcardImg());
                customerDetailVO.setIdcardCheckStatus(registerDTO.getIdcardCheckStatus());
                customerDetailVO.setImToken(registerDTO.getImToken());
                customerDetailVO.setNickName(registerDTO.getNickName());
                customerDetailVO.setJob(registerDTO.getJob());
                customerDetailVO.setLanguage(registerDTO.getLanguage());
                customerDetailVO.setSchool(registerDTO.getSchool());
                //插入客户信息
                registerMapper.insertCustomerAccountDetail(customerDetailVO);

                result.put("resultCode", ResultCodeInfo.E000000);
            } else {
                result.put("resultCode", ResultCodeInfo.E777777);
                result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E777777));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.put("resultCode", ResultCodeInfo.E999999);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E999999));
        }
        return result;
    }
}
