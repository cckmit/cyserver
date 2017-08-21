package ltd.moore.ctravel.score.controller;

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.common.page.Page;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.customer.model.ScoreVO;
import ltd.moore.ctravel.customer.service.CustomerAccountService;
import ltd.moore.ctravel.customer.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/7/3 0003.
 */
@Controller
@EnableSwagger2
@RequestMapping("/api/score")
public class ScoreController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private ScoreService scoreService;
    /**
     * 增加评分
     *
     * @return
     */
    @RequestMapping(value = "/scores", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  addScore(@RequestParam String customerId, @RequestParam String rateredId, @RequestParam String score, @RequestParam String token) {
        CustomerAccountVO customerAccountVO =customerAccountService.readCustomerAccountById(rateredId);
        CustomerAccountVO customerAccountVO2=customerAccountService.readCustomerAccountById(customerId);
        if(customerAccountVO==null){
            return ResultGen(ResultCodeInfo.E200014);
        }
        if(score==null||"".equals(score)){
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode",ResultCodeInfo.E200001);
            result.put("errorMsg",ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }
        if(!isBigDecimal(score) || Integer.parseInt(score)>100){
            return ResultGen(ResultCodeInfo.E200015);
        }
        try {
            ScoreVO scoreVO =new ScoreVO();
            scoreVO.setCustomerId(customerId);
            scoreVO.setAccount(customerAccountVO2.getAccount());
            scoreVO.setRateredId(rateredId);
            scoreVO.setScore(score);
            scoreService.saveScore(scoreVO) ;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
        return ResultGen(ResultCodeInfo.E000000);
    }
    /**
     * 删除评分
     *
     * @return
     */
    @RequestMapping(value = "/scores/{scoreId}", method = RequestMethod.DELETE, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  delScore(@PathVariable("scoreId")String scoreId,@RequestParam String customerId, @RequestParam String token) {
        ScoreVO  scoreVO=scoreService.readScoreById(scoreId);
        if(scoreVO==null){
            return ResultGen(ResultCodeInfo.E200016);
        }
        if(!customerId.equals(scoreVO.getCustomerId())){
            return ResultGen(ResultCodeInfo.E200010);
        }
        try {
            scoreService.deleteScores(scoreId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
        return ResultGen(ResultCodeInfo.E000000);
    }
    /**
     * 修改评分
     *
     * @return
     */
    @RequestMapping(value = "/scores/{scoreId}", method = RequestMethod.PUT, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  editScore(@PathVariable("scoreId") String scoreId ,@RequestParam String customerId,@RequestParam (required = false)String score, @RequestParam String token) {
        ScoreVO  scoreVO=scoreService.readScoreById(scoreId);
        if(scoreVO==null){
            return ResultGen(ResultCodeInfo.E200016);
        }
        if(!customerId.equals(scoreVO.getCustomerId())){
            return ResultGen(ResultCodeInfo.E200010);
        }
        if(!isBigDecimal(score) || Integer.parseInt(score)>100){
            return ResultGen(ResultCodeInfo.E200015);
        }
        try {
            scoreVO.setScore(score);
            scoreService.saveScore(scoreVO) ;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
        return ResultGen(ResultCodeInfo.E000000);
    }
    /**
     * 查询评分
     *
     * @return
     */
    @RequestMapping(value = "/scores/{scoreId}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  queryScore(@PathVariable("scoreId") String scoreId,@RequestParam String customerId, @RequestParam String token) {
        try {
        ScoreVO  scoreVO=scoreService.readScoreById(scoreId);
        if(scoreVO==null){
            return ResultGen(ResultCodeInfo.E200016);
        }
            if(!customerId.equals(scoreVO.getCustomerId())){
                return ResultGen(ResultCodeInfo.E200010);
            }
            JSONObject rst = new JSONObject();
            rst.put("resultCode", ResultCodeInfo.E000000);
            rst.put("scoreDetail",scoreVO );
            return rst.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
    }
    /**
     * 条件检索评分
     *
     * @return
     */
    @RequestMapping(value = "/conditionQuery", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String conditionQueryScore(@RequestParam String customerId,@RequestParam(required = false) String rateredId,@RequestParam int page,@RequestParam int rows, @RequestParam String token){
        Map<String, Object> queryCondition = new HashMap<String,Object>();
        queryCondition.put("rateredId", rateredId);
        Page<ScoreVO> scoreDetailPage = scoreService.findPage(queryCondition, page, rows);
        JSONObject rst = new JSONObject();
        rst.put("resultCode", ResultCodeInfo.E000000);
        rst.put("total", scoreDetailPage.getTotalElements());
        rst.put("experienceDetail", scoreDetailPage.getContent());
        return rst.toJSONString();
    }
    /**
     * 生成返回信息
     * @param e000000
     * @return
     */
    private String ResultGen(ResultCodeInfo e000000) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resultCode",e000000.getResultCode());
        result.put("errorMsg",e000000.getErrorMsg());
        return JSONObject.toJSONString(result);
    }
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str.trim());
        return match.matches();
    }
    public static boolean isBigDecimal(String str) {
        Matcher match =null;
        if(isNumber(str)==true){
            Pattern pattern = Pattern.compile("[0-9]*");
            match = pattern.matcher(str.trim());
        }else{
            if(str.trim().indexOf(".")==-1){
                Pattern pattern = Pattern.compile("^[+-]?[0-9]*");
                match = pattern.matcher(str.trim());
            }else{
                Pattern pattern = Pattern.compile("/\\d{1,8}\\.{0,1}\\d{0,2}/");
                match = pattern.matcher(str.trim());
            }
        }
        return match.matches();
    }
}
