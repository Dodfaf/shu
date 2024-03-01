<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="me.xdf.controller.LoginServlet" %>
<%@ page import="me.xdf.controller.captchaServlet" %>
<html>
<head>
    <title>登录</title>
    <!-- 在HTML文档中添加Content Security Policy头 -->
    <meta http-equiv="Content-Security-Policy" content="default-src 'self'; script-src 'self' https://trusted-scripts.com">
</head>
<body>
<c:if test="${error == 1}">
    <p style="color: red;">账号或密码错误！</p>
</c:if>
<c:if test="${error == 2}">
    <p style="color: red;">验证码错误！</p>
</c:if>

<div align="center">
    <h1>登 录 页 面 </h1>
        <form action="LoginServlet" method="post">
            <table>
                <tr>
                    <td><label for="username" id="username">用户名:</label> </td>
                </tr>
                <tr>
                    <td> <input type="text" name="username" oninput="formatUsername(this)" required></td>
                </tr>
                <tr>
                    <td><label for="password" id="password">密码:</label> </td>
                </tr>
                <tr>
                    <td> <input type="password" name="password" oninput="validateCaptcha(this)" required></td>
                    <td>只允许输入数字和字母 </td>
                </tr>
                <tr>
                    <td><label for="captcha">验证码:</label> </td>
                </tr>
                <tr>
                    <td> <input type="text" id="captcha" name="captcha" oninput="validateCaptcha(this)" required></td>
                    <td> <img src="captchaServlet" alt="Captcha Image"></td>
                </tr>
                <tr>
                    <td><input type="submit" value="登录"> </td>
                    <td><a href="sign_up.jsp"><input type="button" value="注册"></a>  </td>
                </tr>
            </table>
        </form>
</div>

<script>
    function formatUsername(input) {
        // 正则表达式，只允许输入汉字、字母和数字
        var regex = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/;

        if (!regex.test(input.value)) {
            // 非法字符，清空输入框
            input.value = input.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9]/g, '');
        }
    }

    function validateCaptcha(input) {
        // 正则表达式，只允许输入字母和数字
        var regex = /^[a-zA-Z0-9]+$/;

        if (!regex.test(input.value)) {
            // 非法字符，清空输入框
            input.value = input.value.replace(/[^a-zA-Z0-9]/g, '');
        }
    }
</script>

</body>
</html>