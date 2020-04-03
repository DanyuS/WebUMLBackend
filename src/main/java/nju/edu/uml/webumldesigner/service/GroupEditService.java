package nju.edu.uml.webumldesigner.service;

public interface GroupEditService {
    public Integer createFileByGroup(Integer gid, String fileName, String fileType);

    public String updateText(String idType, Integer id, String text);//节点或者线条, 参数修改可能需要其他的方法，有可能要vid
}
