package com.ljf.ch09.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

//@WebServlet("/login")
@Controller
public class LoginController extends HttpServlet {
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("come in");
//        String uName = req.getParameter("uName");
//        req.getSession().setAttribute("uName", uName);
//        // 转发到聊天页面
//        req.getRequestDispatcher("/static/index.html").forward(req, resp);
//    }

    @PostMapping("/login")
    public String login(@RequestParam("uName") String uName, HttpSession session) {
        System.out.println("come in");
        session.setAttribute("uName", uName);
        return "redirect:/index.html"; // 重定向到静态页面
    }
}
