package nju.edu.uml.webumldesigner.repository;

import java.util.List;

public class User {
    private int uid;
    private String userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private List<Integer> fidList;
    private String editable;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Integer> getFidList() {
        return fidList;
    }

    public void setFidList(List<Integer> fidList) {
        this.fidList = fidList;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }
}
