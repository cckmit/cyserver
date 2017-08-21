package ltd.moore.ctravel.register.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import ltd.moore.ctravel.register.dto.RegisterDTO;
import ltd.moore.ctravel.register.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ZhuTail
 * @Date: 2017/5/23
 */
@Controller
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 获取短息验证码
     *
     * @param registerDTO
     * @return
     */
    @RequestMapping(value = "/register/validate", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String registerValidation(@ModelAttribute RegisterDTO registerDTO) {

        return JSONObject.toJSONString(registerService.registerValidation(registerDTO));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String register(@ModelAttribute RegisterDTO registerDTO) {

        return JSONObject.toJSONString(registerService.register(registerDTO));
    }
}
