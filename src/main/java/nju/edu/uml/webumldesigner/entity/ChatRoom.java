package nju.edu.uml.webumldesigner.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;
    private Integer gid;
    private Integer uid;
    private LocalDate chatTime;
    private String chatContent;

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public LocalDate getChatTime() {
        return chatTime;
    }

    public void setChatTime(LocalDate chatTime) {
        this.chatTime = chatTime;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
}
