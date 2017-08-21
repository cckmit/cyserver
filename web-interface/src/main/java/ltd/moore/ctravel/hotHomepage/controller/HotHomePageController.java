package ltd.moore.ctravel.hotHomepage.controller;

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.common.util.CacheUtils;
import io.swagger.annotations.ApiImplicitParams;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.experience.model.ExperienceDetailVO;
import ltd.moore.ctravel.homepage.model.HomePageHotVO;
import ltd.moore.ctravel.homepage.service.HotAllService;
import ltd.moore.ctravel.login.controller.LoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cocouzx on 2017-6-28 0028.
 */
@Controller
@RequestMapping("/api")
public class HotHomePageController {

    @Autowired
    private HotAllService hotAllService;
    /**
     * 获取首页内容
     */
    @RequestMapping(value = "/hot/homePage", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotHomePage(@RequestParam(value = "userId") String userId, @RequestParam String token){
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        List<ExperienceDetailVO> listHotExperience=  hotAllService.getHotExperience();
        List<HomePageHotVO> listHotService =  hotAllService.getHotService();
        List<HomePageHotVO> listHotDestination =  hotAllService.getHotDestinations();
//        List<ExperienceDetailVO> listHotServiceExperience=  hotAllService.getHotServiceExperience();
//        List<ExperienceDetailVO> listHotDestinationExperience=  hotAllService.getHotDestinationExperience();
        result.put("resultCode",ResultCodeInfo.E000000.getResultCode());
        result.put("errorMsg",ResultCodeInfo.E000000.getErrorMsg());
        result.put("hotExperienceList",listHotExperience);
        result.put("hotServiceList",listHotService);
        result.put("hotDestinationList",listHotDestination);
//        result.put("hotServiceExperienceList",listHotServiceExperience);
//        result.put("hotDestinationExperienceList",listHotDestinationExperience);
        return JSONObject.toJSONString(result);
    }

    /**
     * 获取热门目的地体验
     */
    @RequestMapping(value = "/hot/hotDestinationExperience/{destinationId}",  method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotDestinationExperienceList(@PathVariable(value = "destinationId") String destinationId,@RequestParam(value = "userId") String userId,@RequestParam(value = "token") String token) {
        Map<String, Object> result = new HashMap<>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        List<ExperienceDetailVO> list =  hotAllService.getHotDestinationExperience(destinationId);
        result.put("resultCode",ResultCodeInfo.E000000.getResultCode());
        result.put("errorMsg",ResultCodeInfo.E000000.getErrorMsg());
        result.put("hotDestinationExperienceList",list);
        return JSONObject.toJSONString(result);
    }

    /**
     * 获取热门服务体验
     */
    @RequestMapping(value = "/hot/hotServiceExperience/{serviceTypeId}",  method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotServiceExperienceList(@PathVariable(value = "serviceTypeId") String serviceTypeId,@RequestParam(value = "userId") String userId,@RequestParam(value = "token") String token) {
        Map<String, Object> result = new HashMap<>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        List<ExperienceDetailVO> list =  hotAllService.getHotServiceExperience(serviceTypeId);
        result.put("resultCode",ResultCodeInfo.E000000.getResultCode());
        result.put("errorMsg",ResultCodeInfo.E000000.getErrorMsg());
        result.put("hotServiceExperienceList",list);
        return JSONObject.toJSONString(result);
    }

    /**
     * 1)	新增热门体验
     */
    @RequestMapping(value = "/hot/hotExperience/{experienceId}", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotExperience(@PathVariable(value = "experienceId") String experienceId, String userId, String token){
        Map<String, Object> result = new HashMap<String, Object>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        hotAllService.addHotExperience(experienceId);
        return ResultGen(ResultCodeInfo.E000000);
    }

    /**
     *2)	删除热门体验:
     */
    @RequestMapping(value = "/hot/hotExperience/{hotExperienceId}",  method = RequestMethod.DELETE, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotExperienceDelete(@PathVariable(value = "experienceId") String experienceId, String userId, String token) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        hotAllService.deleteHotExperience(experienceId);
        return ResultGen(ResultCodeInfo.E000000);
    }

    /**
     * 3)   获取热门体验：
     */
    @RequestMapping(value = "/hot/hotExperience",  method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotExperienceList(String userId, String token) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        List<ExperienceDetailVO> list =  hotAllService.getHotExperience();
        result.put("resultCode",ResultCodeInfo.E000000.getResultCode());
        result.put("errorMsg",ResultCodeInfo.E000000.getErrorMsg());
        result.put("hotExperienceList",list);
        return JSONObject.toJSONString(result);
    }

    /**
     * 1)	新增热门目的地
     */
    @RequestMapping(value = "/hot/hotDestination/{destinationId}", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotDestination(@PathVariable(value = "destinationId") String destinationId, String userId, String token){
        Map<String, Object> result = new HashMap<String, Object>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        hotAllService.addHotDestination(destinationId);
        return ResultGen(ResultCodeInfo.E000000);
    }

    /**
     *2)	删除热门目的地:
     */
    @RequestMapping(value = "/hot/hotDestination/{hotDestinationId}",  method = RequestMethod.DELETE, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotDestinationDelete(@PathVariable(value = "hotDestinationId") String hotDestinationId, String userId, String token) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        hotAllService.deleteHotDestination(hotDestinationId);
        return ResultGen(ResultCodeInfo.E000000);
    }

    /**
     * 3)   获取热门目的地：
     */
    @RequestMapping(value = "/hot/hotDestination",  method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotDestinationList(String userId, String token) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        List<HomePageHotVO> list =  hotAllService.getHotDestinations();
        result.put("resultCode",ResultCodeInfo.E000000.getResultCode());
        result.put("errorMsg",ResultCodeInfo.E000000.getErrorMsg());
        result.put("hotDestinationList",list);
        return JSONObject.toJSONString(result);
    }

    /**
     * 1)	新增热门服务类型
     */
    @RequestMapping(value = "/hot/hotService/{serviceTypeId}", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotService(@PathVariable(value = "serviceTypeId") String serviceTypeId, String userId, String token){
        Map<String, Object> result = new HashMap<String, Object>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        hotAllService.addHotService(serviceTypeId);
        return ResultGen(ResultCodeInfo.E000000);
    }

    /**
     *2)	删除热门服务类型
     */
    @RequestMapping(value = "/hot/hotService/{serviceTypeId}",  method = RequestMethod.DELETE, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotServiceDelete(@PathVariable(value = "serviceTypeId") String serviceTypeId, String userId, String token) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        hotAllService.deleteHotService(serviceTypeId);
        return ResultGen(ResultCodeInfo.E000000);
    }

    /**
     * 3)   获取热门服务类型：
     */
    @RequestMapping(value = "/hot/hotService",  method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String hotServiceList(String userId, String token) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(!verrifyUser(userId, token)){
            return ResultGen(ResultCodeInfo.E100010);
        }
        List<HomePageHotVO> list =  hotAllService.getHotService();
        result.put("resultCode",ResultCodeInfo.E000000.getResultCode());
        result.put("errorMsg",ResultCodeInfo.E000000.getErrorMsg());
        result.put("hotDestinationList",list);
        return JSONObject.toJSONString(result);
    }











    /**
     * 生成返回信息
     * @param e000000
     * @return
     */
    public String ResultGen(ResultCodeInfo e000000) {
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
    public boolean verrifyUser(String userId, String token) {
        if(token == null || userId == null){
            return false;
        }
        return token.equals(CacheUtils.get(LoginController.LOGIN_TOKEN_TAG+userId))? true : false;
    }
}
