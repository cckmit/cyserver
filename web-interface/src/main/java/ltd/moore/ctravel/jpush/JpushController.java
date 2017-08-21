package ltd.moore.ctravel.jpush;

import com.alibaba.fastjson.JSONObject;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.jpush.api.ErrorCodeEnum;
import ltd.moore.ctravel.jpush.api.IOSExtra;
import ltd.moore.ctravel.jpush.api.JPushClient;
import ltd.moore.ctravel.jpush.api.MessageResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
@Controller
@RequestMapping("/api/jpush")
public class JpushController {
    //在极光注册上传应用的 appKey 和 masterSecret
    private static final String appKey = "abc30522491012e27e5dd32f";////必填，例如466f7032ac604e02fb7bda89

    private static final String masterSecret = "3c2445fa0142833d2ac50700";//必填，每个应用都对应一个masterSecret

    private static JPushClient jpush = null;

    /*
     * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
     * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
     * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒
     */
    private static long timeToLive = 60 * 60 * 24;

    /**
     * 向客户端推送消息
     *
     * @return
     */
    @RequestMapping(value = "/push", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String JPushClient(@RequestParam String customerId, @RequestParam String token, @RequestParam String msgTitle, @RequestParam String msgContent) {

		/*
         * Example1: 初始化,默认发送给android和ios，同时设置离线消息存活时间
		 * jpush = new JPushClient(masterSecret, appKey, timeToLive);
		 *
		 * Example2: 只发送给android
		 * jpush = new JPushClient(masterSecret, appKey, DeviceEnum.Android);
		 *
		 * Example3: 只发送给IOS
		 * jpush = new JPushClient(masterSecret, appKey, DeviceEnum.IOS);
		 *
		 * Example4: 只发送给android,同时设置离线消息存活时间
		 * jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.Android);
		 */
        jpush = new JPushClient(masterSecret, appKey, timeToLive);
		/*
		 * 是否启用ssl安全连接, 可选
		 * 参数：启用true， 禁用false，默认为非ssl连接
		 */
        //jpush.setEnableSSL(true);

        //测试发送消息或者通知
        return testSend(msgTitle, msgContent);
}

    private  String testSend(String msgTitle, String msgContent) {
        // 在实际业务中，建议 sendNo 是一个你自己的业务可以处理的一个自增数字。
        // 除非需要覆盖，请确保不要重复使用。详情请参考 API 文档相关说明。
        Integer num = getRandomSendNo();
        String sendNo = num.toString();
//            msgTitle = "jpush,TEST\"\"";
//            msgContent = "安卓与IOS推送TEST";
		/*
		 * IOS设备扩展参数,
		 * 设置badge，设置声音
		 */
        Map<String, Object> extra = new HashMap<String, Object>();
        IOSExtra iosExtra = new IOSExtra(1, "WindowsLogonSound.wav");
        extra.put("ios", iosExtra);
        //IOS和安卓一起
        MessageResult msgResult = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent, 0, extra);

        //对所有用户发送通知, 更多方法请参考文档
        //	MessageResult msgResult = jpush.sendCustomMessageWithAppKey(sendNo,msgTitle, msgContent);

        if (null != msgResult) {
            System.out.println("服务器返回数据: " + msgResult.toString());
            if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {

                System.out.println("发送成功， sendNo=" + msgResult.getSendno());
                return ResultGen(ResultCodeInfo.E000000);
            } else {
                JSONObject rst = new JSONObject();
                rst.put("resultCode", msgResult.getErrcode());
                rst.put("errMsg", msgResult.getErrmsg());
                System.out.println("发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息=" + msgResult.getErrmsg());
                return rst.toJSONString();
            }
        } else {
            System.out.println("无法获取数据");
            JSONObject rst = new JSONObject();
            rst.put("resultCode", ResultCodeInfo.E200022);
            rst.put("errMsg",ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E200022));
            return rst.toJSONString();
        }
    }

    public static final int MAX = Integer.MAX_VALUE;
    public static final int MIN = (int) MAX / 2;

    /**
     * 保持 sendNo 的唯一性是有必要的
     * It is very important to keep sendNo unique.
     *
     * @return sendNo
     */
    public static int getRandomSendNo() {
        return (int) (MIN + Math.random() * (MAX - MIN));
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
}
