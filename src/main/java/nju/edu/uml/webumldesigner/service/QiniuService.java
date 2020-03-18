package nju.edu.uml.webumldesigner.service;

import com.qiniu.common.QiniuException;

public interface QiniuService {
//    public Integer createChatRoom(Integer gid);
public String getToken(String key);
public  void refreshPic(String url) throws QiniuException;
}
