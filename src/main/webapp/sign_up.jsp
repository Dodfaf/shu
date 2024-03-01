<%--
  Created by IntelliJ IDEA.
  User: x
  Date: 2023/11/28
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<div align="center">
    <table>
       <tr> <td><h3>注册页面</h3> </td></tr>
        <form action="AddUserServlet" method="post" enctype="multipart/form-data">
            <tr>
                <td> 用户名: <input type="text" name="username" id="username" required><span id="usernameMessage"></span> </td>
            </tr>
            <tr>
                <td> 密码: <input type="password" name="password" required> </td>
            </tr>
            <tr>
                <td> <label>
                    性别:
                    <select name="gender">
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </label><br> </td>
            </tr>
            <tr>
                <td> 邮箱: <input type="text" name="email" required><br> </td>
            </tr>
            <tr>
                <td> 年龄: <input type="int" name="age" required><br> </td>
            </tr>
            <tr>
                <td>  <input type="submit" value="注册"> </td>
            </tr>
        </form>
    </table>
</div>
</body>
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

</html>
