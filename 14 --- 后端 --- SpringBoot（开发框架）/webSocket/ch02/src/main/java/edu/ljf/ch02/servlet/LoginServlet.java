package edu.ljf.ch02.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uName = req.getParameter("uName");
        req.getSession().setAttribute("uName", uName);
        // 转发到聊天页面
        req.getRequestDispatcher("/WEB-INF/index.html").forward(req, resp);
    }
}
