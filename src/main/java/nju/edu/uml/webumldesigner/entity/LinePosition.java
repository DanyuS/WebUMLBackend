package nju.edu.uml.webumldesigner.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LinePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lpid;
    private Integer lpLeft;
    private Integer lpTop;
    private String lpDirection;

    public Integer getLpid() {
        return lpid;
    }

    public void setLpid(Integer lpid) {
        this.lpid = lpid;
    }

    public Integer getLpLeft() {
        return lpLeft;
    }

    public void setLpLeft(Integer lpLeft) {
        this.lpLeft = lpLeft;
    }

    public Integer getLpTop() {
        return lpTop;
    }

    public void setLpTop(Integer lpTop) {
        this.lpTop = lpTop;
    }

    public String getLpDirection() {
        return lpDirection;
    }

    public void setLpDirection(String lpDirection) {
        this.lpDirection = lpDirection;
    }
}
