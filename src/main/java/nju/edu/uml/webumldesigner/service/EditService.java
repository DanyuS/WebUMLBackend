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

    public Integer addLine(LineParams lineParams);

    public boolean updateLine(LineParams lineParams);

    public boolean delLine(Integer fid, Integer lid);

    public Integer addVarAndFunc(Integer nid, String modifier, String dataType, String name, String params, Integer flag);

    public boolean delVarAndFUnc(Integer nid, Integer vid);

    public boolean upDateVarAndFunc(Integer nid, Integer vid, String modifier, String dataType, String name, String params, Integer flag);

    public boolean importFile(Integer fid, Integer id);//这里的id可以说uid或者gid？

    public List<FilePic> getAllFileByUid(Integer uid);

    public List<NodePic> getAllNodeByFid(Integer fid);

    public List<Line> getAllLineByFid(Integer fid);

    public FilePic getFilePicByFid(Integer fid);

    public NodePic getNodePicByNid(Integer nid);

    public Line getLineByLid(Integer lid);

    public Integer getRefreshTime(Integer fid);
}
