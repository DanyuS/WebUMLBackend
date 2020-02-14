package nju.edu.uml.webumldesigner.service;

import nju.edu.uml.webumldesigner.entity.FilePic;
import nju.edu.uml.webumldesigner.entity.Line;
import nju.edu.uml.webumldesigner.entity.NodePic;
import nju.edu.uml.webumldesigner.entity.Properties;

import java.util.List;

public interface EditService {
    public Properties getPropertiesByPid(Integer pid);

    public boolean createFile(Integer uid, String fileName, String fileType);

    public boolean updateFile(Integer fid, String fileName, String fileType);

    public boolean delFile(Integer uid, Integer fid);

    public boolean addNode(Integer fid, String nodeStyle, String nodeType);

    public boolean updateNode(Integer nid, String nodeStyle, String nodeType);

    public boolean delNode(Integer fid, Integer nid);

    public boolean addLine(Integer fid, String relationType, String fromId, String toId, String styles);

    public boolean updateLine(Integer lid, String relationType, String fromId, String toId, String styles);

    public boolean delLine(Integer fid, Integer lid);

    public boolean addProperties(Integer nid);

    public boolean updateProperties(Integer pid);

    public boolean delProperties(Integer nid, Integer pid);

    public boolean addVarAndFunc(Integer pid, String modifier, String dataType, String name, String params, String propId, Integer flag);

    public boolean delVarAndFUnc(Integer pid, Integer vid);

    public boolean upDateVarAndFunc(Integer pid, Integer vid, String modifier, String dataType, String name, String params, String propId, Integer flag);

    public boolean importFile(Integer fid, Integer id);//这里的id可以说uid或者gid？

    public List<FilePic> getAllFileByUid(Integer uid);

    public List<NodePic> getAllNodeByFid(Integer fid);

    public List<Line> getAllLineByFid(Integer fid);

    public List<Properties> getAllPropertiesByNid(Integer nid);
}
