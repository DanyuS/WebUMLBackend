package nju.edu.uml.webumldesigner.serviceImpl;

import nju.edu.uml.webumldesigner.dao.PropertiesDao;
import nju.edu.uml.webumldesigner.entity.Properties;
import nju.edu.uml.webumldesigner.service.EditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditServiceImpl implements EditService {
    @Autowired
    private PropertiesDao propertiesDao;

    @Override
    public Properties getPropertiesByPid(int pid) {
        return null;
    }

    @Override
    public boolean addProperties(String propertiesId) {
        //存入数据库
        Properties properties = new Properties();
        properties.setPropertiesId(propertiesId);
        Properties result = propertiesDao.save(properties);
        //判断是否存入
        if (result.getPid() >= 0) {
            return true;
        }
        return false;
    }
}
