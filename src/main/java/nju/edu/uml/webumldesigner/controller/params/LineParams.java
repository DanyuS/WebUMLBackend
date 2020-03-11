package nju.edu.uml.webumldesigner.controller.params;


import java.util.List;

public class LineParams {
    private Integer lineId;
    private String relationType;
    private Integer fromId;
    private Integer toId;
    private String text;
    private String markerStart;
    private String markerEnd;
    private List<LinePositionParams> lineList;
    private LinePositionParams startPosition;
    private LinePositionParams endPosition;
    private LineStyleParams lineStyle;
    private LineSvgStyleParams lineSvgStyle;

    private Integer uid;//line创建者
    private Integer gid;//创建者所属组，如果没有就为-1
    private Integer fid;

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

    public List<LinePositionParams> getLineList() {
        return lineList;
    }

    public void setLineList(List<LinePositionParams> lineList) {
        this.lineList = lineList;
    }

    public LinePositionParams getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(LinePositionParams startPosition) {
        this.startPosition = startPosition;
    }

    public LinePositionParams getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(LinePositionParams endPosition) {
        this.endPosition = endPosition;
    }

    public LineStyleParams getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(LineStyleParams lineStyle) {
        this.lineStyle = lineStyle;
    }

    public LineSvgStyleParams getLineSvgStyle() {
        return lineSvgStyle;
    }

    public void setLineSvgStyle(LineSvgStyleParams lineSvgStyle) {
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
}
