package ltd.moore.ctravel.experience.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hdos.platform.common.page.Page;
import com.hdos.platform.common.page.PageImpl;
import com.hdos.platform.common.util.StringUtils;
import com.hdos.platform.core.base.BaseService;
import ltd.moore.ctravel.experience.mapper.ServiceTimeMapper;
import ltd.moore.ctravel.experience.model.ServiceTimeVO;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ServiceTimeService extends BaseService<ServiceTimeVO> {
	
	@Autowired
	private ServiceTimeMapper serviceTimeMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceTimeService.class);
	
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
	public Page<ServiceTimeVO> findPage(Map<String, Object> condition, int pageNumber, int pageSize) {
		int total = serviceTimeMapper.count(condition);
		RowBounds rowBounds = new RowBounds((pageNumber - 1) * pageSize, pageSize);
		List<ServiceTimeVO> content = total > 0 ? serviceTimeMapper.list(condition, rowBounds) : new ArrayList<ServiceTimeVO>(0);
		return new PageImpl<ServiceTimeVO>(content, pageNumber, pageSize, total);
	}
	
	/**
	 * 按id查询
	 * @return
	 */
	public ServiceTimeVO readServiceTimeById(String serviceTimeId) {
	    return  serviceTimeMapper.getById(serviceTimeId);
	}
	/**
	 * 按体验id查询
	 * @return
	 */
	public List<ServiceTimeVO> getByExperienceId(String experienceId) {
		return  serviceTimeMapper.getByExperienceId(experienceId);
	}
	/**
	 * 保存
	 * @param serviceTimeVO
	 */
	public boolean saveServiceTime(ServiceTimeVO serviceTimeVO) {
		if (null == serviceTimeVO) {
			logger.error("保存ERROR：" + serviceTimeVO);
			throw new IllegalArgumentException();
		}

		if (StringUtils.isEmpty(serviceTimeVO.getServiceTimeId())) {// 新增
		//自定义设置主码
		     serviceTimeVO.setServiceTimeId(generateKey(serviceTimeVO));
			serviceTimeMapper.insert(serviceTimeVO);
			return false;
		} else {// 更新
			serviceTimeMapper.update(serviceTimeVO);
			return false;
		}
	}
	
	/**
	 * 批量删除
	 * @param serviceTimeIds
	 */
	public boolean deleteServiceTimes(String... serviceTimeIds) {
		serviceTimeMapper.deleteInBulk(serviceTimeIds);
		return true;
	}
}
