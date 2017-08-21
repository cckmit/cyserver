package ltd.moore.ctravel.experience.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hdos.platform.common.page.Page;
import com.hdos.platform.common.page.PageImpl;
import com.hdos.platform.common.util.StringUtils;
import com.hdos.platform.core.base.BaseService;
import ltd.moore.ctravel.experience.mapper.*;
import ltd.moore.ctravel.experience.model.*;
import java.sql.Timestamp;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class ExperienceDetailService extends BaseService<ExperienceDetailVO> {
	@Autowired
	private DestinationMapper destinationMapper;
	@Autowired
	private ExperienceAndServiceTypeMapper experienceAndServiceTypeMapper;
	@Autowired
	private ExperienceDetailMapper experienceDetailMapper;
	@Autowired
	private ExperienceAllMapper experienceAllMapper;
	@Autowired
	private ExperienceMapper experienceMapper;
	@Autowired
	private ServiceTimeMapper serviceTimeMapper;
	@Autowired
	private ServiceTypeMapper serviceTypeMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ExperienceDetailService.class);
	
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
	public Page<ExperienceDetailVO> findPage(Map<String, Object> condition, int pageNumber, int pageSize) {
		int total = experienceDetailMapper.count(condition);
		RowBounds rowBounds = new RowBounds((pageNumber - 1) * pageSize, pageSize);
		List<ExperienceDetailVO> content = total > 0 ? experienceDetailMapper.list(condition, rowBounds) : new ArrayList<ExperienceDetailVO>(0);
		return new PageImpl<ExperienceDetailVO>(content, pageNumber, pageSize, total);
	}
	public Page<ExperienceAllVO> findPage2(Map<String, Object> condition, int pageNumber, int pageSize) {
		int total = experienceAllMapper.count(condition);
		RowBounds rowBounds = new RowBounds((pageNumber - 1) * pageSize, pageSize);
		List<ExperienceAllVO> content = total > 0 ? experienceAllMapper.list(condition, rowBounds) : new ArrayList<ExperienceAllVO>(0);
		return new PageImpl<ExperienceAllVO>(content, pageNumber, pageSize, total);
	}
	/**
	 * 按id查询
	 * @return
	 */
	public ExperienceAllVO readExperienceDetailById(ExperienceAllVO experienceAllVO,String experienceDetailId,Model model) {
		ExperienceDetailVO experienceDetailVO = experienceDetailMapper.getById(experienceDetailId);
		if(experienceDetailVO==null){
			return null;
		}
		ExperienceVO experienceVO = experienceMapper.getById(experienceDetailVO.getExperienceId());
		List<ServiceTimeVO> serviceTimeVOList= serviceTimeMapper.getByExperienceId(experienceDetailVO.getExperienceId());
		ExperienceAndServiceTypeVO experienceAndServiceTypeVO=experienceAndServiceTypeMapper.getById(experienceDetailVO.getExperienceId());
		ServiceTypeVO serviceTypeVO = serviceTypeMapper.getById(experienceAndServiceTypeVO.getServiceTypeId());
		if(model!=null) {
			model.addAttribute("experienceDetailVO", experienceDetailVO);
			model.addAttribute("experienceVO", experienceVO);
			model.addAttribute("serviceTimeVOList", serviceTimeVOList);
			model.addAttribute("serviceTimeVOListLength", serviceTimeVOList.size());
			model.addAttribute("experienceAndServiceTypeVO", experienceAndServiceTypeVO);
			model.addAttribute("serviceTypeVO", serviceTypeVO);
		}
		experienceAllVO.setExperienceDetailId(experienceDetailVO.getExperienceDetailId());
		experienceAllVO.setExperienceId(experienceVO.getExperienceId());
		experienceAllVO.setServiceTypeId(serviceTypeVO.getServiceTypeId());
		experienceAllVO.setCustomerId(experienceVO.getCustomerId());
	    return  experienceAllVO;
	}
	
	/**
	 * 保存
	 * @param experienceAllVO
	 */
	public boolean saveExperienceDetail(ExperienceAllVO experienceAllVO,ExperienceDetailVO experienceDetailVO,ExperienceVO experienceVO) {
		ServiceTimeVO serviceTimeVO = new ServiceTimeVO();
		//ServiceTypeVO serviceTypeVO = new ServiceTypeVO();
		ExperienceAndServiceTypeVO experienceAndServiceTypeVO = new ExperienceAndServiceTypeVO();
//		serviceTimeVO.setStartTime(experienceAllVO.getStartTime());
//		serviceTimeVO.setEndTime(experienceAllVO.getEndTime());
//		serviceTimeVO.setServiceDate(experienceAllVO.getStartTime());
		experienceAndServiceTypeVO.setServiceTypeId(experienceAllVO.getServiceTypeId());
		experienceDetailVO.setDestination(experienceAllVO.getDestination());
		if (null == experienceAllVO) {
			logger.error("保存ERROR：" + experienceAllVO);
			throw new IllegalArgumentException();
		}
		if (StringUtils.isEmpty(experienceDetailVO.getExperienceDetailId())) {// 新增
		//自定义设置主码
			experienceDetailVO.setExperienceDetailId(generateKey(experienceDetailVO));
			experienceVO.setExperienceId(generateKey(experienceDetailVO));
			//体验服务明细表
			experienceDetailVO.setExperienceId(experienceVO.getExperienceId());
			//体验服务信息表
			experienceVO.setExperienceDetailId(experienceDetailVO.getExperienceDetailId());
			//TODO
			if(experienceAllVO.getCustomerId()!=null) {
				experienceVO.setCustomerId(experienceAllVO.getCustomerId());
			}
			//服务类型和体验关联关系
			experienceAndServiceTypeVO.setExperienceId(experienceVO.getExperienceId());
			dataTimeArraySplit(experienceAllVO,serviceTimeVO,experienceDetailVO);
			experienceDetailMapper.insert(experienceDetailVO);
			experienceAndServiceTypeMapper.insert(experienceAndServiceTypeVO);
			experienceMapper.insert(experienceVO);
			return false;
		} else {// 更新
			dataTimeArraySplit2(experienceAllVO,serviceTimeVO,experienceDetailVO);
			String [] serviceTypeIds =experienceAllVO.getServiceTypeId().split(",");
			if(serviceTypeIds.length==1){
				experienceAndServiceTypeVO.setServiceTypeId(serviceTypeIds[0]);
			}else {
				if (serviceTypeMapper.getById(serviceTypeIds[0]).getServiceName().equals(serviceTypeIds[1])) {
					experienceAndServiceTypeVO.setServiceTypeId(serviceTypeIds[0]);
				} else {
					experienceAndServiceTypeVO.setServiceTypeId(serviceTypeIds[1]);
				}
			}
			experienceAndServiceTypeMapper.delete(experienceVO.getExperienceId());
			experienceAndServiceTypeVO.setExperienceId(experienceVO.getExperienceId());
			experienceAndServiceTypeMapper.insert(experienceAndServiceTypeVO);
			experienceDetailMapper.update(experienceDetailVO);
			experienceMapper.update(experienceVO);
			return false;
		}
	}
	
	/**
	 * 批量删除
	 * @param experienceDetailIds
	 */
	public boolean deleteExperienceDetails(String... experienceDetailIds) {
		for (int i=0;i<experienceDetailIds.length;i++){
			ExperienceDetailVO experienceDetailVO=experienceDetailMapper.getById(experienceDetailIds[i]);
			List<ServiceTimeVO> serviceTimeVOList=serviceTimeMapper.getByExperienceId(experienceDetailVO.getExperienceId());
			ExperienceAndServiceTypeVO experienceAndServiceTypeVO=experienceAndServiceTypeMapper.getById(experienceDetailVO.getExperienceId());
			experienceDetailMapper.deleteInBulk(experienceDetailIds[i]);
			experienceMapper.deleteInBulk(experienceDetailVO.getExperienceId());
			for(ServiceTimeVO serviceTimeVO:serviceTimeVOList){
				serviceTimeMapper.deleteInBulk(serviceTimeVO.getServiceTimeId());
			}
			experienceAndServiceTypeMapper.deleteInBulk(experienceAndServiceTypeVO.getServiceTypeId());
		}
		return true;
	}
	/**
	 * 将时间插入PUB_SERVICE_TIME表
	 * @param experienceAllVO  serviceTimeVO
	 * @return
	 */
	public void dataTimeArraySplit(ExperienceAllVO experienceAllVO,ServiceTimeVO serviceTimeVO,ExperienceDetailVO experienceDetailVO) {
		if (experienceAllVO.getStartTime() != null) {
			String[] startTimeArray = experienceAllVO.getStartTimeArray().split(",");
			String[] endTimeArray = experienceAllVO.getEndTimeArray().split(",");
			for (int i = 0; i < startTimeArray.length; i++) {
				serviceTimeVO.setServiceTimeId(generateKey(experienceDetailVO));
				serviceTimeVO.setStartTime(StrToDateTime(startTimeArray[i]));
				serviceTimeVO.setEndTime(StrToDateTime(endTimeArray[i]));
				serviceTimeVO.setServiceDate(StrToDate(startTimeArray[i]));
				//体验服务时间
				serviceTimeVO.setExperienceId(experienceDetailVO.getExperienceId());
				serviceTimeMapper.insert(serviceTimeVO);
			}
		}

	}
	/**
	 * 将时间更新PUB_SERVICE_TIME表
	 * @param experienceAllVO  serviceTimeVO
	 * @return
	 */
	public void dataTimeArraySplit2(ExperienceAllVO experienceAllVO,ServiceTimeVO serviceTimeVO,ExperienceDetailVO experienceDetailVO){
		if (experienceAllVO.getStartTime() != null) {
			String serviceTimeId = experienceAllVO.getServiceTimeIdArray();
			serviceTimeId = serviceTimeId.substring(1);
			String[] startTimeArray = experienceAllVO.getStartTimeArray().split(",");
			String[] endTimeArray = experienceAllVO.getEndTimeArray().split(",");
			String[] serviceTimeIdArray = serviceTimeId.split(",");
			serviceTimeMapper.delete(experienceAllVO.getExperienceId());
			for (int i = 0; i < startTimeArray.length; i++) {
				serviceTimeVO.setStartTime(StrToDateTime(startTimeArray[i]));
				serviceTimeVO.setEndTime(StrToDateTime(endTimeArray[i]));
				serviceTimeVO.setServiceDate(StrToDate(startTimeArray[i]));
				serviceTimeVO.setExperienceId(experienceAllVO.getExperienceId());
				if ("new".equals(serviceTimeIdArray[i])) {
					serviceTimeVO.setServiceTimeId(generateKey(experienceDetailVO));
					serviceTimeMapper.insert(serviceTimeVO);
				} else {
					serviceTimeVO.setServiceTimeId(serviceTimeIdArray[i]);
					serviceTimeMapper.insert(serviceTimeVO);
				}
			}
		}
	}
	/**
	 * 字符串转换成日期
	 * @param str
	 * @return date
	 */
	public static Timestamp StrToDateTime(String str) {
		return Timestamp.valueOf(str);
	}
	/**
	 * 字符串转换成日期
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {
		Date date = new Date();
		//注意format的格式要与日期String的格式相匹配
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(str);
			System.out.println(date.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据experiID获取detail信息
	 * @param experienceId
	 * @return
	 */
	public ExperienceDetailVO getDetailById(String experienceId) {
		return experienceDetailMapper.getDetailById(experienceId);
	}
}
