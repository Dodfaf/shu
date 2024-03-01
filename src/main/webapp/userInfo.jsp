<%--
  Created by IntelliJ IDEA.
  User: x
  Date: 2023/12/4
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <script src="./js/jquery-1.8.0.min.js"></script>
</head>
<body>
    <div align="center">
        <table>
            <tr> <td><h3>欢迎你,${userList[0].username}</h3> </td></tr>
                <tr>
                    <td> 用户名: ${userList[0].username} </td>
                </tr>
                <tr>
                    <td> <label>性别:${userList[0].gender}</label> </td>
                </tr>
                <tr>
                    <td> 邮箱: ${userList[0].email} </td>
                </tr>
                <tr>
                    <td> 年龄: ${userList[0].age} </td>
                </tr>
        </table>
    </div>
</body>
</html>
