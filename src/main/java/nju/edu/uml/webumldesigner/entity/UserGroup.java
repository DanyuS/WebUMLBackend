package nju.edu.uml.webumldesigner.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;
    private String groupId;
    private String groupName;
    @ElementCollection
    private List<Integer> invitedUidList;
    @ElementCollection
    private List<String> invitedUserNameList;
    @ElementCollection
    private List<Integer> invitingUidList;
    @ElementCollection
    private List<String> invitingUserNameList;
    @ElementCollection
    private List<Integer> fidList;
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

    public List<Integer> getInvitedUidList() {
        return invitedUidList;
    }

    public void setInvitedUidList(List<Integer> invitedUidList) {
        this.invitedUidList = invitedUidList;
    }

    public List<String> getInvitedUserNameList() {
        return invitedUserNameList;
    }

    public void setInvitedUserNameList(List<String> invitedUserNameList) {
        this.invitedUserNameList = invitedUserNameList;
    }

    public List<Integer> getInvitingUidList() {
        return invitingUidList;
    }

    public void setInvitingUidList(List<Integer> invitingUidList) {
        this.invitingUidList = invitingUidList;
    }

    public List<String> getInvitingUserNameList() {
        return invitingUserNameList;
    }

    public void setInvitingUserNameList(List<String> invitingUserNameList) {
        this.invitingUserNameList = invitingUserNameList;
    }

    public List<Integer> getFidList() {
        return fidList;
    }

    public void setFidList(List<Integer> fidList) {
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
