<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="me.xdf.bean.User" %>
<%@ page import="me.xdf.dao.UserDAO" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- 在HTML文档中添加Content Security Policy头 -->
<%--    <meta http-equiv="Content-Security-Policy"--%>
<%--          content="default-src 'self'; script-src 'self' https://trusted-scripts.com">--%>

    <title>管理员界面</title>
</head>
<body>
<div align="center">

    <h2>欢迎你，管理员！</h2>
    <h3>用户列表</h3>
    <hr>
    <!-- 分页控件 -->
    <div class="pagination-container">
        <c:if test="${not empty noOfPages and not empty currentPage}">
            <a href="?page=1">首页</a>
            <c:if test="${currentPage > 1}">
                <a href="?page=${currentPage-1}">上一页</a>
            </c:if>
            <c:forEach var="i" begin="1" end="${noOfPages}">
                <c:choose>
                    <c:when test="${i eq currentPage}">
                        <b>${i}</b>
                    </c:when>
                    <c:otherwise>
                        <a href="?page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage < noOfPages}">
                <a href="?page=${currentPage + 1}">下一页</a>
            </c:if>
            <a href="?page=${noOfPages}">末页</a>
        </c:if>
    </div>

    <hr>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>用户姓名</th>
            <th>密码</th>
            <th>性别</th>
            <th>邮箱</th>
            <th>年龄</th>
            <th>操作</th>
        </tr>
        <%
            UserDAO userDAO = new UserDAO();
            List<User> userList =  userDAO.getAllUsers();
        %>

        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.gender}</td>
                <td>${user.email}</td>
                <td>${user.age}</td>
                <td>
                    <a href="./edit.jsp?id=${user.id}">修改</a>
                    <a href="DeleteUserServlet?id=${user.id}">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h3>新增用户</h3>
    <form action="AddUserServlet" method="post">
        <table>
            <tr>
                <td> 用户姓名: <input type="text" name="username" id="username" required>
                    <span id="usernameMessage" style="display: none"></span>
                </td>
            </tr>
            <tr>
                <td> 密码: <input type="password" name="password" required></td>
            </tr>
            <tr>
                <td><label>
                    性别:
                    <select name="gender">
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </label><br></td>
            </tr>
            <tr>
                <td> 邮箱: <input type="text" name="email" required><br></td>
            </tr>
            <tr>
                <td> 年龄: <input type="int" name="age" required><br></td>
            </tr>
            <tr>
                <td><input type="submit" value="添加用户"></td>
            </tr>
        </table>
    </form>

    <h3>用户搜索</h3>
    <label for="searchUsername">搜索用户:</label>
    <input type="text" id="searchUsername" placeholder="输入用户姓名">
    <table id="userTable" style="display: none;">
        <thead>
        <tr>
            <th>ID</th>
            <th>用户姓名</th>
            <th>性别</th>
            <th>邮箱</th>
            <th>年龄</th>
        </tr>
        </thead>
        <tbody id="searchResult">
        </tbody>
    </table>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        var usernameInput = document.getElementById("username");
        usernameInput.addEventListener("blur", function () {
            var username = usernameInput.value;
            // 创建 XMLHttpRequest 对象
            var xhr = new XMLHttpRequest();
            // 配置请求
            xhr.open("GET", "CheckUsernameServlet?username=" + encodeURIComponent(username), true);
            // 注册回调函数
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    // 处理响应
                    var data = xhr.responseText;
                    var messageElement = document.getElementById("usernameMessage");
                    if (data === "true" ) {
                        // 用户名可用
                        messageElement.textContent = "用户名可使用";
                        messageElement.style.color = "blue";
                        // messageElement.style.display = "none";
                    } else {
                        // 用户名重复
                        messageElement.textContent = "用户名已存在！";
                        messageElement.style.color = "red";
                    }
                }
            };
            // 发送请求
            xhr.send();
        });
    });
</script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        var searchInput = document.getElementById("searchUsername");
        var userTable = document.getElementById("userTable");
        var searchResultContainer = document.getElementById("searchResult");
        searchInput.addEventListener("input", function () {
            var username = searchInput.value.trim();
            // 根据是否有输入内容决定是否显示表格
            userTable.style.display = username !== "" ? "table" : "none";
            // 发送根据用户名查询用户列表的 AJAX 请求
            searchUsers(username);
        });
        function searchUsers(username) {
            // 创建 XMLHttpRequest 对象
            var xhr = new XMLHttpRequest();
            // 配置请求
            xhr.open("GET", "SearchServlet?action=search&username=" + encodeURIComponent(username), true);
            // 注册回调函数
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    // 处理响应
                    var data = JSON.parse(xhr.responseText);
                    displaySearchResults(data);
                }
            };
            // 发送请求
            xhr.send();
        }
        function displaySearchResults(userList) {
            // 清空之前的搜索结果
            searchResultContainer.innerHTML = "";
            // 在页面上显示查询到的用户列表
            userList.forEach(function (user) {
                var row = document.createElement("tr");
                row.innerHTML = "<td>" + user.id + "</td><td>" + user.username + "</td><td>" + user.gender + "</td><td>" + user.email + "</td><td>" + user.age + "</td>";
                searchResultContainer.appendChild(row);
            });
        }
    });
</script>

</body>
</html>