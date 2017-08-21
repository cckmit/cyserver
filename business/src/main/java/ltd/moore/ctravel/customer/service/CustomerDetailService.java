package ltd.moore.ctravel.customer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hdos.platform.common.page.Page;
import com.hdos.platform.common.page.PageImpl;
import com.hdos.platform.common.util.StringUtils;
import com.hdos.platform.core.base.BaseService;
import ltd.moore.ctravel.customer.mapper.CustomerDetailMapper;
import ltd.moore.ctravel.customer.model.CustomerDetailVO;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class CustomerDetailService extends BaseService<CustomerDetailVO> {
	
	@Autowired
	private CustomerDetailMapper customerDetailMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerDetailService.class);
	
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
	public Page<CustomerDetailVO> findPage(Map<String, Object> condition, int pageNumber, int pageSize) {
		int total = customerDetailMapper.count(condition);
		RowBounds rowBounds = new RowBounds((pageNumber - 1) * pageSize, pageSize);
		List<CustomerDetailVO> content = total > 0 ? customerDetailMapper.list(condition, rowBounds) : new ArrayList<CustomerDetailVO>(0);
		return new PageImpl<CustomerDetailVO>(content, pageNumber, pageSize, total);
	}
	
	/**
	 * 按id查询
	 * @return
	 */
	public CustomerDetailVO readCustomerDetailById(String customerDetailId) {
	    return  customerDetailMapper.getById(customerDetailId);
	}
	
	/**
	 * 保存
	 * @param customerDetailVO
	 */
	public boolean saveCustomerDetail(CustomerDetailVO customerDetailVO) {
		if (null == customerDetailVO) {
			logger.error("保存ERROR：" + customerDetailVO);
			throw new IllegalArgumentException();
		}

		if (StringUtils.isEmpty(customerDetailVO.getCustomerId())) {// 新增
		//自定义设置主码
		     customerDetailVO.setCustomerId(generateKey(customerDetailVO));
			customerDetailMapper.insert(customerDetailVO);
			return false;
		} else {// 更新
			customerDetailMapper.update(customerDetailVO);
			return false;
		}
	}
	
	/**
	 * 批量删除
	 * @param customerDetailIds
	 */
	public boolean deleteCustomerDetails(String... customerDetailIds) {
		customerDetailMapper.deleteInBulk(customerDetailIds);
		return true;
	}
}
