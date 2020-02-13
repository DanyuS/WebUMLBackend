package nju.edu.uml.webumldesigner.service;

import nju.edu.uml.webumldesigner.entity.Properties;

public interface EditService {
    public Properties getPropertiesByPid(Integer pid);

    public boolean createFile(Integer uid, String fileName, String fileType);

    public boolean delFile(Integer uid, Integer fid);

    public boolean addNode(Integer fid, String nodeStyle, String nodeType);

    public boolean delNode(Integer fid, Integer nid);

    public boolean addLine(Integer fid, String relationType, String fromId, String toId, String styles);

    public boolean delLine(Integer fid, Integer lid);

    public boolean addProperties(Integer nid);

    public boolean delProperties(Integer nid, Integer pid);
}
