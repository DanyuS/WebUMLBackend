package nju.edu.uml.webumldesigner.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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
    @ManyToMany
    private List<LinePosition> lineList;
    @OneToOne
    private LinePosition startPosition;
    @OneToOne
    private LinePosition endPosition;
    @OneToOne
    private LineStyle lineStyle;
    @OneToOne
    private LineSvgStyle lineSvgStyle;

    private Integer uid;//line创建者
    private Integer gid;//创建者所属组，如果没有就为-1
    private Integer fid;

    private String editMethod;

    private String isDeleted;

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

    public List<LinePosition> getLineList() {
        return lineList;
    }

    public void setLineList(List<LinePosition> lineList) {
        this.lineList = lineList;
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

    public LineSvgStyle getLineSvgStyle() {
        return lineSvgStyle;
    }

    public void setLineSvgStyle(LineSvgStyle lineSvgStyle) {
        this.lineSvgStyle = lineSvgStyle;
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
