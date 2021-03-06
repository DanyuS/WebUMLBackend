package nju.edu.uml.webumldesigner.entity;

import javax.persistence.*;

@Entity
public class NodePic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nid;
    private String nodeId;
    private String nodeType;
    @OneToOne
    private NodeStyle nodeStyle;
    @OneToOne
    private Properties properties;

    private Integer uid;//node创建者
    private Integer gid;//创建者所属组，如果没有就为-1
    private Integer fid;

    private String editMethod;

    private String isDeleted;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public NodeStyle getNodeStyle() {
        return nodeStyle;
    }

    public void setNodeStyle(NodeStyle nodeStyle) {
        this.nodeStyle = nodeStyle;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getEditMethod() {
        return editMethod;
    }

    public void setEditMethod(String editMethod) {
        this.editMethod = editMethod;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}
