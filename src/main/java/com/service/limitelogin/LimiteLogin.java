package com.service.limitelogin;

import com.entity.User;
import com.service.UserService;
import com.utils.DateUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 判断用户是否在线及剔除处理 Created by root on 16-10-16.
 */
@Service
@Transactional
public class LimiteLogin {

    private static Logger log = Logger.getLogger(SessionListener.class);

    private static Map<String, String> loginUserMap = new HashMap<String, String>();//存储在线用户
    private static Map<String, String> loginOutTime = new HashMap<String, String>();//存储剔除用户时间
    @Autowired
    private UserService userService;

    public String loginLimite(HttpServletRequest request, String userName) {
        User user = userService.findByUserName(userName);
        String sessionId = request.getSession().getId();
        for (String key : loginUserMap.keySet()) {
            //用户已在另一处登录
            if (key.equals(user.getUserName()) && !loginUserMap.containsValue(sessionId)) {
                log.info("用户：" + user.getUserName() + "，于" + DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + "被剔除！");
                loginOutTime.put(user.getUserName(), DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
                loginUserMap.remove(user.getUserName());
                break;
            }
        }
        loginUserMap.put(user.getUserName(), sessionId);
        request.getSession().getServletContext().setAttribute("loginUserMap", loginUserMap);
        request.getSession().getServletContext().setAttribute("loginOutTime", loginOutTime);
        return "success";
    }


}
