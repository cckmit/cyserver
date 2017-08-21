package ltd.moore.ctravel.customer.mapper;

import com.hdos.platform.core.base.BaseMapper;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountMapper extends BaseMapper<CustomerAccountVO> {
    CustomerAccountVO getCustomerAccountVOByAccount(String account);

    /**
     * 根据密码账号查询
     * @param username
     * @param password
     * @return
     */
    CustomerAccountVO queryAccountByAccountAndPwd(String username, String password);
}
