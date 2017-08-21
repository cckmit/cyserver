package ltd.moore.ctravel.hotdestinations.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import ltd.moore.ctravel.experience.model.ExperienceDetailVO;
import ltd.moore.ctravel.homepage.model.HomePageHotVO;
import ltd.moore.ctravel.homepage.service.HotAllService;
import ltd.moore.ctravel.hotdestinations.service.HotDestinationsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HotDestinations控制器
 * @author Cocouzx
 * @version 1.0
 */
@Controller
@RequestMapping("/hotAll")
public class HotDestinationsController {
    private static Logger logger = Logger.getLogger(HotDestinationsController.class);
    @Autowired
    private HotDestinationsService hotDestinationsService;
    @Autowired
    private HotAllService hotAllService;

    /**
     * 热门体验datagrid
     */
    @RequestMapping(value = "/listHot",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listHot(String option, String hotDestination, String hotService){
        //初始化
        if(StringUtils.isEmpty(option) || "2".equals(option)){
            List<ExperienceDetailVO> reslut =hotAllService.getHotExperience();
            logger.info(reslut);
            return JSONObject.toJSONString(reslut);
        }
        //热门目的地体验
        if("1".equals(option)){
            if("-1".equals(hotDestination)){
                return JSONObject.toJSONString(null);
            }else{
                List<ExperienceDetailVO> reslut =hotAllService.getHotDestinationExperience(hotDestination);
                logger.info(reslut);
                return JSONObject.toJSONString(reslut);
            }
        }
        if("3".equals(option)){
            if("-1".equals(hotService)){
                return JSONObject.toJSONString(null);
            }else{
                List<ExperienceDetailVO> reslut =hotAllService.getHotServiceExperience(hotService);
                logger.info(reslut);
                return JSONObject.toJSONString(reslut);
            }
        }
        return JSONObject.toJSONString(null);
    }

    /**
     * 添加体验视图
     */
    @RequestMapping(value = "/add",produces = "application/json;charset=UTF-8")
    public String add(String option,String hotDestination, String hotService, Model model){
        model.addAttribute("option",option);
        model.addAttribute("hotDestination",hotDestination);
        model.addAttribute("hotService",hotService);
        return "hotdestinations/hotDestinationsAdd";
    }

    /**
     * 删除对应体验
     */
    @RequestMapping(value = "/delete",produces = "application/json;charset=UTF-8")
    public String delete(String option, String experienceIds){
        //删除热门体验
        if(StringUtils.isEmpty(option) || "2".equals(option)) {
            String[] idss = experienceIds.split(",");
            int i = 0;
            while(i <idss.length){
                hotAllService.deleteHotExperience(idss[i]);
                i++;
            }
        }
        //删除热门目的地体验
        if("1".equals(option)){
            String[] idss = experienceIds.split(",");
            int i = 0;
            while(i <idss.length){
                hotAllService.deleteHotDestinationExperience(idss[i]);
                i++;
            }
        }
        //删除热门服务体验
        if("3".equals(option)){
            String[] idss = experienceIds.split(",");
            int i = 0;
            while(i <idss.length){
                hotAllService.deleteHotServiceExperience(idss[i]);
                i++;
            }
        }
        return "hotdestinations/hotDestinationsAdd";
    }

    /**
     * 添加体验
     */
    @RequestMapping(value = "/listAdd",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listAdd(String option,String hotService, String hotDestination){
        logger.info(option);
        //热门体验
        if("2".equals(option)){
            List<ExperienceDetailVO> reslut =hotAllService.getNotHotExperience();
            return JSONObject.toJSONString(reslut);
        }
        //热门目的地
        if("1".equals(option)){
            List<ExperienceDetailVO> result = hotAllService.getNotHotDestinationsExperience(hotDestination);
            return JSONObject.toJSONString(result);
        }
        if("3".equals(option)){
            List<ExperienceDetailVO> result = hotAllService.getNotHotServiceExperience(hotService);
            return JSONObject.toJSONString(result);
        }
        return "hotdestinations/hotDestinationsAdd";
    }

    /**
     * 子页面，添加热门体验
     */
    @RequestMapping(value = "/addHotExperience",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void addHotExperience(String ids){
        logger.info(ids);
        String[] idss = ids.split(",");
        int i = 0;
        while(i <idss.length){
            hotAllService.addHotExperience(idss[i]);
            i++;
        }
    }
    /**
     * 获取热门目的地
     * @return
     */
    @RequestMapping(value = "/hotDestinations",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hotDestinations(){
        HomePageHotVO homePageHotVO1 = new HomePageHotVO();
        homePageHotVO1.setDestination("-请选择-");
        homePageHotVO1.setDestinationId("-1");
        List<HomePageHotVO> homePageHotVO= hotAllService.getHotDestinations();
        homePageHotVO.add(homePageHotVO1);
        Map<String, Object> result = new HashMap<>();
        return JSONObject.toJSONString(homePageHotVO);
    }
    @RequestMapping(value = "/hotDestinationst",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hotDestinationst(){
        List<HomePageHotVO> homePageHotVO= hotAllService.getHotDestinations();
        Map<String, Object> result = new HashMap<>();
        return JSONObject.toJSONString(homePageHotVO);
    }

    /**
     * 添加热门目的地体验
     */
    @RequestMapping(value = "/addHotDestinationExperience",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void addHotDestinationExperience(String ids, String hotDestination){
        logger.info(ids);
        String[] idss = ids.split(",");
        int i = 0;
        while(i <idss.length){
            hotAllService.addHotDestinationExperience(idss[i],hotDestination);
            i++;
        }
    }
    /**
     * 添加热门服务体验
     */
    @RequestMapping(value = "/addHotServiceExperience",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void addHotServiceExperience(String ids, String hotService){
        logger.info(ids);
        String[] idss = ids.split(",");
        int i = 0;
        while(i <idss.length){
            hotAllService.addHotServiceExperience(idss[i],hotService);
            i++;
        }
    }

    /**
     * 获取非热门目的地
     * @return
     */
    @RequestMapping(value = "/notHotDestinations",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String notHotDestinations(){
        HomePageHotVO homePageHotVO1 = new HomePageHotVO();
        homePageHotVO1.setDestination("-请选择-");
        homePageHotVO1.setDestinationId("-1");
        List<HomePageHotVO> homePageHotVO= hotAllService.getNotHotDestinations();
        homePageHotVO.add(homePageHotVO1);
        Map<String, Object> result = new HashMap<>();
        return JSONObject.toJSONString(homePageHotVO);
    }
    /**
     * 增加热门目的地
     */
    @RequestMapping(value = "/addHotDestination",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void addHotDestination(String id){
        hotAllService.addHotDestination(id);
    }
    /**
     * 删除热门目的地
     */
    @RequestMapping(value = "/deleteHotDestination",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void deleteHotDestination(String ids){
        String[] idss = ids.split(",");
        int i = 0;
        while(i <idss.length){
            hotAllService.deleteHotDestination(idss[i]);
            i++;
        }
    }
    /**
     * 获取非热门服务
     * @return
     */
    @RequestMapping(value = "/notHotService",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String notHotService(){
        HomePageHotVO homePageHotVO1 = new HomePageHotVO();
        homePageHotVO1.setServiceName("-请选择-");
        homePageHotVO1.setServiceTypeId("-1");
        List<HomePageHotVO> homePageHotVO= hotAllService.getNotHotService();
        homePageHotVO.add(homePageHotVO1);
        Map<String, Object> result = new HashMap<>();
        return JSONObject.toJSONString(homePageHotVO);
    }
    /**
     * 增加热门服务
     */
    @RequestMapping(value = "/addHotService",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void addHotService(String id){
        hotAllService.addHotService(id);
    }
    /**
     * 删除热门服务
     */
    @RequestMapping(value = "/deleteHotService",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void deleteHotService(String ids){
        String[] idss = ids.split(",");
        int i = 0;
        while(i <idss.length){
            hotAllService.deleteHotService(idss[i]);
            i++;
        }
    }
    /**
     * 获取热门类型
     */
    @RequestMapping(value = "/hotService",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hotService(){
        HomePageHotVO homePageHotVO1 = new HomePageHotVO();
        homePageHotVO1.setServiceTypeId("-1");
        homePageHotVO1.setServiceName("-请选择-");
        List<HomePageHotVO> homePageHotVO= hotAllService.getHotService();
        homePageHotVO.add(homePageHotVO1);
        Map<String, Object> result = new HashMap<>();
        return JSONObject.toJSONString(homePageHotVO);
    }
    @RequestMapping(value = "/hotServicet",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hotServicet(){
        List<HomePageHotVO> homePageHotVO= hotAllService.getHotService();
        Map<String, Object> result = new HashMap<>();
        return JSONObject.toJSONString(homePageHotVO);
    }

    /**
     * 获取首页页面
     * @return
     */
    @RequestMapping(value = "/init")
    public String mainFrame(){
        return "hotdestinations/hotDestinationsMain";
    }

    /**
     * 修改热门目的地
     */
    @RequestMapping(value = "/editHotDestination")
    public String editHotDestination(){
        return "hotdestinations/hotDestinationsEdit";
    }

    /**
     * 修改热门服务类型
     */
    @RequestMapping(value = "/editHotService")
    public String editHotService(){
        return "hotdestinations/hotServiceEdit";
    }
}
