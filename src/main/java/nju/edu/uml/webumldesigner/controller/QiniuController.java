package nju.edu.uml.webumldesigner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import nju.edu.uml.webumldesigner.service.QiniuService;
@RestController
public class QiniuController {
        private final QiniuService qiniuService;

        @Autowired
        public QiniuController(QiniuService qiniuService) {
            this.qiniuService = qiniuService;
        }

        @PostMapping("/getToken")
        public String getToken() {
           String token=qiniuService.getToken();
           System.out.println("token:"+token);
           return token;
        }
    }
