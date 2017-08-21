package ltd.moore.ctravel.filter;

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.common.util.CacheUtils;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.hotHomepage.controller.HotHomePageController;
import ltd.moore.ctravel.login.controller.LoginController;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAuthcFilter extends AccessControlFilter {
	private static final Logger logger = LoggerFactory.getLogger(LoginAuthcFilter.class);
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse arg1, Object arg2) throws Exception {
		return false;
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String token = request.getParameter("token");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultCode", ResultCodeInfo.E100010.getResultCode());
		jsonObject.put("errorMsg", ResultCodeInfo.E100010.getErrorMsg());
		if(token == null || userId == null){
			httpResponse.getWriter().write(jsonObject.toString());
			return false;
		}
		if(!token.equals(CacheUtils.get(LoginController.LOGIN_TOKEN_TAG+userId))){

			httpResponse.getWriter().write(jsonObject.toString());
			return false;
		}
		return  true;
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated()) {
			authenticationFailed(response);
			return false;
		}
		return true;
	}

	/**
	 * 认证失败
	 *
	 * @param response
	 * @throws IOException
	 */
	private void authenticationFailed(ServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultCode", ResultCodeInfo.E888888);
		jsonObject.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E888888));
		logger.info(jsonObject.toString());
		httpResponse.getWriter().write(jsonObject.toString());
	}

}
