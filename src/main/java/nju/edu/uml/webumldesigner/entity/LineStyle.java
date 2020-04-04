package nju.edu.uml.webumldesigner.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LineStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lsid;
    private String stroke;
    private String strokeDasharray;
    private String strokeWidth;
    private String fill;

    public Integer getLsid() {
        return lsid;
    }

    public void setLsid(Integer lsid) {
        this.lsid = lsid;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public String getStrokeDasharray() {
        return strokeDasharray;
    }

    public void setStrokeDasharray(String strokeDasharray) {
        this.strokeDasharray = strokeDasharray;
    }

    public String getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(String strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }
}
