package nju.edu.uml.webumldesigner.serviceImpl;

import com.qiniu.util.Auth;
import nju.edu.uml.webumldesigner.service.QiniuService;
import org.springframework.stereotype.Service;

@Service
public class QiniuServiceImpl implements QiniuService {

    @Override
    public String getToken() {
        String accessKey = "Ek8r8-emjAd5FmEKzxZQnFHqyUhTP8Y5p8QNcDSd";
        String secretKey = "g50ZakQN2k0lK39px-f1ZCfwL1o8xDWN3fDQR6E2";
        String bucket = "uml";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println("upToken:" + upToken);
        return upToken;
    }
}
