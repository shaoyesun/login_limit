package com.controller.other;

import com.service.UserService;
import com.service.limitelogin.LimiteLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by root on 16-9-28.
 */
@Controller
@RequestMapping(value = "/other")
public class OtherController {

    @Autowired
    private UserService userService;
    @Autowired
    private LimiteLogin limiteLogin;

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping(value = "/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @param redirectAttributes
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String userName, String password, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //判断用户是否已经在线及处理（已在线则剔除）
        String loginLimite = limiteLogin.loginLimite(request, userName);
        //判断用户名、密码是否正确
        String result = userService.login(userName, password);
        if (result.equals("success")) {
            request.getSession().setAttribute("now_user", userService.findByUserName(userName));

            //用户掉线，登录后重定向到保存的链接
            Object url = request.getSession().getAttribute("redirect_link");
            if (url != null) {
                request.getSession().removeAttribute("redirect_link");
                return "redirect:" + url.toString();
            }
            return "index";
        }
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/other/toLogin";
    }

    /**
     * 多用户登录限制,清除session信息(登录信息、提示信息)
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/clearUserSession")
    public String clearUserSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        //httpSession.invalidate();
        httpSession.removeAttribute("now_user");
        httpSession.removeAttribute("mess");
        return "success";
    }

}
