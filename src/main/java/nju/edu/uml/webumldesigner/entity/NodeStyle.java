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
    private double styleWidth;
    private double styleHeight;
    private double styleLeft;
    private double styleTop;
    //日后添加层数


    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public double getStyleWidth() {
        return styleWidth;
    }

    public void setStyleWidth(double styleWidth) {
        this.styleWidth = styleWidth;
    }

    public double getStyleHeight() {
        return styleHeight;
    }

    public void setStyleHeight(double styleHeight) {
        this.styleHeight = styleHeight;
    }

    public double getStyleLeft() {
        return styleLeft;
    }

    public void setStyleLeft(double styleLeft) {
        this.styleLeft = styleLeft;
    }

    public double getStyleTop() {
        return styleTop;
    }

    public void setStyleTop(double styleTop) {
        this.styleTop = styleTop;
    }
}
