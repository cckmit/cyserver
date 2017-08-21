package ltd.moore.ctravel.experience.controller;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.base.fileuploader.model.FileVO;
import com.hdos.platform.base.fileuploader.service.FileUploaderService;
import com.hdos.platform.common.page.Page;
import com.hdos.platform.common.util.CacheUtils;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.customer.model.CustomerDetailVO;
import ltd.moore.ctravel.customer.service.CustomerAccountService;
import ltd.moore.ctravel.customer.service.CustomerDetailService;
import ltd.moore.ctravel.experience.mapper.*;
import ltd.moore.ctravel.experience.model.*;
import ltd.moore.ctravel.experience.service.ExperienceDetailService;
import ltd.moore.ctravel.experience.service.ServiceTimeService;
import ltd.moore.ctravel.login.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@EnableSwagger2
@RequestMapping("/api/experience")
public class ExperienceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExperienceDetailService experienceDetailService;
    @Autowired
    private ExperienceMapper experienceMapper;
    @Autowired
    private ServiceTypeMapper serviceTypeMapper;
    @Autowired
    private FileUploaderService fileUploaderService;
    @Autowired
    private ServiceTimeService serviceTimeService;
    @Autowired
    private CustomerDetailService customerDetailService;
    /**
     * 增加体验管理
     *
     * @return
     */
    @RequestMapping(value = "/experiences", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  addExperience(@ModelAttribute AddExperienceAllVO addExperienceAllVO, @RequestParam String customerId,@RequestParam String token) {
//        if(!verrifyUser(customerId, token)){
//            return ResultGen(ResultCodeInfo.E100010);
//        }
        ExperienceAllVO experienceAllVO = changeVO(addExperienceAllVO);
        ServiceTypeVO serviceTypeVO=serviceTypeMapper.getByServiceName(experienceAllVO.getServiceName());
      //  DestinationVO destinationVO=destinationMapper.getByDestination(experienceAllVO.getDestination());
        if (serviceTypeVO==null){
            return ResultGen(ResultCodeInfo.E200007);
        }
        experienceAllVO.setServiceTypeId(serviceTypeVO.getServiceTypeId());

//        if (destinationVO==null){
//            return ResultGen(ResultCodeInfo.E200008);
//        }
        try {
            if ("success".equals(addValidate(experienceAllVO))) {
                ExperienceDetailVO experienceDetailVO = changeVO1(addExperienceAllVO);
                ExperienceVO experienceVO = changeVO2(addExperienceAllVO);
                experienceVO.setCustomerId(customerId);
                String flag = experienceDetailService.saveExperienceDetail(experienceAllVO,experienceDetailVO, experienceVO) ? "false" : "success";
                if (("success").equals(flag)) {
                    return ResultGen(ResultCodeInfo.E000000);
                }
                return ResultGen(ResultCodeInfo.E200001);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
        return addValidate(experienceAllVO);
    }
    /**
     * 发布体验
     *
     * @return
     */
    @RequestMapping(value = "/publish/{experienceId}", method = RequestMethod.PUT, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String publishExperience(@PathVariable("experienceId")String experienceId,@RequestParam String customerId,@RequestParam String token) {
        CustomerDetailVO customerDetailVO=customerDetailService.readCustomerDetailById(customerId);
        if(customerDetailVO.getIdcardCheckStatus()!=9){
            return ResultGen(ResultCodeInfo.E200019);
        }
        List<String> experienceIdList = experienceMapper.getExperienceByCustomerId(customerId);
        System.out.println(experienceIdList);
        ExperienceVO experienceVO = experienceMapper.getById(experienceId);
        if (experienceVO==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        if(!experienceIdList.contains(experienceId)){
            return ResultGen(ResultCodeInfo.E200010);
        }
        if(experienceVO.getEnabled()==1){
            return ResultGen(ResultCodeInfo.E200011);
        }
        experienceVO.setEnabled(1);
        experienceMapper.update(experienceVO);
        return ResultGen(ResultCodeInfo.E000000);
    }
    /**
     * 取消发布体验
     *
     * @return
     */
    @RequestMapping(value = "/cancel/{experienceId}", method = RequestMethod.PUT, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String cancelExperience(@PathVariable("experienceId")String experienceId,@RequestParam String customerId,@RequestParam String token) {
        List<String> experienceIdList = experienceMapper.getExperienceByCustomerId(customerId);
        ExperienceVO experienceVO = experienceMapper.getById(experienceId);
        if (experienceVO==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        if(!experienceIdList.contains(experienceId)){
            return ResultGen(ResultCodeInfo.E200010);
        }
        if(experienceVO.getEnabled()!=1){
            return ResultGen(ResultCodeInfo.E200012);
        }
        experienceVO.setEnabled(2);
        experienceMapper.update(experienceVO);
        return ResultGen(ResultCodeInfo.E000000);
    }
    /**
     * 删除体验
     *
     * @return
     */
    @RequestMapping(value = "/experiences/{experienceId}", method = RequestMethod.DELETE, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String delExperience(@PathVariable("experienceId")String experienceId,@RequestParam String customerId,@RequestParam String token) {
        //        if(!verrifyUser(customerId, token)){
        //            return ResultGen(ResultCodeInfo.E100010);
        //        }
        List<String> experienceIdList = experienceMapper.getExperienceByCustomerId(customerId);
        ExperienceVO experienceVO = experienceMapper.getById(experienceId);
        if (experienceVO==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        if(!experienceIdList.contains(experienceId)){
            return ResultGen(ResultCodeInfo.E200010);
        }
        if (!experienceDetailService.deleteExperienceDetails(experienceVO.getExperienceDetailId())) {
            return ResultGen(ResultCodeInfo.E200004);
        }
        return ResultGen(ResultCodeInfo.E000000);
    }
    /**
     * 修改体验
     *
     * @return
     */
    @RequestMapping(value = "/experiences/{experienceId}", method = RequestMethod.PUT, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String modExperience(@PathVariable("experienceId") String experienceId ,@ModelAttribute  AddExperienceAllVO addExperienceAllVO,@RequestParam String customerId,@RequestParam String token){
        //        if(!verrifyUser(customerId, token)){
        //            return ResultGen(ResultCodeInfo.E100010);
        //        }
        ExperienceVO experienceVO1 = experienceMapper.getById(experienceId);
        List<String> experienceIdList = experienceMapper.getExperienceByCustomerId(customerId);
        if (experienceVO1==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        if(!experienceIdList.contains(experienceId)){
            return ResultGen(ResultCodeInfo.E200010);
        }
        if(experienceVO1==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        ExperienceAllVO experienceAllVO = changeVO(addExperienceAllVO);
        ExperienceDetailVO experienceDetailVO = changeVO1(addExperienceAllVO);
        ExperienceVO experienceVO = changeVO2(addExperienceAllVO);
        ExperienceAllVO experienceAllVO2=experienceDetailService.readExperienceDetailById(experienceAllVO,experienceVO1.getExperienceDetailId(),null);
        if(!"success".equals(modifyValidate(experienceAllVO))){
            return modifyValidate(experienceAllVO);
        }
        //总的VO
        experienceAllVO.setExperienceDetailId(experienceAllVO2.getExperienceDetailId());
        experienceAllVO.setExperienceId(experienceAllVO2.getExperienceId());
        experienceAllVO.setServiceTypeId(experienceAllVO2.getServiceTypeId());
        //体验服务明细表
        experienceDetailVO.setExperienceDetailId(experienceAllVO2.getExperienceDetailId());
        experienceDetailVO.setExperienceId(experienceAllVO2.getExperienceId());
        //体验服务信息表
        experienceVO.setExperienceDetailId(experienceAllVO2.getExperienceDetailId());
        experienceVO.setExperienceId(experienceAllVO2.getExperienceId());
        Map<String, Object> result = new HashMap<>();
        String flag = experienceDetailService.saveExperienceDetail(experienceAllVO,experienceDetailVO,experienceVO) ? "false" : "success";
        if(("success").equals(flag)){
            return ResultGen(ResultCodeInfo.E000000);
        }
        return ResultGen(ResultCodeInfo.E200002);
    }
    /**
     * 查询体验详情
     *
     * @return
     */
    @RequestMapping(value = "/experiences/{experienceId}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String queryExperience(@PathVariable("experienceId") String experienceId,@RequestParam String customerId,@RequestParam String token){
        //        if(!verrifyUser(customerId, token)){
        //            return ResultGen(ResultCodeInfo.E100010);
        //        }
        ExperienceAllVO experienceAllVO = new ExperienceAllVO();
        ExperienceVO experienceVO = experienceMapper.getById(experienceId);
        List<String> experienceIdList = experienceMapper.getExperienceByCustomerId(customerId);
        if (experienceVO==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        if(!experienceIdList.contains(experienceId)){
            return ResultGen(ResultCodeInfo.E200010);
        }
        try {
            Map<String, Object> queryCondition = new HashMap<String,Object>();
            queryCondition.put("experienceId", experienceId);
            Page<ExperienceAllVO> experienceDetailPage = experienceDetailService.findPage2(queryCondition, 1, 1);
            JSONObject rst = new JSONObject();
            rst.put("resultCode", ResultCodeInfo.E000000);
            rst.put("total", experienceDetailPage.getTotalElements());
            rst.put("experienceDetail", experienceDetailPage.getContent());
            return rst.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
    }

    /**
     * 条件检索体验
     *
     * @return
     */
    @RequestMapping(value = "/conditionQuery", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String conditionQuerryExperience(@RequestParam String customerId,@RequestParam String token,@RequestParam (required = false)String enabled,@RequestParam (required = false)String serviceName,@RequestParam int page,@RequestParam int rows){
        //        if(!verrifyUser(customerId, token)){
        //            return ResultGen(ResultCodeInfo.E100010);
        //        }
        Map<String, Object> queryCondition = new HashMap<String,Object>();
        if(!(serviceName==null &&("").equals(serviceName))) {
            //ServiceTypeVO serviceTypeVO = serviceTypeMapper.getByServiceName(serviceName);
            //list = experienceAndServiceTypeMapper.getListById(serviceTypeVO.getServiceTypeId());
            queryCondition.put("serviceName", serviceName);
        }
        if(!isNumber(enabled)){
            return ResultGen(ResultCodeInfo.E200018);
        }
        queryCondition.put("enabled", enabled);
        Page<ExperienceAllVO> experienceDetailPage = experienceDetailService.findPage2(queryCondition, page, rows);
        JSONObject rst = new JSONObject();
        rst.put("resultCode", ResultCodeInfo.E000000);
        rst.put("total", experienceDetailPage.getTotalElements());
        rst.put("experienceDetail", experienceDetailPage.getContent());
        return rst.toJSONString();
    }
    /**
     * 增加服务时间
     *
     * @return
     */
    @RequestMapping(value = "/serviceTime/{experienceId}", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String addServiceTime(@PathVariable("experienceId") String experienceId,@RequestParam String customerId,@RequestParam String token,@RequestParam String  serviceDate,@RequestParam String startTime ,@RequestParam String endTime){
        //        if(!verrifyUser(customerId, token)){
        //            return ResultGen(ResultCodeInfo.E100010);
        //        }
        ExperienceVO experienceVO = experienceMapper.getById(experienceId);
        List<String> experienceIdList = experienceMapper.getExperienceByCustomerId(customerId);
        if (experienceVO==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        if(!experienceIdList.contains(experienceId)){
            return ResultGen(ResultCodeInfo.E200010);
        }
        try {
            ServiceTimeVO serviceTimeVO = new ServiceTimeVO();
            serviceTimeVO.setExperienceId(experienceId);
            SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
            serviceTimeVO.setServiceDate(sdf1.parse(serviceDate));
            serviceTimeVO.setStartTime(Timestamp.valueOf(startTime));
            serviceTimeVO.setEndTime(Timestamp.valueOf(endTime));
            serviceTimeService.saveServiceTime(serviceTimeVO);
            return ResultGen(ResultCodeInfo.E000000);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E200013);
        }
    }

    /**
     * 删除服务时间
     *
     * @return
     */
    @RequestMapping(value = "/serviceTime/{experienceId}/{serviceTimeId}", method = RequestMethod.DELETE, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String delServiceTime(@PathVariable("experienceId") String experienceId ,@RequestParam String customerId,@RequestParam  String token ,@PathVariable("serviceTimeId") String serviceTimeId){
        //        if(!verrifyUser(customerId, token)){
        //            return ResultGen(ResultCodeInfo.E100010);
        //        }
        ExperienceVO experienceVO = experienceMapper.getById(experienceId);
        List<String> experienceIdList = experienceMapper.getExperienceByCustomerId(customerId);
        if (experienceVO==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        if(!experienceIdList.contains(experienceId)){
            return ResultGen(ResultCodeInfo.E200010);
        }
        try {
            String [] serviceTimeIdArray =serviceTimeId.split(",");
            for(int i=0;i<serviceTimeIdArray.length;i++){
                ServiceTimeVO  serviceTimeVO=serviceTimeService.readServiceTimeById(serviceTimeIdArray[i]);
                if(serviceTimeVO==null){
                    return ResultGen(ResultCodeInfo.E200010);
                }
            }
            serviceTimeService.deleteServiceTimes(serviceTimeIdArray);
            return ResultGen(ResultCodeInfo.E000000);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
    }
    /**
     * 编辑服务时间
     *
     * @return
     */
    @RequestMapping(value = "/serviceTime/{experienceId}/{serviceTimeId}", method = RequestMethod.PUT, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String editServiceTime(@PathVariable("experienceId") String experienceId,@PathVariable("serviceTimeId") String serviceTimeId,@RequestParam String customerId,@RequestParam String token,@RequestParam String  serviceDate,@RequestParam String startTime ,@RequestParam String endTime){
        //        if(!verrifyUser(customerId, token)){
        //            return ResultGen(ResultCodeInfo.E100010);
        //        }
        ExperienceVO experienceVO = experienceMapper.getById(experienceId);
        List<String> experienceIdList = experienceMapper.getExperienceByCustomerId(customerId);
        if (experienceVO==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        if(!experienceIdList.contains(experienceId)){
            return ResultGen(ResultCodeInfo.E200010);
        }
        try {
            ServiceTimeVO serviceTimeVO = new ServiceTimeVO();
            serviceTimeVO.setServiceTimeId(serviceTimeId);
            serviceTimeVO.setExperienceId(experienceId);
            SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
            serviceTimeVO.setServiceDate(sdf1.parse(serviceDate));
            serviceTimeVO.setStartTime(Timestamp.valueOf(startTime));
            serviceTimeVO.setEndTime(Timestamp.valueOf(endTime));
            serviceTimeService.saveServiceTime(serviceTimeVO);
            return ResultGen(ResultCodeInfo.E000000);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
    }

    /**
     * 查询服务时间
     *
     * @return
     */
    @RequestMapping(value = "/serviceTime/{experienceId}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String queryServiceTime(@PathVariable("experienceId") String experienceId,@RequestParam String customerId,@RequestParam String token){
        //        if(!verrifyUser(customerId, token)){
        //            return ResultGen(ResultCodeInfo.E100010);
        //        }
        ExperienceVO experienceVO = experienceMapper.getById(experienceId);
        List<String> experienceIdList = experienceMapper.getExperienceByCustomerId(customerId);
        if (experienceVO==null){
            return ResultGen(ResultCodeInfo.E200005);
        }
        if(!experienceIdList.contains(experienceId)){
            return ResultGen(ResultCodeInfo.E200010);
        }
        try {
           List<ServiceTimeVO> list = serviceTimeService.getByExperienceId(experienceId);
            Map<String, Object> result = new HashMap<>();
            result.put("resultCode", ResultCodeInfo.E000000);
            result.put("serviceTimeDetail",list);
            return JSONObject.toJSONString(result);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultGen(ResultCodeInfo.E999999);
        }
    }





    /**
     * 增加体验管理字段验证
     *
     * @param experienceAllVO
     */
    @ResponseBody
    public String addValidate(ExperienceAllVO experienceAllVO){
        Map<String, Object> result = new HashMap<>();
        if(experienceAllVO.getTitle()==null || ("").equals(experienceAllVO.getTitle()) || experienceAllVO.getTitle().getBytes().length >100){
            result.put("resultCode", ResultCodeInfo.E200001);
            result.put("errorMsg", "标题"+ ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }
        if(experienceAllVO.getPrice()!=null)
        if(experienceAllVO.getPrice().length() >10 &&isBigDecimal(experienceAllVO.getPrice())) {
            return ResultGen(ResultCodeInfo.E200006);
        }

        if (experienceAllVO.getImageId()!=null) {
            FileVO fileVO = fileUploaderService.readById(experienceAllVO.getImageId());
            if (fileVO == null) {
                return ResultGen(ResultCodeInfo.E200007);
            }
        }
       /* if(experienceAllVO.getContentDescription()==null || experienceAllVO.getContentDescription().getBytes().length >250){
            result.put("resultCode", ResultCodeInfo.E200001);
            result.put("errorMsg", "内容描述"+ ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getDestination()==null || experienceAllVO.getDestination().getBytes().length >250){
            result.put("resultCode", ResultCodeInfo.E200001);
            result.put("errorMsg","目的地"+  ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getRendezvous()==null || experienceAllVO.getRendezvous().getBytes().length >250){
            result.put("resultCode", ResultCodeInfo.E200001);
            result.put("errorMsg", "集合地"+ ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getContentDetails()==null || experienceAllVO.getContentDetails().getBytes().length >1000){
            result.put("resultCode", ResultCodeInfo.E200001);
            result.put("errorMsg", "体验内容明细"+ ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getComment()==null || experienceAllVO.getComment().getBytes().length >1000){
            result.put("resultCode", ResultCodeInfo.E200001);
            result.put("errorMsg", "备注"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getRequirement()==null || experienceAllVO.getRequirement().getBytes().length >1000){
            result.put("resultCode", ResultCodeInfo.E200001);
            result.put("errorMsg", "要求"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
            return JSONObject.toJSONString(result);
        }*/
//        Integer a2 = experienceAllVO.getSortNo();//转换为包装类Integer
//        if(experienceAllVO.getSortNo()==0 || a2.toString().length() >8){
//            result.put("resultCode", ResultCodeInfo.E200001);
//            result.put("errorMsg", "排序"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
//            return JSONObject.toJSONString(result);
//        }

//        Integer a4 =experienceAllVO.getPrice();//转换为包装类Integer
//        if(experienceAllVO.getEnabled()==0 || a4.toString().length() >2){
//            result.put("resultCode", ResultCodeInfo.E200001);
//            result.put("errorMsg", "激活标识符"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
//            return JSONObject.toJSONString(result);
//        }
        return "success";
    }
    /**
     * 修改体验管理字段验证
     *
     * @param experienceAllVO
     */
    @ResponseBody
    public String modifyValidate(ExperienceAllVO experienceAllVO){
        Map<String, Object> result = new HashMap<>();
        if(experienceAllVO.getTitle()!=null&&experienceAllVO.getTitle().getBytes().length >100){
            result.put("resultCode", ResultCodeInfo.E200002);
            result.put("errorMsg","标题"+ ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getSubtitle()!=null&&experienceAllVO.getSubtitle().getBytes().length >250){
            result.put("resultCode", ResultCodeInfo.E200002);
            result.put("errorMsg", "副标题"+ ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getContentDescription()!=null&&experienceAllVO.getContentDescription().getBytes().length >250){
            result.put("resultCode", ResultCodeInfo.E200002);
            result.put("errorMsg", "内容描述"+ ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getDestination()!=null&&experienceAllVO.getDestination().getBytes().length >250){
            result.put("resultCode", ResultCodeInfo.E200002);
            result.put("errorMsg","目的地"+  ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
            return JSONObject.toJSONString(result);
        }if( experienceAllVO.getRendezvous()!=null&&experienceAllVO.getRendezvous().getBytes().length >250){
            result.put("resultCode", ResultCodeInfo.E200002);
            result.put("errorMsg", "集合地"+ ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getContentDetails()!=null&&experienceAllVO.getContentDetails().getBytes().length >1000){
            result.put("resultCode", ResultCodeInfo.E200002);
            result.put("errorMsg", "体验内容明细"+ ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getComment()!=null&&experienceAllVO.getComment().getBytes().length >1000){
            result.put("resultCode", ResultCodeInfo.E200002);
            result.put("errorMsg", "备注"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
            return JSONObject.toJSONString(result);
        }if(experienceAllVO.getRequirement()!=null&&experienceAllVO.getRequirement().getBytes().length >1000){
            result.put("resultCode", ResultCodeInfo.E200002);
            result.put("errorMsg", "要求"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
            return JSONObject.toJSONString(result);
        }
        if(experienceAllVO.getPeopleNumber()!=null&&experienceAllVO.getPeopleNumber().length() >10){
            result.put("resultCode", ResultCodeInfo.E200002);
            result.put("errorMsg", "人数"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
            return JSONObject.toJSONString(result);
        }
//        Integer a2 = experienceAllVO.getSortNo();//转换为包装类Integer
//        if(experienceAllVO.getSortNo()==0 || a2.toString().length() >8){
//            result.put("resultCode", ResultCodeInfo.E200002);
//            result.put("errorMsg", "排序"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
//            return JSONObject.toJSONString(result);
//        }
        if(experienceAllVO.getCurrencyType()!=null&&experienceAllVO.getCurrencyType().getBytes().length >8){
            result.put("resultCode", ResultCodeInfo.E200002);
            result.put("errorMsg", "货币类型"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200002));
            return JSONObject.toJSONString(result);
        }
        if(experienceAllVO.getPrice()!=null&&experienceAllVO.getPrice().length() >10 &&isBigDecimal(experienceAllVO.getPrice())){
            result.put("resultCode", ResultCodeInfo.E200006);
            result.put("errorMsg", "价格"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200006));
            return JSONObject.toJSONString(result);
//        Integer a4 =experienceAllVO.getPrice();//转换为包装类Integer
//        if(experienceAllVO.getEnabled()==0 || a4.toString().length() >2){
//            result.put("resultCode", ResultCodeInfo.E200001);
//            result.put("errorMsg", "激活标识符"+ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
//            return JSONObject.toJSONString(result);
//        }
//        }if(experienceAllVO.getStartTime()==null){
//            result.put("resultCode", ResultCodeInfo.E200001);
//            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
//            return JSONObject.toJSONString(result);
//        }if(experienceAllVO.getEndTime()==null){
//            result.put("resultCode", ResultCodeInfo.E200001);
//            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200001));
//            return JSONObject.toJSONString(result);
        }
        if(experienceAllVO.getImageId()!=null) {
            FileVO fileVO = fileUploaderService.readById(experienceAllVO.getImageId());
            if (fileVO == null) {
                result.put("resultCode", ResultCodeInfo.E200007);
                result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200007));
                return JSONObject.toJSONString(result);
            }
        }
        return "success";
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
    /**
     * 对登陆用户进行校验
     * @param userId
     * @param token
     * @return
     */
    private boolean verrifyUser(String userId, String token) {
        if(token == null || userId == null){
            return false;
        }
        return token.equals(CacheUtils.get(LoginController.LOGIN_TOKEN_TAG+userId))? true : false;
    }
    private ExperienceAllVO changeVO(AddExperienceAllVO addExperienceAllVO){
        ExperienceAllVO experienceAllVO = new ExperienceAllVO();
        experienceAllVO.setTitle(addExperienceAllVO.getTitle());
        experienceAllVO.setSubtitle(addExperienceAllVO.getSubtitle());
        experienceAllVO.setSortNo(addExperienceAllVO.getSortNo());
        experienceAllVO.setRequirement(addExperienceAllVO.getRequirement());
        experienceAllVO.setRendezvous(addExperienceAllVO.getRendezvous());
        experienceAllVO.setPrice(addExperienceAllVO.getPrice());
        experienceAllVO.setPeopleNumber(addExperienceAllVO.getPeopleNumber());
        experienceAllVO.setImageUrl(addExperienceAllVO.getImageUrl());
        experienceAllVO.setImageId(addExperienceAllVO.getImageId());
        experienceAllVO.setDestination(addExperienceAllVO.getDestination());
        experienceAllVO.setCurrencyType(addExperienceAllVO.getCurrencyType());
        experienceAllVO.setContentDetails(addExperienceAllVO.getContentDetails());
        experienceAllVO.setContentDescription(addExperienceAllVO.getContentDescription());
        experienceAllVO.setComment(addExperienceAllVO.getComment());
        experienceAllVO.setServiceName(addExperienceAllVO.getServiceName());
        return experienceAllVO;
    }
    private ExperienceDetailVO changeVO1(AddExperienceAllVO addExperienceAllVO){
        ExperienceDetailVO experienceDetailVO = new ExperienceDetailVO();
        experienceDetailVO.setTitle(addExperienceAllVO.getTitle());
        experienceDetailVO.setSubtitle(addExperienceAllVO.getSubtitle());
        experienceDetailVO.setSortNo(addExperienceAllVO.getSortNo());
        experienceDetailVO.setRequirement(addExperienceAllVO.getRequirement());
        experienceDetailVO.setRendezvous(addExperienceAllVO.getRendezvous());
        experienceDetailVO.setPeopleNumber(addExperienceAllVO.getPeopleNumber());
        experienceDetailVO.setImageUrl(addExperienceAllVO.getImageUrl());
        experienceDetailVO.setImageId(addExperienceAllVO.getImageId());
        experienceDetailVO.setDestination(addExperienceAllVO.getDestination());
        experienceDetailVO.setContentDetails(addExperienceAllVO.getContentDetails());
        experienceDetailVO.setContentDescription(addExperienceAllVO.getContentDescription());
        experienceDetailVO.setComment(addExperienceAllVO.getComment());
        return experienceDetailVO;
    }

    private ExperienceVO changeVO2(AddExperienceAllVO addExperienceAllVO){
        ExperienceVO experienceVO = new ExperienceVO();
        experienceVO.setPrice(addExperienceAllVO.getPrice());
        experienceVO.setCurrencyType(addExperienceAllVO.getCurrencyType());
        return experienceVO;
    }
}


