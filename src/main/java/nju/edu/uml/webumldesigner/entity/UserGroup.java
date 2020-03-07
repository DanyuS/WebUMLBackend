package nju.edu.uml.webumldesigner.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;
    private String groupId;
    private String groupName;
    private String invitedUidList;
    private String invitedUserNameList;
    private String invitingUidList;
    private String invitingUserNameList;
    private String fidList;
    private Integer captainId;
    private String captainEmail;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getInvitedUidList() {
        return invitedUidList;
    }

    public void setInvitedUidList(String invitedUidList) {
        this.invitedUidList = invitedUidList;
    }

    public String getInvitedUserNameList() {
        return invitedUserNameList;
    }

    public void setInvitedUserNameList(String invitedUserNameList) {
        this.invitedUserNameList = invitedUserNameList;
    }

    public String getInvitingUidList() {
        return invitingUidList;
    }

    public void setInvitingUidList(String invitingUidList) {
        this.invitingUidList = invitingUidList;
    }

    public String getInvitingUserNameList() {
        return invitingUserNameList;
    }

    public void setInvitingUserNameList(String invitingUserNameList) {
        this.invitingUserNameList = invitingUserNameList;
    }

    public String getFidList() {
        return fidList;
    }

    public void setFidList(String fidList) {
        this.fidList = fidList;
    }

    public Integer getCaptainId() {
        return captainId;
    }

    public void setCaptainId(Integer captainId) {
        this.captainId = captainId;
    }

    public String getCaptainEmail() {
        return captainEmail;
    }

    public void setCaptainEmail(String captainEmail) {
        this.captainEmail = captainEmail;
    }
}
