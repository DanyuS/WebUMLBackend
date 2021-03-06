package nju.edu.uml.webumldesigner.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class FilePic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fid;
    private String fileId;
    private String fileName;
    private String fileType;
    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name="nidList_1")
    private List<Integer> nidList;
    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name="lidList_1")
    private List<Integer> lidList;

    private Integer refreshTime;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<Integer> getNidList() {
        return nidList;
    }

    public void setNidList(List<Integer> nidList) {
        this.nidList = nidList;
    }

    public List<Integer> getLidList() {
        return lidList;
    }

    public void setLidList(List<Integer> lidList) {
        this.lidList = lidList;
    }

    public Integer getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Integer refreshTime) {
        this.refreshTime = refreshTime;
    }
}
