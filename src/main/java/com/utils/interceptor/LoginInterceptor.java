package com.utils.interceptor;

import com.entity.User;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器 1、未登录跳转登录页面 2、掉线保存当前链接，重定向到登录页面,登录后重定向到要访问页面 3、用户被踢掉后给出提示信息
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("now_user");
        if (user == null) {
            //用户掉线，保存当前链接并重定向到登录页面
            //String s = request.getHeader("x-requested-with");
            if (request.getHeader("x-requested-with") == null) {//非ajax(异步)请求，则保存当前访问链接
                String queryUrl = request.getQueryString() == null ? "" : ("?" + request.getQueryString());//获取参数
                String requestUrl = request.getServletPath() + queryUrl;//httpRequest.getServletPath(),获取链接
                if (session.getAttribute("redirect_link") == null) {
                    session.setAttribute("redirect_link", requestUrl);
                }
            }
            response.sendRedirect(request.getContextPath() + "/other/toLogin");
            return false;
        }

        //多用户登录限制判断,并给出提示信息
        boolean isLogin = false;
        if (user != null) {
            Map<String, String> loginUserMap = (Map<String, String>) session.getServletContext().getAttribute("loginUserMap");
            String sessionId = session.getId();
            for (String key : loginUserMap.keySet()) {
                //用户已在另一处登录
                if (key.equals(user.getUserName()) && !loginUserMap.containsValue(sessionId)) {
                    isLogin = true;
                    break;
                }
            }
        }
        if (isLogin) {
            Map<String, String> loginOutTime = (Map<String, String>) session.getServletContext().getAttribute("loginOutTime");
            session.setAttribute("mess", "用户：" + user.getUserName() + "，于 " + loginOutTime.get(user.getUserName()) + " 已在别处登录!");
            loginOutTime.remove(user.getUserName());
            session.getServletContext().setAttribute("loginUserMap", loginOutTime);
            response.sendRedirect(request.getContextPath() + "/other/toLogin");
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
