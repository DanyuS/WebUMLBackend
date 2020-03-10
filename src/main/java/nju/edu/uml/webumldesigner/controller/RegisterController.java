package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.service.MailService;
import nju.edu.uml.webumldesigner.service.RegisterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class RegisterController {
    private final RegisterService registerService;
    private final MailService mailService;

    public RegisterController(RegisterService registerService, MailService mailService) {
        this.registerService = registerService;
        this.mailService = mailService;
    }

    @GetMapping("/register")
    public boolean register(String userName, String userEmail, String userPassword, String code){
        boolean result = registerService.userRegister(userName, userEmail, userPassword, code);
//        boolean result = registerService.userRegister("userName1", "userEmail1", "userPassword1");
        return result;
    }

    @GetMapping("/sendMailCode")
    public String sendMailCode(String userEmail){
        String code = ((int)(Math.random()*10000000))+"";
        String result = mailService.sendSimpleMail(userEmail, "UML绘图网站验证码", code);
        return result;
    }
}
