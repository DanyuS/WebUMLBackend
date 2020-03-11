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
    private String lpLeft;
    private String lpTop;
    private String lpDirection;

    public Integer getLpid() {
        return lpid;
    }

    public void setLpid(Integer lpid) {
        this.lpid = lpid;
    }

    public String getLpLeft() {
        return lpLeft;
    }

    public void setLpLeft(String lpLeft) {
        this.lpLeft = lpLeft;
    }

    public String getLpTop() {
        return lpTop;
    }

    public void setLpTop(String lpTop) {
        this.lpTop = lpTop;
    }

    public String getLpDirection() {
        return lpDirection;
    }

    public void setLpDirection(String lpDirection) {
        this.lpDirection = lpDirection;
    }
}
