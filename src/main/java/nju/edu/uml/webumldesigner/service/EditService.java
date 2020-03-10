package nju.edu.uml.webumldesigner.service;

import nju.edu.uml.webumldesigner.controller.params.LineParams;
import nju.edu.uml.webumldesigner.entity.*;

import java.util.List;

public interface EditService {
    public Properties getPropertiesByPid(Integer pid);

    public Integer createFile(Integer uid, String fileName, String fileType);

    public boolean updateFile(Integer fid, String fileName, String fileType);

    public boolean delFile(Integer uid, Integer fid);

    public Integer addNode(Integer uid, Integer gid, Integer fid, String nodeType, NodeStyle nodeStyle, Properties properties);

    public boolean updateNode(Integer nid, String nodeKey, List<String> key, List<String> value);

    public boolean delNode(Integer fid, Integer nid);

//    public Integer addLine(Integer uid, Integer gid, Integer fid, String relationType, String fromId, String toId, String styles);

    public boolean addLine(LineParams lineParams);

    public boolean updateLine(Integer lid, String relationType, Integer fromId, Integer toId);

    public boolean delLine(Integer fid, Integer lid);

//    public boolean addProperties(Integer nid);
//
//    public boolean updateProperties(Integer pid);
//
//    public boolean delProperties(Integer nid, Integer pid);

    public boolean addVarAndFunc(Integer pid, String modifier, String dataType, String name, String params, String propId, Integer flag);

    public boolean delVarAndFUnc(Integer pid, Integer vid);

    public boolean upDateVarAndFunc(Integer pid, Integer vid, String modifier, String dataType, String name, String params, String propId, Integer flag);

    public boolean importFile(Integer fid, Integer id);//这里的id可以说uid或者gid？

    public List<FilePic> getAllFileByUid(Integer uid);

    public List<NodePic> getAllNodeByFid(Integer fid);

    public List<Line> getAllLineByFid(Integer fid);

//    public List<Properties> getAllPropertiesByNid(Integer nid);
}
