package me.xdf.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter("/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 获取当前请求的路径
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        //某些页面不进行拦截
        if ("/".equals(path)||"/login.jsp".equals(path) || "/LoginServlet".equals(path) || "/captchaServlet".equals(path) || "/sign_up.jsp".equals(path) || "/UserLoginServlet".equals(path)) {
            chain.doFilter(request, response);
            return;
        }
        // 对其他请求进行登陆态检查
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            // 用户未登录，强制跳转到登录页面
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script>alert('请先进行登录,正在跳转...');setTimeout(function(){window.location.href='login.jsp'},1);</script>");
            return;
        } else {
            // 用户已登录，继续执行过滤器链
            chain.doFilter(request, response);
        }
    }
    @Override
    public void destroy() {
    }
}