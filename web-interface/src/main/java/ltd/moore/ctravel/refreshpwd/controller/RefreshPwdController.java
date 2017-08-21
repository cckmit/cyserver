package ltd.moore.ctravel.refreshpwd.controller;

import com.alibaba.fastjson.JSONObject;
import ltd.moore.ctravel.refreshpwd.dto.RefreshPwdDTO;
import ltd.moore.ctravel.refreshpwd.service.RefreshPwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: youcai
 * @Date: 2017/6/6
 */
@Controller
@RequestMapping("/api")
public class RefreshPwdController {

    @Autowired
    private RefreshPwdService refreshPwdService;

    /**
     * 获取短息验证码
     *
     * @param refreshPwdDTO
     * @return
     */
    @RequestMapping(value = "/refreshpwd/validate", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String refreshpwdValidation(@ModelAttribute RefreshPwdDTO refreshPwdDTO) {

        return JSONObject.toJSONString(refreshPwdService.refreshpwdValidation(refreshPwdDTO));
    }

    @RequestMapping(value = "/refreshpwd", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String refreshpwd(@ModelAttribute RefreshPwdDTO refreshPwdDTO) {
        return JSONObject.toJSONString(refreshPwdService.refreshpwd(refreshPwdDTO));
    }
}
