package nju.edu.uml.webumldesigner.util;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.entity.ChatRoom;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtil {
    //房间集合
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketUtil>> chatRoomList = new ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketUtil>>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    @OnOpen
    public void onOpen(Session session, String userName, int flag) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        ChatRoom chatRoom = new Gson().fromJson(message, ChatRoom.class);
        
    }

    public void broadcast(String userName, int flag) throws IOException {
        if (flag == 0) {
            String content = "用户" + userName + "退出房间";
            /////////////////////////////right?
            sendMessage(content);
        } else {
            String content = "用户" + userName + "加入房间";
            /////////////////////////////right?
            sendMessage(content);
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void onClose(Session session) {

    }
}
