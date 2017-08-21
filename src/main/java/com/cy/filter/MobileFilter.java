package com.cy.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 手机端网页拦截器
 */
public class MobileFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("手机端网页拦截器!");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String code = request.getParameter("code") ;
        String state = request.getParameter("state") ;
        System.out.println("code -> " + code + " ; state -> " + state);
//        request.set
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
