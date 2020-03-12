package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@ServerEndpoint("/websocket/{uid}")
public class ChatController {
    //TODO 目前只适用于单个房间可能有问题
    private static ConcurrentHashMap<String, ChatController> chatRoomList = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String name;

    @Autowired
    private UserDao userDao;

    @OnOpen
    public void onOpen(@PathParam("uid") Integer uid, Session session) {
        User user = userDao.findUserByUid(uid);
        String userName = user.getUserName();
        chatRoomList.put(userName, this);
        System.out.println(userName + "连接服务器成功");
        System.out.println("客户端连接个数:" + getConnectNum());

        this.session = session;
        this.name = userName;
    }

    @OnClose
    public void onClose() {
        chatRoomList.remove(name);
        System.out.println(name + "断开了服务器连接");
    }

    @OnError
    public void onError(Throwable error) {
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
                entry.getValue().sendMessage(message);
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        if (session.isOpen()) {
            session.getBasicRemote().sendText(message);
        }
    }

    public int getConnectNum() {
        return chatRoomList.size();
    }

}
