package ltd.moore.ctravel.experience;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
        import java.util.*;

        import com.hdos.platform.common.page.Page;
        import ltd.moore.ctravel.experience.mapper.ExperienceAllMapper;
        import ltd.moore.ctravel.experience.mapper.ServiceTypeMapper;
        import ltd.moore.ctravel.experience.model.*;
        import ltd.moore.ctravel.experience.service.ExperienceDetailService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.PathVariable;
        import com.alibaba.fastjson.JSONObject;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.ResponseBody;
        import org.springframework.web.bind.annotation.RequestMapping;
/**
 * ExperienceDetail控制器
 * @author caicai
 * @version 1.0
 */
@Controller
@RequestMapping("/experienceDetail")
public class ExperienceDetailController  {

    @Autowired
    private ExperienceDetailService experienceDetailService;
    @Autowired
    private ServiceTypeMapper serviceTypeMapper;
    /**
     * 分页查询数据
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(String serviceName,String enabled,int page,int rows) {
        Map<String, Object> queryCondition = new HashMap<String,Object>();
        if("-1".equals(enabled)){
            enabled="";
        }
        if("-全部-".equals(serviceName)||"".equals(serviceName)){
            serviceName="";
        }else if(serviceName!=null){
            serviceName=serviceTypeMapper.getById(serviceName).getServiceName();
        }
        queryCondition.put("serviceName", serviceName);
        queryCondition.put("enabled", enabled);
        Page<ExperienceAllVO> experienceDetailPage = experienceDetailService.findPage2(queryCondition, page, rows);
        JSONObject rst = new JSONObject();
        rst.put("total", experienceDetailPage.getTotalElements());
        rst.put("rows", experienceDetailPage.getContent());
        return rst.toJSONString();
    }

    /**
     * 主页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/init")
    public String init(Model model) {
        return "experience/experienceMain";
    }

    /**
     * 编辑页面
     * @return
     */
    @RequestMapping(value = "/add")
    public String addExperienceDetail() {
        return "experience/experienceAdd";
    }

    /**
     * 保存
     * @param experienceAllVO
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public String saveExperienceDetail(ExperienceAllVO experienceAllVO,ExperienceDetailVO experienceDetailVO,ExperienceVO experienceVO) {
        return  experienceDetailService.saveExperienceDetail(experienceAllVO,experienceDetailVO,experienceVO) ? "false" : "success";
    }

    /**
     * 批量删除
     * @param experienceDetailIds
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String deleteExperienceDetail(String experienceDetailIds) {
        String[] ids = experienceDetailIds.split(",");
        if(!experienceDetailService.deleteExperienceDetails(ids)){
            return "false";
        }
        return "success";
    }

    /**
     * 修改页面
     * @return
     */
    @RequestMapping(value = "/modify/{experienceDetailId}")
    public String modifyExperienceDetail(@PathVariable("experienceDetailId") String experienceDetailId,Model model) {
        experienceDetailService.readExperienceDetailById(new ExperienceAllVO(),experienceDetailId,model);
        return "experience/experienceDetail2";
    }
    /**
     * 获取服务类型combobox
     *
     * @return String
     */
    @RequestMapping(value = "/serviceNameCombobox", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String cardfactorycombobox() {
        Map<String, Object> queryCondition = new HashMap<String,Object>();
        List<ServiceTypeVO> serviceTypeVOList = serviceTypeMapper.list(queryCondition);
        ServiceTypeVO serviceTypeVO = new ServiceTypeVO();
        serviceTypeVO.setServiceName("-请选择-");
        serviceTypeVO.setServiceTypeId("");
        serviceTypeVOList.add(0, serviceTypeVO);
        return JSONObject.toJSONString(serviceTypeVOList);
    }
    /**
     * 获取服务类型combobox
     *
     * @return String
     */
    @RequestMapping(value = "/serviceNameCombobox2", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String cardfactorycombobox2() {
        Map<String, Object> queryCondition = new HashMap<String,Object>();
        List<ServiceTypeVO> serviceTypeVOList = serviceTypeMapper.list(queryCondition);
        ServiceTypeVO serviceTypeVO = new ServiceTypeVO();
        serviceTypeVO.setServiceName("-全部-");
        serviceTypeVO.setServiceTypeId("");
        serviceTypeVOList.add(0, serviceTypeVO);
        return JSONObject.toJSONString(serviceTypeVOList);
    }
}

