package nju.edu.uml.webumldesigner.serviceImpl;

import com.qiniu.cdn.CdnResult;
import com.qiniu.util.Auth;
import com.qiniu.cdn.CdnManager;
import com.qiniu.common.QiniuException;
import nju.edu.uml.webumldesigner.service.QiniuService;
import org.springframework.stereotype.Service;

@Service
public class QiniuServiceImpl implements QiniuService {

    @Override
    public String getToken(String key) {
        System.out.println("key:"+key);
        String accessKey = "Ek8r8-emjAd5FmEKzxZQnFHqyUhTP8Y5p8QNcDSd";
        String secretKey = "g50ZakQN2k0lK39px-f1ZCfwL1o8xDWN3fDQR6E2";
        String bucket = "uml";
        String pickey = key;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, key);
        System.out.println("upToken:" + upToken);
        return upToken;
    }

    @Override
    public void refreshPic(String url) throws QiniuException {
        String [] urls = {url};
        String accessKey = "Ek8r8-emjAd5FmEKzxZQnFHqyUhTP8Y5p8QNcDSd";
        String secretKey = "g50ZakQN2k0lK39px-f1ZCfwL1o8xDWN3fDQR6E2";
        Auth auth = Auth.create(accessKey, secretKey);
        CdnManager c = new CdnManager(auth);
        CdnResult.RefreshResult response = c.refreshUrls(urls);
        System.out.println(response);
    }
}
