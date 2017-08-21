package ltd.moore.ctravel.refreshpwd.service.impl;

import com.hdos.platform.base.user.model.AccountInfoVO;
import com.hdos.platform.common.util.CacheUtils;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.refreshpwd.dto.RefreshPwdDTO;
import ltd.moore.ctravel.refreshpwd.service.RefreshPwdService;
import ltd.moore.ctravel.register.mapper.RegisterMapper;
import ltd.moore.ctravel.sms.mapper.SmsSendMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by youcai on 2017/6/6
 */
@Service
public class RefreshPwdServiceImpl implements RefreshPwdService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegisterMapper registerMapper;
    @Autowired
    private SmsSendMapper smsSendMapper;

    @Override
    public Map<String, Object> refreshpwdValidation(RefreshPwdDTO refreshPwdDTO) {

        Map<String, Object> result = new HashMap<>();
        try {
            //验证短信验证码
            if (!CacheUtils.get(refreshPwdDTO.getMessageId()).equals(refreshPwdDTO.getSmsCaptcha())) {
                result.put("resultCode", ResultCodeInfo.E100006);
                result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E100006));
                return result;
            }
            //验证手机号是否与账户关联
            if (smsSendMapper.queryAccountByMobile(refreshPwdDTO.getPhoneNo()) == null) {
                result.put("resultCode", ResultCodeInfo.E100008);
                result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E100008));
                return result;
            }

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            result.put("resultCode", ResultCodeInfo.E999999);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E999999));
            return result;
        }

        String refreshpwdId = UUID.randomUUID().toString();
        CacheUtils.put(refreshpwdId, refreshPwdDTO.getPhoneNo());
        result.put("refreshpwdId", refreshpwdId);
        result.put("resultCode", ResultCodeInfo.E000000);
        return result;
    }

    @Override
    public Map<String, Object> refreshpwd(RefreshPwdDTO refreshPwdDTO) {

        Map<String, Object> result = new HashMap<>();

        try {
            //验证是否是直接发起找回密码请求
            if (refreshPwdDTO.getPhoneNo().equals(CacheUtils.get(refreshPwdDTO.getRefreshpwdId()))) {
                AccountInfoVO accountInfoVO = smsSendMapper.queryAccountByPhoneNo(refreshPwdDTO.getPhoneNo());
                if (DigestUtils.md5Hex(refreshPwdDTO.getPassword()).equals(accountInfoVO.getPwd())) {
                    result.put("resultCode", ResultCodeInfo.E555555);
                    result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E555555));
                } else {
                    CustomerAccountVO customerAccountVO = new CustomerAccountVO();
                    customerAccountVO.setMobile(refreshPwdDTO.getPhoneNo());
                    customerAccountVO.setPassword(DigestUtils.md5Hex(refreshPwdDTO.getPassword()));
                    //更新密码
                    registerMapper.updateCustomerAccount(customerAccountVO);
                    result.put("resultCode", ResultCodeInfo.E000000);

                }
            } else {
                result.put("resultCode", ResultCodeInfo.E666666);
                result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E666666));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.put("resultCode", ResultCodeInfo.E999999);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E999999));
        }
        return result;
    }
}
