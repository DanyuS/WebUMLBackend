package nju.edu.uml.webumldesigner.controller;

import com.qiniu.common.QiniuException;
import nju.edu.uml.webumldesigner.controller.params.QiniuParams;
import nju.edu.uml.webumldesigner.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QiniuController {
    private final QiniuService qiniuService;

    @Autowired
    public QiniuController(QiniuService qiniuService) {
        this.qiniuService = qiniuService;
    }

    @PostMapping("/getToken")
    public String getToken(@RequestBody QiniuParams qiniuParams) {
        System.out.println("key:" + qiniuParams.getKey());
        String token = qiniuService.getToken(qiniuParams.getKey());
        System.out.println("token:" + token);
        return token;
    }

    @PostMapping("/refreshPic")
    public void refreshPic(@RequestBody QiniuParams qiniuParams) throws QiniuException {
        System.out.println("url:" + qiniuParams.getUrl());
        qiniuService.refreshPic(qiniuParams.getUrl());
    }
}
