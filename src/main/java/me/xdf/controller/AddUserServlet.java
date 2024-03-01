package me.xdf.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.xdf.bean.User;
import me.xdf.dao.UserDAO;
import java.io.IOException;

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String image = request.getParameter("image");
        int age = Integer.parseInt(request.getParameter("age"));
        //哈希加盐加密密码
        String salt = UserDAO.generateSalt();
        String hashedPassword = UserDAO.hashPassword(password, salt);
        // 创建新用户对象
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setGender(gender);
        newUser.setEmail(email);
        newUser.setAge(age);
        newUser.setImagePath(image);
        newUser.setSalt(salt);

        // 将新用户添加到数据库
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(newUser);
        System.out.println("User added successfully");
        // 重定向回管理员用户页面
        response.sendRedirect("adminuser.jsp");
    }


}