package edu.ljf.ws.server;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;

/*
* JSR标准注解
* 标记这个类为WebSocket的端点（服务端）
* value属性指定连接服务端的url地址
*/
@ServerEndpoint("/connect")
public class WebSocketServer {
    /*
    * 打开新连接时触发
    * session是web服务为每个连接的客户端创建的唯一会话，所有WebSocket方法都有这个session参数
    * 主要用于服务端区分不同的客户端
    */
    @OnOpen
    public void onOpen() {
        System.out.println("客户端连接");
    }

    /*
    * 接收到客户端的消息时触发
    */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("接收的消息：" + message);
        session.getBasicRemote().sendText("你也6");
    }

    /*
    * 客户端断开连接时触发
    */
    @OnClose
    public void onClose() {
        System.out.println("客户端断开");
    }
}
