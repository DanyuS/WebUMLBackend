package nju.edu.uml.webumldesigner.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    //策略为递增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    private String userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String fidList;
    private String editable;
    private String code;
    private String findPwdCode;
    private Boolean pwdCodeValid;
    @ElementCollection
    private List<Integer> gidList;

    @ElementCollection
    private List<Integer> invitingGidList;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
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

    public String getFidList() {
        return fidList;
    }

    public void setFidList(String fidList) {
        this.fidList = fidList;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public List<Integer> getGidList() {
        return gidList;
    }

    public void setGidList(List<Integer> gidList) {
        this.gidList = gidList;
    }

    public List<Integer> getInvitingGidList() {
        return invitingGidList;
    }

    public void setInvitingGidList(List<Integer> invitingGidList) {
        this.invitingGidList = invitingGidList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFindPwdCode() {
        return findPwdCode;
    }

    public void setFindPwdCode(String findPwdCode) {
        this.findPwdCode = findPwdCode;
    }

    public Boolean getPwdCodeValid() {
        return pwdCodeValid;
    }

    public void setPwdCodeValid(Boolean pwdCodeValid) {
        this.pwdCodeValid = pwdCodeValid;
    }
}
