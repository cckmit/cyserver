package ltd.moore.ctravel.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hdos.platform.common.page.Page;
import ltd.moore.ctravel.customer.mapper.ScoreMapper;
import ltd.moore.ctravel.customer.model.ScoreVO;
import ltd.moore.ctravel.customer.service.ScoreService;
import ltd.moore.ctravel.experience.model.ServiceTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Score控制器
 * @author caicai
 * @version 1.0
 */
@Controller
@RequestMapping("/score")
public class ScoreController {
   
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private ScoreMapper scoreMapper;
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping(value = "/list/{customerId}")
	@ResponseBody
	public String list(@PathVariable("customerId") String customerId,String account,String rateredId,int page,int rows) {
		if(("-全部-").equals(customerId)){
			customerId="";
		}
		Map<String, Object> queryCondition = new HashMap<String,Object>();
		//raterId 被评分人   customerId评分人
		queryCondition.put("rateredId", customerId);
		queryCondition.put("account", account);
		queryCondition.put("customerId", rateredId);
		Page<ScoreVO> scorePage = scoreService.findPage(queryCondition, page, rows);
		JSONObject rst = new JSONObject();
		rst.put("total", scorePage.getTotalElements());
		rst.put("rows", scorePage.getContent());
		return rst.toJSONString();
	}
	
	/**
	 * 主页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init(Model model) {
		return "vel/scoreMain";
	}
	
	/**
	 * 编辑页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String addScore() {
		return "vel/scoreEdit";
	}
	
	/**
	 * 查看详情   
	 */           
	@RequestMapping(value = "/check")
	public String checkOrder(@PathVariable("scoreId") String scoreId,Model model) {
		ScoreVO scoreVO = scoreService.readScoreById(scoreId);
		model.addAttribute("scoreVO", scoreVO);
		return "vel/scoreDetail";
	}
	
	/**
	 * 保存
	 * @param scoreVO
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String saveScore(ScoreVO scoreVO) {
		return  scoreService.saveScore(scoreVO) ? "false" : "success";
	}
	
	/**
	 * 批量删除
	 * @param scoreIds
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String deleteScore(String scoreIds) {
		String[] ids = scoreIds.split(",");
		if(!scoreService.deleteScores(ids)){
			return "false";
		}
		return "success";
	}
	
	/**
	 * 修改页面
	 * @return
	 */
	@RequestMapping(value = "/modify/{scoreId}")
	public String modifyScore(@PathVariable("scoreId") String scoreId,Model model) {
		ScoreVO scoreVO = scoreService.readScoreById(scoreId);
		model.addAttribute("scoreVO", scoreVO);
		return "vel/scoreEdit";
	}
	/**
	 * 获取服务类型combobox
	 *
	 * @return String
	 */
	@RequestMapping(value = "/scoreCombobox/{customerId}", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String scorecombobox(@PathVariable("customerId") String customerId) {
		Map<String, Object> queryCondition = new HashMap<String,Object>();
		queryCondition.put("rateredId",customerId);
		List<ScoreVO> scoreVOList = scoreMapper.list1(queryCondition);
		ScoreVO scoreVO = new ScoreVO();
		scoreVO.setCustomerId("-全部-");
		scoreVO.setScoreId("");
		scoreVOList.add(0, scoreVO);
		return JSONObject.toJSONString(scoreVOList);
	}
}
