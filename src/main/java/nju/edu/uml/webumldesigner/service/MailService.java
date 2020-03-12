package nju.edu.uml.webumldesigner.service;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2020/3/10
 */
public interface MailService {
    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     */
    String sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件，方便用户点击html用来验证激活账户
     * @param to
     * @param content
     */
    //void sendHtmlMail(String to, String subject, String content);
    String sendFindPwdCode(String to, String subject, String content);
}
