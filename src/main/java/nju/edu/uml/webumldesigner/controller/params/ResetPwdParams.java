package nju.edu.uml.webumldesigner.controller.params;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2020/3/12
 */
public class ResetPwdParams {
    String userEmail;
    String userPassword;
    String code;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
