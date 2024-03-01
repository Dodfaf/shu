<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="me.xdf.bean.User" %>
<%@ page import="me.xdf.dao.UserDAO" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>修改</title>
</head>
<body>
<h2>用户修改</h2>

<%
    // 直接在 JSP 中获取参数
    int userId = Integer.parseInt(request.getParameter("id"));
//    String uname = request.getParameter("uname");
    // 通过 ID 从数据库中获取用户信息
    UserDAO userDAO = new UserDAO();
    List<User> user = userDAO.getUserById(userId);
%>

<form action="UpdateUserServlet" method="post">
    <input type="hidden" name="id" value="<%= user.get(0).getId() %>">
    用户姓名: <input type="text" name="username" value="<%= user.get(0).getUsername() %>" required><br>
    密码: <input type="password" name="password" value="<%= user.get(0).getPassword() %>" required><br>
    性别:
    <select name="gender">
        <option value="男" <%= user.get(0).getGender().equals("男") ? "selected" : "" %>>男</option>
        <option value="女" <%= user.get(0).getGender().equals("女") ? "selected" : "" %>>女</option>
    </select><br>
    邮箱: <input type="text" name="email" value="<%= user.get(0).getEmail() %>" required><br>
    年龄: <input type="number" name="age" value="<%= user.get(0).getAge() %>" required><br>
    <input type="submit" value="确认">
</form>
</body>
</html>