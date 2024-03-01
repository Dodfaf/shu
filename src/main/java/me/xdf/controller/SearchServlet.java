package me.xdf.controller;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.xdf.bean.User;
import me.xdf.dao.UserDAO;

import java.io.IOException;
import java.util.List;
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("search".equals(action)) {
            // 处理根据用户名查询用户列表的请求
            handleSearchUsers(request, response);
        }
    }
    // 处理根据用户名查询用户列表的请求
    private void handleSearchUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String usernameToSearch = request.getParameter("username");
        List<User> userList = UserDAO.getUsersByUsername(usernameToSearch);
        // 将查询到的用户列表以 JSON 格式返回给前端
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String jsonUserList = gson.toJson(userList);
        response.getWriter().write(jsonUserList);
    }
}
