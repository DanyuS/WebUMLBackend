package nju.edu.uml.webumldesigner.serviceImpl;

import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2020/3/10
 */
@Service
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JavaMailSender mailSender;

    private UserDao userDao;
    /**
     * 配置文件中163邮箱
     */
    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, UserDao userDao) {
        this.mailSender = mailSender;
        this.userDao = userDao;
    }


    /**
     * 发送验证码邮件并新建用户
     * @param email 收件者
     * @param subject 邮件主题
     * @param code 验证码
     */
    @Override
    public String sendSimpleMail(String email, String subject, String code) {
        //email exist
        User user = userDao.findUserByUserEmail(email);
        if(user!=null && user.getUserPassword()!=null){
            return "邮箱已注册";
        }else if(user!=null){
            user.setCode(code);
            userDao.save(user);
        }else{
            //save code
            User newUser = new User();
            newUser.setUserEmail(email);
            newUser.setCode(code);
            String num = String.valueOf(userDao.count() + 1);
            newUser.setUserId("u" + num);
            userDao.save(newUser);
        }
        //send code
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setCc(from);
            //邮件接收人
            simpleMailMessage.setTo(email);
            //邮件主题
            simpleMailMessage.setSubject(subject);
            //邮件内容
            simpleMailMessage.setText("您的验证码是:\n"+code+"\n请填写正确验证码进行注册。\n感谢您的使用。");
            mailSender.send(simpleMailMessage);
            logger.info("邮件已经发送。");
            return "邮件已发送";
        } catch (Exception e) {
            logger.error("邮件发送失败。", e.getMessage());
            e.printStackTrace();
            return "邮件发送失败";
        }

    }

    @Override
    public String sendFindPwdCode(String email, String subject, String code) {
        User user = userDao.findUserByUserEmail(email);
        if(user==null || user.getUserPassword()==null){
            return "邮箱未注册";
        }
        user.setFindPwdCode(code);
        user.setPwdCodeValid(true);
        userDao.save(user);

        //send code
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setCc(from);
            //邮件接收人
            simpleMailMessage.setTo(email);
            //邮件主题
            simpleMailMessage.setSubject(subject);
            //邮件内容
            simpleMailMessage.setText("您的验证码是:\n"+code+"\n请填写正确验证码进行密码重置。\n感谢您的使用。");
            mailSender.send(simpleMailMessage);
            logger.info("邮件已经发送。");
            return "邮件已发送";
        } catch (Exception e) {
            logger.error("邮件发送失败。", e.getMessage());
            e.printStackTrace();
            return "邮件发送失败";
        }
    }
}
