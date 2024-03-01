package me.xdf.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.xdf.dao.UserDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String input_username = request.getParameter("username");
        String password = request.getParameter("password");
        String enteredCaptcha = request.getParameter("captcha");
        String expectedCaptcha = (String) request.getSession().getAttribute("captchaCode");
        // 验证码正确正确则进入
        if (expectedCaptcha != null && expectedCaptcha.equals(enteredCaptcha)) {

            if ("admin".equals(input_username) && "admin".equals(password)) {
                //登录成功
                request.getSession().setAttribute("username", input_username);
                response.sendRedirect("ListUsersServlet");

            } else if (UserDAO.check_password(input_username, password)) {
                //验证密码
                String encodedMessage = URLEncoder.encode(" " + input_username, StandardCharsets.UTF_8.toString());
                // 使用UTF-8编码，并替换空格为%20
                encodedMessage = encodedMessage.replace("+", "");
                response.sendRedirect("UserLoginServlet?username=" + encodedMessage );
            } else {
                // 账号或密码错误
                request.setAttribute("error",1);
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }
        }else {
            // 验证码错误
            request.setAttribute("error",2);
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

}