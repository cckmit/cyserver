package ltd.moore.ctravel.comment.controller;

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.common.page.Page;
import ltd.moore.ctravel.comment.model.CommentMessageVO;
import ltd.moore.ctravel.comment.service.CommentMessageService;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.customer.service.CustomerAccountService;
import ltd.moore.ctravel.experience.mapper.ExperienceMapper;
import ltd.moore.ctravel.experience.model.ExperienceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/7/3 0003.
 */
@Controller
@RequestMapping("/api/comment")
public class CommentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CommentMessageService commentMessageService;
    @Autowired
    private ExperienceMapper experienceMapper;
    /**
     * 增加评论
     *
     * @return
     */
    @RequestMapping(value = "/comments", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  addcomment(@RequestParam String customerId, @RequestParam String experienceId, @RequestParam String commentContent,@RequestParam(required = false) String remark, @RequestParam String token) {
        CustomerAccountVO customerAccountVO =customerAccountService.readCustomerAccountById(customerId);
        ExperienceVO experienceVO = experienceMapper.getById(experienceId);
        if (experienceVO==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        if(commentContent==null||"".equals(commentContent) ||commentContent.getBytes().length>250){
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode",ResultCodeInfo.E200001);
            result.put("errorMsg","评论内容"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }
        if(remark!=null && remark.getBytes().length>250){
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode",ResultCodeInfo.E200001);
            result.put("errorMsg","备注"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }
        try {
            CommentMessageVO commentVO =new CommentMessageVO();
            commentVO.setCustomerId(customerId);
            commentVO.setExperienceId(experienceId);
            commentVO.setAccount(customerAccountVO.getAccount());
            commentVO.setCommentContent(commentContent);
            commentMessageService.saveCommentMessage(commentVO);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
        return ResultGen(ResultCodeInfo.E000000);
    }
    /**
     * 删除评论
     *
     * @return
     */
    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.DELETE, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  delcomment(@PathVariable("commentId") String commentId , @RequestParam String customerId, @RequestParam String token) {
        CommentMessageVO  commentVO=commentMessageService.readCommentMessageById(commentId);
        if(commentVO==null){
            return ResultGen(ResultCodeInfo.E200017);
        }
        if(!customerId.equals(commentVO.getCustomerId())){
            return ResultGen(ResultCodeInfo.E200010);
        }
        try {
            commentMessageService.deleteCommentMessages(commentId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
        return ResultGen(ResultCodeInfo.E000000);
    }
    /**
     * 修改评论
     *
     * @return
     */
    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.PUT, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  editcomment(@PathVariable("commentId") String commentId ,@RequestParam String customerId,@RequestParam (required = false)String commentContent,@RequestParam (required = false)String remark, @RequestParam String token) {
        CommentMessageVO  commentVO=commentMessageService.readCommentMessageById(commentId);
        if(commentVO==null){
            return ResultGen(ResultCodeInfo.E200017);
        }
        if(!customerId.equals(commentVO.getCustomerId())){
            return ResultGen(ResultCodeInfo.E200010);
        }
        if(commentContent!=null&&commentContent.getBytes().length>250){
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode",ResultCodeInfo.E200001);
            result.put("errorMsg","评论内容"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }
        if(remark!=null&&remark.getBytes().length>250){
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode",ResultCodeInfo.E200001);
            result.put("errorMsg","备注"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }
        try {
            commentVO.setCommentContent(commentContent);
            commentVO.setRemark(remark);
            commentMessageService.saveCommentMessage(commentVO);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
        return ResultGen(ResultCodeInfo.E000000);
    }
    /**
     * 查询评论
     *
     * @return
     */
    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  queryComment(@RequestParam String customerId, @RequestParam String commentId, @RequestParam String token) {
        try {
                CommentMessageVO commentVO = commentMessageService.readCommentMessageById(commentId);
                if (commentVO == null) {
                    return ResultGen(ResultCodeInfo.E200017);
                }
                if (!customerId.equals(commentVO.getCustomerId())) {
                    return ResultGen(ResultCodeInfo.E200010);
                }
                JSONObject rst = new JSONObject();
                rst.put("resultCode", ResultCodeInfo.E000000);
                rst.put("commentDetail", commentVO);
                return rst.toJSONString();
            } catch (Exception e) {
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
    public String conditionQuerycomment(@RequestParam String customerId,@RequestParam(required = false) String experienceId,@RequestParam int page,@RequestParam int rows, @RequestParam String token){
        Map<String, Object> queryCondition = new HashMap<String,Object>();
        queryCondition.put("experienceId", experienceId);
        Page<CommentMessageVO> commentDetailPage = commentMessageService.findPage(queryCondition, page, rows);
        JSONObject rst = new JSONObject();
        rst.put("resultCode", ResultCodeInfo.E000000);
        rst.put("total", commentDetailPage.getTotalElements());
        rst.put("experienceDetail", commentDetailPage.getContent());
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
