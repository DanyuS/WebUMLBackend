package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.service.GroupEditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupEditController {
    private final GroupEditService groupEditService;

    public GroupEditController(GroupEditService groupEditService) {
        this.groupEditService = groupEditService;
    }

    @GetMapping("/createFileByGroup")
    public Integer createFileByGroup(Integer gid, String fileName, String fileType) {
        return groupEditService.createFileByGroup(gid, fileName, fileType);
    }
}
