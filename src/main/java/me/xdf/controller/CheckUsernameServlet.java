package me.xdf.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.xdf.dao.UserDAO;

import java.io.IOException;
@WebServlet("/CheckUsernameServlet")
public class CheckUsernameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取要检查的用户名
        String usernameToCheck = request.getParameter("username");
        // 在此处添加检查用户名是否重复的逻辑
        boolean isUsernameExists = UserDAO.isUsernameExists(usernameToCheck);
        // 将结果返回给AJAX请求
        response.getWriter().write(String.valueOf(!isUsernameExists));
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}