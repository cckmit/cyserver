package ltd.moore.ctravel.customer.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import java.util.Map;

import com.hdos.platform.base.user.model.AccountVO;
import com.hdos.platform.common.page.Page;
import com.hdos.platform.common.page.PageImpl;
import com.hdos.platform.core.base.BaseService;
import ltd.moore.ctravel.customer.mapper.CustomerAccountMapper;
import ltd.moore.ctravel.customer.mapper.CustomerDetailMapper;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.customer.model.CustomerDetailVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class CustomerAccountService extends BaseService<CustomerAccountVO> {
	
	@Autowired
	private CustomerAccountMapper customerAccountMapper;
	@Autowired
	private CustomerDetailMapper customerDetailMapper;
	private static final Logger logger = LoggerFactory.getLogger(CustomerAccountService.class);
	
	/**
	 * 分页查询
	 * 
	 * @param condition
	 *            条件
	 * @param pageNumber
	 *            页码，从 1 开始
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public Page<CustomerAccountVO> findPage(Map<String, Object> condition, int pageNumber, int pageSize) {
		int total = customerAccountMapper.count(condition);
		RowBounds rowBounds = new RowBounds((pageNumber - 1) * pageSize, pageSize);
		List<CustomerAccountVO> content = total > 0 ? customerAccountMapper.list(condition, rowBounds) : new ArrayList<CustomerAccountVO>(0);
		return new PageImpl<CustomerAccountVO>(content, pageNumber, pageSize, total);
	}
	
	/**
	 * 按id查询
	 * @return
	 */
	public CustomerAccountVO readCustomerAccountById(String customerAccountId) {
	    return  customerAccountMapper.getById(customerAccountId);
	}
	/**
	 * 保存
	 * @param customerAccountVO
	 */
	public boolean saveCustomerAccount(CustomerAccountVO customerAccountVO,CustomerDetailVO customerDetailVO) {
		if (null == customerAccountVO) {
			logger.error("保存ERROR：" + customerAccountVO);
			throw new IllegalArgumentException();
		}
			String pwd="";
		if (!(customerAccountVO.getPassword() == null || customerAccountVO.getPassword()=="")) {
			 pwd = DigestUtils.md5Hex(customerAccountVO.getPassword());
		}
		if (StringUtils.isEmpty(customerAccountVO.getCustomerId())) {// 新增
			if (this.getCustomerAccountVOByAccount(customerAccountVO.getAccount()) != null) {
				return true;
			}
		//自定义设置主码
			String customerId = generateKey(customerAccountVO);
			customerAccountVO.setCustomerId(customerId);
			customerAccountVO.setStatus(1);
			customerAccountVO.setPassword(pwd);
			customerDetailVO.setCustomerId(customerId);
			customerDetailVO.setIdcardCheckStatus(0);
			customerAccountMapper.insert(customerAccountVO);
			customerDetailMapper.insert(customerDetailVO);
			return false;
		} else {// 更新
			if(pwd!=""){
				customerAccountVO.setPassword(pwd);
			}
			customerAccountMapper.update(customerAccountVO);
			customerDetailMapper.update(customerDetailVO);
			return false;
		}
	}
	
	/**
	 * 批量删除
	 * @param customerAccountIds
	 */
	public boolean deleteCustomerAccounts(String... customerAccountIds) {
		customerAccountMapper.deleteInBulk(customerAccountIds);
		customerDetailMapper.deleteInBulk(customerAccountIds);
		return true;
	}
	/**
	 * 获取merchantVO
	 *
	 * @param account
	 * @return
	 */
	public CustomerAccountVO getCustomerAccountVOByAccount(String account) {
		return customerAccountMapper.getCustomerAccountVOByAccount(account);
	}

	/**
	 * 根据密码账号查询
	 * @param username
	 * @param password
	 * @return
	 */
	public CustomerAccountVO queryAccountByAccountAndPwd(String username, String password) {
		return customerAccountMapper.queryAccountByAccountAndPwd(username, password);
	}
}
