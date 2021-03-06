package nju.edu.uml.webumldesigner.controller;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.controller.params.*;
import nju.edu.uml.webumldesigner.entity.*;
import nju.edu.uml.webumldesigner.service.EditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        properties.setWeak(props.isWeak());
        properties.setClassName(props.getClassName());
        properties.setClassType(props.getClassType());
        properties.setCompositionType(props.getCompositionType());
        properties.setConditions(props.getConditions());
        properties.setName(props.getName());
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

    @PostMapping("/addLine")
    public Integer addLine(@RequestBody LineParams lineParams) {
        return editService.addLine(lineParams);
//        return editService.addLine(lineParams.getLineId(), lineParams.getRelationType(), lineParams.getFromId(), lineParams.getToId(), lineParams.getText(), lineParams.getMarkerStart(), line.getMarkerEnd(),linePositionList , startPosition, endPosition, lineStyle, lineSvgStyle, lineParams.getUid(), lineParams.getGid());
    }

    @PostMapping("/updateLine")
    public boolean updateLine(@RequestBody LineParams lineParams) {
        return editService.updateLine(lineParams);
    }

    @GetMapping("/delLine")
    public boolean delLine(Integer fid, Integer lid) {
        return editService.delLine(fid, lid);
    }

    @PostMapping("/addVarAndFunc")
    public Integer addVarAndFunc(@RequestBody VarAndFuncParams varAndFuncParams) {
        return editService.addVarAndFunc(varAndFuncParams.getNid(), varAndFuncParams.getModifier(), varAndFuncParams.getDataType(), varAndFuncParams.getName(), varAndFuncParams.getParams(), varAndFuncParams.getFlag());
    }

    @GetMapping("/delVarAndFUnc")
    public boolean delVarAndFUnc(Integer nid, Integer vid) {
        return editService.delVarAndFUnc(nid, vid);
    }

    @PostMapping("/upDateVarAndFunc")
    public boolean upDateVarAndFunc(@RequestBody VarAndFuncParams varAndFuncParams) {
        return editService.upDateVarAndFunc(varAndFuncParams.getNid(), varAndFuncParams.getVid(), varAndFuncParams.getModifier(), varAndFuncParams.getDataType(), varAndFuncParams.getName(), varAndFuncParams.getParams(), varAndFuncParams.getFlag());
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

    @GetMapping("/getRefreshTime")
    public Integer getRefreshTime(Integer fid) {
        return editService.getRefreshTime(fid);
    }

}
