package me.xdf.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import me.xdf.bean.User;
import me.xdf.dao.UserDAO;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet("/UploadAvatarServlet")
@MultipartConfig
public class UploadAvatarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("avatar");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if (fileName == null || fileName.isEmpty()) {
            response.sendRedirect("edit.jsp?error=No file selected");
            return;
        }

        String newFileName = UUID.randomUUID().toString() + "_" + fileName;

        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        System.out.println("------"+fileName+"------"+filePart+"-------"+uploadPath);

        filePart.write(uploadPath + File.separator + newFileName);

        String userIdStr = request.getParameter("userId");
        int userId = 0;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            // 如果用户 ID 无效，重定向到错误页面或显示错误信息
            response.sendRedirect("edit.jsp?error=Invalid user ID");
            return;
        }

        // 确保 userId 是有效的
        if (userId <= 0) {
            response.sendRedirect("edit.jsp?error=Invalid user ID");
            return;
        }

        UserDAO userDAO = new UserDAO();
        List<User> userList =userDAO.getUserById(userId); // 从数据库获取用户
        if (userList != null&& !userList.isEmpty()) {
            User user = userList.get(0);
            user.setImagePath("images/" + newFileName); // 设置用户的新头像路径
            userDAO.updateUser(user);
            String imagePath = uploadPath + newFileName;
            request.getSession().setAttribute("avatarPath",imagePath);
            response.sendRedirect("edit.jsp?id=" + userId);
        } else {
            // 如果找不到用户，重定向到错误页面或显示错误信息
            response.sendRedirect("edit.jsp?error=User not found");
        }
    }
}
