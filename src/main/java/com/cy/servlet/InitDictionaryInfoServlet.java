package com.cy.servlet;

import com.cy.system.GetDictionaryInfo;
import com.cy.util.UserUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Map;

public class InitDictionaryInfoServlet extends HttpServlet
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InitDictionaryInfoServlet.class);

	private static final long serialVersionUID = 1L;

	private ServletContext context;

	@Override
	public void init() throws ServletException
	{
		/*GetDictionaryInfo.servletContext = this.getServletContext();

		// 注入用户工具类servletContext - 刘振 20160714
		UserUtils.servletContext = this.getServletContext();

		UserUtils.getInstance().getUserRoles();
		context = this.getServletContext();
		Map<String, Object> dictionaryInfoMap = GetDictionaryInfo.dictionaryInfoMap;
		Map<String, Object> authorityMap = GetDictionaryInfo.authorityMap;
		logger.info("##############################字典MAP里的数据长度为" + dictionaryInfoMap.size() + "##############################");
		context.setAttribute("dictionaryInfoMap", dictionaryInfoMap);
		context.setAttribute("authorityMap", authorityMap);
		context.setAttribute("menuUrlMap", GetDictionaryInfo.menuUrlMap);
		logger.info("##############################系统字典表数据加载成功##############################:");
		String userAccount = getInitParameter("userAccount");
		String passWord = getInitParameter("passWord");
		context.setAttribute("userAccount", userAccount);
		context.setAttribute("passWord", passWord);*/
	}

}
