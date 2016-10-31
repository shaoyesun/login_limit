package com.controller.user;

import com.entity.User;
import com.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by root on 16-9-28.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 添加新的用户
     *
     * @param userName
     * @param password
     * @param model
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(String userName, String password, Model model) {
        String result = userService.register(userName, password);
        log.info("userName = " + userName + " add success!");
        return result;
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findAll")
    public List findAll() {
        return userService.findAll();
    }

    /**
     * 删除指定用户
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public String del(Long id) {
        log.info("id = " + id + " del success!");
        return userService.del(id);
    }

    /**
     * 编辑指定用户信息
     *
     * @param id
     * @param userName
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Long id, String userName, String password) {
        log.info("id = " + id + ", userName = " + userName + " edit success!");
        return userService.update(id, userName, password);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginOut")
    public String loginOut(HttpServletRequest request) {
        HttpSession session1 = request.getSession();
        session1.invalidate();
        return "redirect:/other/toLogin";
    }

    /**
     * 登录后重定向test 用户掉线，刷新跳转登录页面，登录后重定向到success页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginRedirect")
    public String loginRedirect(HttpServletRequest request) {
        return "success";
    }

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request) {
        return "index";
    }

}
