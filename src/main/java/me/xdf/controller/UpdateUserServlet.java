package me.xdf.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.xdf.bean.User;
import me.xdf.dao.UserDAO;

import java.io.IOException;
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取参数
        int userId = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String image = request.getParameter("image");
        String salt = UserDAO.generateSalt();
        String hash_password = UserDAO.hashPassword(password, salt);
        int age = Integer.parseInt(request.getParameter("age"));
        // 创建用户对象
        if (password.equals(UserDAO.getUsersByUsername(username).get(0).getPassword())){
            User updatedUser = new User(userId, username, gender, email, age, image);
            new UserDAO().updateUser(updatedUser);
        }else {
            User updatedUser = new User(userId, username, hash_password, gender, email, age, image, salt);
            // 更新用户信息
            UserDAO userDAO = new UserDAO();
            userDAO.updateUser(updatedUser);
        }



        // 重定向到管理员用户页面
        response.sendRedirect("ListUsersServlet");
    }
}