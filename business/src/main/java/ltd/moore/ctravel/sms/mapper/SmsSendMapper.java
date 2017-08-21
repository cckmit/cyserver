package ltd.moore.ctravel.sms.mapper;

import com.hdos.platform.base.user.model.AccountInfoVO;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import org.springframework.stereotype.Repository;

/**
 * @Author: ZhuTail
 * @Date: 2017/5/18.
 */
@Repository
public interface SmsSendMapper {

    /**
     * 根据手机号获取账号信息
     *
     * @param phoneNo
     * @return
     */
    AccountInfoVO queryAccountByPhoneNo(String phoneNo);
    /**
     * 根据手机号获取账号信息
     *
     * @param mobile
     * @return
     */
    CustomerAccountVO queryAccountByMobile(String mobile);
}
