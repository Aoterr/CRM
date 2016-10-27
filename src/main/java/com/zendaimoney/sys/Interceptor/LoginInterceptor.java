package com.zendaimoney.sys.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springside.modules.ee.WebAuthException;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.sysuser.entity.SysUser;

/**
 * 登陆拦截器.
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final String[] IGNORE_URI = {"/login.jsp", "/index/login", "/index/doLogin","static/"};
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String url = request.getRequestURL().toString();
        //System.out.println(">>>: " + url);
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            SysUser user = (SysUser) request.getSession().getAttribute(ParamConstant.SYS_USER);
            if (user != null){
            	flag = true;
            }else{
            	throw new WebAuthException();
            }
        }
        return flag;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}