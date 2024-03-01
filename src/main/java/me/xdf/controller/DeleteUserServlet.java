package me.xdf.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.xdf.dao.UserDAO;

import java.io.IOException;
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取要删除的用户的ID
        int userId = Integer.parseInt(request.getParameter("id"));
        // 删除用户
        UserDAO userDAO = new UserDAO();
        userDAO.deleteUserAndReuseId(userId);
        // 重定向到管理员用户页面
        response.sendRedirect("adminuser.jsp");
    }
}