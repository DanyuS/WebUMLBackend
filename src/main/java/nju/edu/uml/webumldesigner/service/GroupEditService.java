package nju.edu.uml.webumldesigner.service;

import nju.edu.uml.webumldesigner.entity.FilePic;

public interface GroupEditService {
    public Integer createFileByGroup(Integer gid, String fileName, String fileType);

    public boolean deleteFileByGroup(Integer gid, Integer fid);

    public String updateText(String idType, Integer id, String text);//节点或者线条, 参数修改可能需要其他的方法，有可能要vid

    public FilePic undo(Integer fid, Integer uid);

    public FilePic execute(Integer fid, Integer uid);
}
