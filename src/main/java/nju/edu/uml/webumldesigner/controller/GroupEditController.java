package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.service.GroupEditService;
import nju.edu.uml.webumldesigner.serviceImpl.GroupEditServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
public class GroupEditController {

    private static ConcurrentHashMap<String, ConcurrentHashMap<String, GroupEditServiceImpl>> groupEditList = new ConcurrentHashMap<String, ConcurrentHashMap<String, GroupEditServiceImpl>>();


    private final GroupEditService groupEditService;

    public GroupEditController(GroupEditService groupEditService) {
        this.groupEditService = groupEditService;
    }

    @GetMapping("/createFileByGroup")
    public Integer createFileByGroup(Integer gid, String fileName, String fileType) {
        return groupEditService.createFileByGroup(gid, fileName, fileType);
    }
}
