package com.cy.core.log.util;

import com.cy.common.utils.SpringContextHolder;
import com.cy.common.utils.StringUtils;
import com.cy.core.log.dao.LogMapper;
import com.cy.core.log.entity.Log;
import com.cy.core.user.entity.User;
import com.cy.util.UserUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cha0res on 12/19/16.
 */
public class LogUtils {
    public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

    private static LogMapper logMapper = SpringContextHolder.getBean("logMapper");		// 人

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, String title){
        saveLog(request, null, null, title);
    }

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
        User user = UserUtils.getUser();
        if (user != null && user.getUserId() > 0){
            Log log = new Log();
            log.setTitle(title);
            log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
            log.setRemoteAddr(StringUtils.getRemoteAddr(request));
            log.setUserAgent(request.getHeader("user-agent"));
            log.setRequestUri(request.getRequestURI());
            log.setParams(request.getParameterMap());
            log.setMethod(request.getMethod());
            log.preInsert();
            logMapper.saveLog(log);
            // 异步保存日志
            new SaveLogThread(log, handler, ex).start();
        }
    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread{

        private Log log;
        private Object handler;
        private Exception ex;

        public SaveLogThread(Log log, Object handler, Exception ex){
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.handler = handler;
            this.ex = ex;
        }


    }

}
