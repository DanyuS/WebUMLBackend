package nju.edu.uml.webumldesigner.controller;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.controller.params.*;
import nju.edu.uml.webumldesigner.entity.*;
import nju.edu.uml.webumldesigner.service.EditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserEditController {
    private final EditService editService;

    public UserEditController(EditService editService) {
        this.editService = editService;
    }

    @GetMapping("/createFile")
    public Integer createFile(Integer uid, String fileName, String fileType) {
        Integer fid = editService.createFile(uid, fileName, fileType);
        return fid;
    }

    @GetMapping("/updateFile")
    public boolean updateFile(Integer fid, String fileName, String fileType) {
        return editService.updateFile(fid, fileName, fileType);
    }

    @GetMapping("/delFile")
    public boolean delFile(Integer uid, Integer fid) {
        boolean result = editService.delFile(uid, fid);
        return result;
    }

    /*@GetMapping("/addNode")
    public Integer addNode(Integer uid, Integer gid, Integer fid, String nodeType, NodeStyle nodeStyle, Properties properties) {
        return editService.addNode(uid, gid, fid, nodeType, nodeStyle, properties);
    }*/

    @PostMapping("/addNode")
    public Integer addNode(@RequestBody NewNodeParam newNodeParam) {
        Style styles = newNodeParam.getStyles();
        NodeStyle nodeStyle = new NodeStyle();
        nodeStyle.setStyleHeight(styles.getHeight());
        nodeStyle.setStyleLeft(styles.getLeft());
        nodeStyle.setStyleTop(styles.getTop());
        nodeStyle.setStyleWidth(styles.getWidth());
        Prop props = newNodeParam.getProps();
        Properties properties = new Properties();
        properties.setInstance(props.isInstance());
        properties.setWeak(properties.isWeak());
        properties.setClassName(properties.getClassName());
        properties.setClassType(properties.getClassType());
        properties.setCompositionType(properties.getCompositionType());
        properties.setConditions(properties.getConditions());
        properties.setName(properties.getName());
        return editService.addNode(newNodeParam.getUid(), newNodeParam.getGid(), newNodeParam.getFid(), newNodeParam.getNodeType(), nodeStyle, properties);
    }


    @PostMapping("/updateNode")
    public boolean updateNode(@RequestBody NodeParams nodeParams) {
        //TODO
        return editService.updateNode(nodeParams.getNid(), nodeParams.getNodeKey(), nodeParams.getKey(), nodeParams.getValue());
    }

    @GetMapping("/delNode")
    public boolean delNode(Integer fid, Integer nid) {
        return editService.delNode(fid, nid);
    }

    @GetMapping("/addLine")
    public boolean addLine(@RequestBody LineParams lineParams) {
        return editService.addLine(lineParams);
//        return editService.addLine(lineParams.getLineId(), lineParams.getRelationType(), lineParams.getFromId(), lineParams.getToId(), lineParams.getText(), lineParams.getMarkerStart(), line.getMarkerEnd(),linePositionList , startPosition, endPosition, lineStyle, lineSvgStyle, lineParams.getUid(), lineParams.getGid());
    }

    @GetMapping("/updateLine")
    public boolean updateLine(Integer lid, String relationType, Integer fromId, Integer toId) {
        //TODO shirting确定格式
        return editService.updateLine(lid, relationType, fromId, toId);
    }

    @GetMapping("/delLine")
    public boolean delLine(Integer fid, Integer lid) {
        return editService.delLine(fid, lid);
    }

//    @GetMapping("/addProperties")
//    public boolean addProperties(Integer nid) {
//        return editService.addProperties(nid);
//    }
//
//    @GetMapping("/updateProperties")
//    public boolean updateProperties(Integer pid) {
//        return editService.updateProperties(pid);
//    }
//
//    @GetMapping("/delProperties")
//    public boolean delProperties(Integer nid, Integer pid) {
//        return editService.delProperties(nid, pid);
//    }

    @GetMapping("/addVarAndFunc")
    public boolean addVarAndFunc(Integer pid, String modifier, String dataType, String name, String params, String propId, Integer flag) {
        return editService.addVarAndFunc(pid, modifier, dataType, name, params, propId, flag);
    }

    @GetMapping("/delVarAndFUnc")
    public boolean delVarAndFUnc(Integer pid, Integer vid) {
        return editService.delVarAndFUnc(pid, vid);
    }

    @GetMapping("/upDateVarAndFunc")
    public boolean upDateVarAndFunc(Integer pid, Integer vid, String modifier, String dataType, String name, String params, String propId, Integer flag) {
        return editService.upDateVarAndFunc(pid, vid, modifier, dataType, name, params, propId, flag);
    }

    @GetMapping("/getAllFilePicByUid")
    public List<FilePic> getAllFilePicByUid(Integer uid) {
        List<FilePic> filePicList = editService.getAllFileByUid(uid);
        return filePicList;
    }

    @GetMapping("/getAllNodeByFid")
    public List<NodePic> getAllNodeByFid(Integer fid) {
        return editService.getAllNodeByFid(fid);
    }

    @GetMapping("/getAllLineByFid")
    public List<Line> getAllLineByFid(Integer fid) {
        return editService.getAllLineByFid(fid);
    }

//    @GetMapping("/getAllPropertiesByNid")
//    public List<Properties> getAllPropertiesByNid(Integer nid) {
//        return editService.getAllPropertiesByNid(nid);
//    }
}
