package ltd.moore.ctravel.comment;

import java.util.HashMap;
import java.util.Map;

import com.hdos.platform.common.page.Page;
import ltd.moore.ctravel.comment.model.CommentMessageVO;
import ltd.moore.ctravel.comment.service.CommentMessageService;
import ltd.moore.ctravel.experience.model.ExperienceAllVO;
import ltd.moore.ctravel.experience.service.ExperienceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * CommentMessage控制器
 * @author caicai
 * @version 1.0
 */
@Controller
@RequestMapping("/commentMessage")
public class CommentMessageController {
	@Autowired
	private CommentMessageService commentMessageService;
	@Autowired
	private ExperienceDetailService experienceDetailService;
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping(value = "/list/{experienceDetailId}")
	@ResponseBody
	public String list(@PathVariable("experienceDetailId") String experienceDetailId,String account,int page,int rows) {
		ExperienceAllVO experienceAllVO=experienceDetailService.readExperienceDetailById(new ExperienceAllVO(),experienceDetailId,null);
		Map<String, Object> queryCondition = new HashMap<String,Object>();
		queryCondition.put("experienceId", experienceAllVO.getExperienceId());
		queryCondition.put("account",account);
		Page<CommentMessageVO> commentMessagePage = commentMessageService.findPage(queryCondition, page, rows);
		JSONObject rst = new JSONObject();
		rst.put("total", commentMessagePage.getTotalElements());
		rst.put("rows", commentMessagePage.getContent());
		return rst.toJSONString();
	}
	
	/**
	 * 主页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init/{experienceDetailId}")
	public String init(@PathVariable("experienceDetailId") String experienceDetailId,Model model) {
		experienceDetailService.readExperienceDetailById(new ExperienceAllVO(),experienceDetailId,model);
		return "comment/commentMessageMain";
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	@RequestMapping(value = "/add/{experienceDetailId}")
	public String addCommentMessage(@PathVariable("experienceDetailId") String experienceDetailId,Model model) {
		experienceDetailService.readExperienceDetailById(new ExperienceAllVO(),experienceDetailId,model);
		return "comment/commentMessageAdd";
	}

	/**
	 * 保存
	 * @param commentMessageVO
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String saveCommentMessage(CommentMessageVO commentMessageVO) {
		return  commentMessageService.saveCommentMessage(commentMessageVO) ? "false" : "success";
	}
	
	/**
	 * 批量删除
	 * @param commentIds
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String deleteCommentMessage(String commentIds) {
		String[] ids = commentIds.split(",");
		if(!commentMessageService.deleteCommentMessages(ids)){
			return "false";
		}
		return "success";
	}
	
	/**
	 * 修改页面
	 * @return
	 */
	@RequestMapping(value = "/modify/{experienceDetailId}/{commentId}")
	public String modifyCommentMessage(@PathVariable("experienceDetailId") String experienceDetailId,@PathVariable("commentId") String commentId,Model model) {
		experienceDetailService.readExperienceDetailById(new ExperienceAllVO(),experienceDetailId,model);
		CommentMessageVO commentMessageVO = commentMessageService.readCommentMessageById(commentId);
		model.addAttribute("commentMessageVO", commentMessageVO);
		return "comment/commentMessageDetail";
	}
}
