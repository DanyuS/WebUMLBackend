package nju.edu.uml.webumldesigner.entity;

import javax.persistence.*;

@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lid;
    private Integer lineId;
    private String relationType;
    private Integer fromId;
    private Integer toId;
    private String text;
    private String markerStart;
    private String markerEnd;
    @OneToOne
    private LinePosition startPosition;
    @OneToOne
    private LinePosition endPosition;
    @OneToOne
    private LineStyle lineStyle;

    private Integer uid;//line创建者
    private Integer gid;//创建者所属组，如果没有就为-1
    private Integer fid;

    private String editMethod;

    private String isDeleted;

    private String path;

    private String startArrow;
    private String endArrow;
    private String startArrowStyle;
    private String endArrowStyle;
    private String startArrowId;
    private String endArrowId;
    private String startArrowType;
    private String endArrowType;

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMarkerStart() {
        return markerStart;
    }

    public void setMarkerStart(String markerStart) {
        this.markerStart = markerStart;
    }

    public String getMarkerEnd() {
        return markerEnd;
    }

    public void setMarkerEnd(String markerEnd) {
        this.markerEnd = markerEnd;
    }

    public LinePosition getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(LinePosition startPosition) {
        this.startPosition = startPosition;
    }

    public LinePosition getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(LinePosition endPosition) {
        this.endPosition = endPosition;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStartArrow() {
        return startArrow;
    }

    public void setStartArrow(String startArrow) {
        this.startArrow = startArrow;
    }

    public String getEndArrow() {
        return endArrow;
    }

    public void setEndArrow(String endArrow) {
        this.endArrow = endArrow;
    }

    public String getStartArrowStyle() {
        return startArrowStyle;
    }

    public void setStartArrowStyle(String startArrowStyle) {
        this.startArrowStyle = startArrowStyle;
    }

    public String getEndArrowStyle() {
        return endArrowStyle;
    }

    public void setEndArrowStyle(String endArrowStyle) {
        this.endArrowStyle = endArrowStyle;
    }

    public String getStartArrowId() {
        return startArrowId;
    }

    public void setStartArrowId(String startArrowId) {
        this.startArrowId = startArrowId;
    }

    public String getEndArrowId() {
        return endArrowId;
    }

    public void setEndArrowId(String endArrowId) {
        this.endArrowId = endArrowId;
    }

    public String getStartArrowType() {
        return startArrowType;
    }

    public void setStartArrowType(String startArrowType) {
        this.startArrowType = startArrowType;
    }

    public String getEndArrowType() {
        return endArrowType;
    }

    public void setEndArrowType(String endArrowType) {
        this.endArrowType = endArrowType;
    }
}
