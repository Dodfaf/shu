package me.xdf.listenner;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import java.util.HashMap;
import java.util.Map;
public class LoginSessionListener implements HttpSessionAttributeListener {
    Map<String, HttpSession> map = new HashMap<String, HttpSession>();
    public void attributeAdded(HttpSessionBindingEvent event) {
        String name = event.getName();
        if (name.equals("username")) {
            if (map.get(event.getValue().toString())!= null) {
                HttpSession session = map.get(event.getValue().toString());
                session.removeAttribute("username");
                session.setAttribute("msg", "您的帐号已经在其他机器上登录，您被迫下线。");
            }
            map.put(event.getValue().toString(), event.getSession());
        }
    }
    public void attributeRemoved(HttpSessionBindingEvent event) {
        String name = event.getName();
        if (name.equals("username")) {
            map.remove(name);
        }
    }
    public void attributeReplaced(HttpSessionBindingEvent event) {
        String name = event.getName();
        if (name.equals("username")) {
            map.remove(event.getValue().toString());
            if (map.get(event.getSession().getAttribute("username").toString()) != null) {
                HttpSession session = map.get(event.getSession().getAttribute("username").toString());
                session.removeAttribute("username");
                session.setAttribute("msg", "您的帐号已经在其他机器上登录，您被迫下线。");
            }
            map.put(event.getSession().getAttribute("username").toString(), event.getSession());
        }
    }
}