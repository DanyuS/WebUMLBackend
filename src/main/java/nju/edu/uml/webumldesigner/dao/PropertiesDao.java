package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.Properties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertiesDao extends JpaRepository<Properties, Integer> {
//    public boolean addProperties();

    public Properties findPropertiesByPid(Integer pid);
//
//    public boolean delPropertiesByPid(int pid);
//
//    public Properties updateProperties();
}
