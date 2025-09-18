package edu.ljf.ch02.ws.server;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import jakarta.websocket.server.ServerEndpointConfig.Configurator;

// 握手处理器
// 在握手时获取servlet环境下的对象信息
// 如HttpSession，HttpRequest之类的
// 并把这些玩意保存到WebSocket的Session对象中
public class HandshakeHandler extends Configurator {
    // 重写握手方法
    // request是Servlet中的请求对象，而response也是Servlet中的响应对象
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 获取httpSession对象
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        if (httpSession != null) {
            // 从session中获取登录用户信息
            Object uName = httpSession.getAttribute("uName");
            if (uName != null) {
                // 最后把它保存在WebSocket的对象中
                // 每个session都维护了一个Map
                // getUserProperties方法就是用来得到session的Mao
                sec.getUserProperties().put("uName", uName.toString());
            }
        }

    }
}
