package nju.edu.uml.webumldesigner.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LineSvgStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lssid;
    private String svgPosition;
    private String svgWidth;
    private String svgHeight;
    private String svgLeft;
    private String svgTop;

    public Integer getLssid() {
        return lssid;
    }

    public void setLssid(Integer lssid) {
        this.lssid = lssid;
    }

    public String getSvgPosition() {
        return svgPosition;
    }

    public void setSvgPosition(String svgPosition) {
        this.svgPosition = svgPosition;
    }

    public String getSvgWidth() {
        return svgWidth;
    }

    public void setSvgWidth(String svgWidth) {
        this.svgWidth = svgWidth;
    }

    public String getSvgHeight() {
        return svgHeight;
    }

    public void setSvgHeight(String svgHeight) {
        this.svgHeight = svgHeight;
    }

    public String getSvgLeft() {
        return svgLeft;
    }

    public void setSvgLeft(String svgLeft) {
        this.svgLeft = svgLeft;
    }

    public String getSvgTop() {
        return svgTop;
    }

    public void setSvgTop(String svgTop) {
        this.svgTop = svgTop;
    }
}
