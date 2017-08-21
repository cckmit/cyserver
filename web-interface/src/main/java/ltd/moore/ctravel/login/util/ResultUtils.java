package ltd.moore.ctravel.login.util;

import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.login.dto.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cocouzx on 2017-5-27 0027.
 */
public class ResultUtils {
    /**
     * 登陆返回对象实例
     * @param resultCodeInfo
     * @param userId
     * @param token
     * @param needCaptcha
     * @return
     */
    public static Object generate(ResultCodeInfo resultCodeInfo, String userId, String token, String needCaptcha){
        Map<String,Object> result = new HashMap<String ,Object>();
        result.put("resultCode",resultCodeInfo.getResultCode());
        result.put("errorMsg",resultCodeInfo.getErrorMsg());
        Map<String,Object> data = new HashMap<String ,Object>();
        data.put("token",token);
        data.put("userId", userId);
        data.put("needCaptcha", needCaptcha);
        result.put("data",data);
        return result;
    }
}
