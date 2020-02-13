package nju.edu.uml.webumldesigner.service;

import nju.edu.uml.webumldesigner.entity.Properties;

public interface EditService {
    public Properties getPropertiesByPid(int pid);

    public boolean addProperties(String pid);
}
