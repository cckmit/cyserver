package ltd.moore.ctravel.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hdos.platform.common.page.Page;
import com.hdos.platform.common.page.PageImpl;
import com.hdos.platform.common.util.StringUtils;
import com.hdos.platform.core.base.BaseService;
import ltd.moore.ctravel.comment.mapper.CommentMessageMapper;
import ltd.moore.ctravel.comment.model.CommentMessageVO;
import ltd.moore.ctravel.experience.mapper.ExperienceMapper;
import ltd.moore.ctravel.experience.model.ExperienceAllVO;
import ltd.moore.ctravel.experience.service.ExperienceDetailService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class CommentMessageService extends BaseService<CommentMessageVO> {
	
	@Autowired
	private CommentMessageMapper commentMessageMapper;
	@Autowired
	private ExperienceDetailService experienceDetailService;
	@Autowired
	private ExperienceMapper experienceMapper;
	private static final Logger logger = LoggerFactory.getLogger(CommentMessageService.class);
	
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
	public Page<CommentMessageVO> findPage(Map<String, Object> condition, int pageNumber, int pageSize) {
		int total = commentMessageMapper.count(condition);
		RowBounds rowBounds = new RowBounds((pageNumber - 1) * pageSize, pageSize);
		List<CommentMessageVO> content = total > 0 ? commentMessageMapper.list(condition, rowBounds) : new ArrayList<CommentMessageVO>(0);
		return new PageImpl<CommentMessageVO>(content, pageNumber, pageSize, total);
	}
	
	/**
	 * 按id查询
	 * @return
	 */
	public CommentMessageVO readCommentMessageById(String commentMessageId) {
	    return  commentMessageMapper.getById(commentMessageId);
	}
	
	/**
	 * 保存
	 * @param commentMessageVO
	 */
	public boolean saveCommentMessage(CommentMessageVO commentMessageVO) {
		ExperienceAllVO experienceAllVO=experienceDetailService.readExperienceDetailById(new ExperienceAllVO(),experienceMapper.getById(commentMessageVO.getExperienceId()).getExperienceDetailId(),null);
		commentMessageVO.setCustomerId(experienceAllVO.getCustomerId());
		commentMessageVO.setExperienceId(experienceAllVO.getExperienceId());
		if (null == commentMessageVO) {
			logger.error("保存ERROR：" + commentMessageVO);
			throw new IllegalArgumentException();
		}

		if (StringUtils.isEmpty(commentMessageVO.getCommentId())) {// 新增
		//自定义设置主码
		     commentMessageVO.setCommentId(generateKey(commentMessageVO));
			commentMessageMapper.insert(commentMessageVO);
			return false;
		} else {// 更新
			commentMessageMapper.update(commentMessageVO);
			return false;
		}
	}
	
	/**
	 * 批量删除
	 * @param commentMessageIds
	 */
	public boolean deleteCommentMessages(String... commentMessageIds) {
		commentMessageMapper.deleteInBulk(commentMessageIds);
		return true;
	}
}
