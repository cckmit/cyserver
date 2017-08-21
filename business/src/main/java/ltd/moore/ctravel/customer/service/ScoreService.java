package ltd.moore.ctravel.customer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hdos.platform.common.page.Page;
import com.hdos.platform.common.page.PageImpl;
import com.hdos.platform.common.util.StringUtils;
import com.hdos.platform.core.base.BaseService;
import ltd.moore.ctravel.customer.mapper.ScoreMapper;
import ltd.moore.ctravel.customer.model.ScoreVO;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ScoreService extends BaseService<ScoreVO> {
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ScoreService.class);
	
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
	public Page<ScoreVO> findPage(Map<String, Object> condition, int pageNumber, int pageSize) {
		int total = scoreMapper.count(condition);
		RowBounds rowBounds = new RowBounds((pageNumber - 1) * pageSize, pageSize);
		List<ScoreVO> content = total > 0 ? scoreMapper.list(condition, rowBounds) : new ArrayList<ScoreVO>(0);
		return new PageImpl<ScoreVO>(content, pageNumber, pageSize, total);
	}
	
	/**
	 * 按id查询
	 * @return
	 */
	public ScoreVO readScoreById(String scoreId) {
	    return  scoreMapper.getById(scoreId);
	}
	
	/**
	 * 保存
	 * @param scoreVO
	 */
	public boolean saveScore(ScoreVO scoreVO) {
		if (null == scoreVO) {
			logger.error("保存ERROR：" + scoreVO);
			throw new IllegalArgumentException();
		}

		if (StringUtils.isEmpty(scoreVO.getScoreId())) {// 新增
		//自定义设置主码
		     scoreVO.setScoreId(generateKey(scoreVO));
			scoreMapper.insert(scoreVO);
			return false;
		} else {// 更新
			scoreMapper.update(scoreVO);
			return false;
		}
	}
	
	/**
	 * 批量删除
	 * @param scoreIds
	 */
	public boolean deleteScores(String... scoreIds) {
		scoreMapper.deleteInBulk(scoreIds);
		return true;
	}
}
