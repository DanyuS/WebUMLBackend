package nju.edu.uml.webumldesigner.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;
    private String groupId;
    private String groupName;
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<User> invitedUserList;
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<User> invitingUserList;
    @ElementCollection
    private List<Integer> fidList;
    private Integer captainId;
    private String captainEmail;

    private String isDeleted;

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

    public List<User> getInvitedUserList() {
        return invitedUserList;
    }

    public void setInvitedUserList(List<User> invitedUserList) {
        this.invitedUserList = invitedUserList;
    }

    public List<User> getInvitingUserList() {
        return invitingUserList;
    }

    public void setInvitingUserList(List<User> invitingUserList) {
        this.invitingUserList = invitingUserList;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}
