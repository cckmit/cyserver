package ltd.moore.ctravel.register.mapper;

import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.customer.model.CustomerDetailVO;
import org.springframework.stereotype.Repository;

/**
 * @Author: ZhuTail
 * @Date: 2017/6/6.
 */
@Repository
public interface RegisterMapper {

    /**
     * 插入客户账号
     *
     * @param customerAccountVO
     */
    void insertCustomerAccount(CustomerAccountVO customerAccountVO);

    /**
     * 插入客户信息
     *
     * @param customerDetailVO
     */
    void insertCustomerAccountDetail(CustomerDetailVO customerDetailVO);

    /**
     * 修改密码
     *
     * @param customerAccountVO
     */
    void updateCustomerAccount(CustomerAccountVO customerAccountVO);
}
