package nju.edu.uml.webumldesigner.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NodeStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sid;
    private Integer styleWidth;
    private Integer styleHeight;
    private Integer styleLeft;
    private Integer styleTop;
    //日后添加层数


    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getStyleWidth() {
        return styleWidth;
    }

    public void setStyleWidth(Integer styleWidth) {
        this.styleWidth = styleWidth;
    }

    public Integer getStyleHeight() {
        return styleHeight;
    }

    public void setStyleHeight(Integer styleHeight) {
        this.styleHeight = styleHeight;
    }

    public Integer getStyleLeft() {
        return styleLeft;
    }

    public void setStyleLeft(Integer styleLeft) {
        this.styleLeft = styleLeft;
    }

    public Integer getStyleTop() {
        return styleTop;
    }

    public void setStyleTop(Integer styleTop) {
        this.styleTop = styleTop;
    }
}
