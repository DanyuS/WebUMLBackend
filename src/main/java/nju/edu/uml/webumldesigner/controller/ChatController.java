package nju.edu.uml.webumldesigner.controller;

import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@ServerEndpoint("/websocket/{name}")
public class ChatController {
    private static ConcurrentHashMap<String, ChatController> chatRoomList = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String name;

    @OnOpen
    public void open(@PathParam("name") String name, Session session) {
        chatRoomList.put(name, this);
        System.out.println(name + "连接服务器成功");
        System.out.println("客户端连接个数:" + getConnectNum());

        this.session = session;
        this.name = name;
    }

    @OnClose
    public void close() {
        chatRoomList.remove(name);
        System.out.println(name + "断开了服务器连接");
    }

    @OnError
    public void error(Throwable error) {
        error.printStackTrace();
        System.out.println(name + "出现了异常");
    }

    @OnMessage
    public void getMessage(String message) throws IOException {
        System.out.println("收到" + name + ":" + message);
        System.out.println("客户端连接个数:" + getConnectNum());

        Set<Map.Entry<String, ChatController>> entries = chatRoomList.entrySet();
        for (Map.Entry<String, ChatController> entry : entries) {
            if (!entry.getKey().equals(name)) {//将消息转发到其他非自身客户端
                entry.getValue().send(message);

            }
        }
    }

    public void send(String message) throws IOException {
        if (session.isOpen()) {
            session.getBasicRemote().sendText(message);
        }
    }

    public int getConnectNum() {
        return chatRoomList.size();
    }

}
