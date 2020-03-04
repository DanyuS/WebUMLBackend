package nju.edu.uml.webumldesigner.controller.params;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2020/3/4
 */
public class NewNodeParam {
    Integer uid;
    Integer gid;
    Integer fid;
    String nodeType;
    Style styles;
    Prop props;

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

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Style getStyles() {
        return styles;
    }

    public void setStyles(Style styles) {
        this.styles = styles;
    }

    public Prop getProps() {
        return props;
    }

    public void setProps(Prop props) {
        this.props = props;
    }
}
