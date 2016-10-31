package com.service.limitelogin;

import com.entity.User;

import org.apache.log4j.Logger;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by root on 16-10-16.
 */
public class SessionListener implements HttpSessionListener {

    private static Logger log = Logger.getLogger(SessionListener.class);

    public void sessionCreated(HttpSessionEvent event) {

    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String sessionId = session.getId();
        //在session销毁的时候,把loginUserMap中保存的键值对清除
        User user = (User) session.getAttribute("now_user");
        if (user != null) {
            Map<String, String> loginUserMap = (Map<String, String>) event.getSession().getServletContext().getAttribute("loginUserMap");
            if(loginUserMap.get(user.getUserName()).equals(sessionId)){
                log.info("clean user from application : " + user.getUserName());
                loginUserMap.remove(user.getUserName());
                event.getSession().getServletContext().setAttribute("loginUserMap", loginUserMap);
            }
        }

    }

}
