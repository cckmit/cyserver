package ltd.moore.ctravel.constants;

import org.springframework.util.StringUtils;

/**
 * Created by cyong on 2017/5/11.
 */
public enum ResultCodeInfo {
    E000000("E000000","成功"),
    E100001("E100001","用户名或者密码错误"),
    E100002("E100002","登陆验证码错误"),
    E100003("E100003","未关联手机号"),
    E100004("E100004","没有开通商户功能"),
    E100005("E100005","用户已注册"),
    E100006("E100006","验证码错误"),
    E100007("E100007","获取验证码-手机号已注册"),
    E100008("E100008","获取验证码-手机号未注册"),
    E100009("E100009","获取验证码-请求过于频繁，请稍后再试"),
    E100010("E100010","用户token错误"),
    E555555("E555555","新密码与原密码相同，请直接登陆"),
    E666666("E666666","找回密码验证失败"),
    E777777("E777777","注册验证失败"),
    E888888("E888888","没有登录"),
    E999999("E999999","系统错误"),
    E200001("E200001","字段为空或者超出长度限制"),
    E200002("E200002","超出长度限制"),
    E200003("E200003","ID不存在或输入有误"),
    E200004("E200004","删除体验失败"),
    E200005("E200005","查无此体验"),
    E200006("E200006","输入不符合金钱格式"),
    E200007("E200007","该服务类型不存在"),
    E200008("E200008","该目的地不存在"),
    E200010("E200010","用户权限不足"),
    E200011("E200011","体验不能重复发布"),
    E200012("E200012","体验不是发布状态，不能取消发布"),
    E200013("E200013","日期格式错误"),
    E200014("E200014","评分人不存在"),
    E200015("E200015","分数格式有误"),
    E200016("E200016","查无此评分"),
    E200017("E200017","查无此评论"),
    E200018("E200018","体验状态类型错误"),
    E200019("E200019","无权限发布体验"),
    E200020("E200020","正在审核不能再次审核"),
    E200021("E200021","审核通过不能审核"),
    E200022("E200022","无法获取推送数据");
    /**
     * 返回结果码
     */
    private String resultCode;

    /**
     * 结果描述
     */
    private String errorMsg;

    ResultCodeInfo(String resultCode, String errorMsg) {
        this.resultCode = resultCode;
        this.errorMsg = errorMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 获取code描述
     * @param code
     */
    public static String formateCodeMsg(ResultCodeInfo code) {
        if(StringUtils.isEmpty(code)) {
            return null;
        }
        for(ResultCodeInfo resultCodeInfo : ResultCodeInfo.values()) {
            if(code.equals(resultCodeInfo)) {
                return resultCodeInfo.getErrorMsg();
            }
        }
        return null;
    }
}
