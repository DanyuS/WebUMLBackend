package nju.edu.uml.webumldesigner.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;
    private String propertiesId;
    private String className;
    private String classType;
    private String name;
    private boolean isInstance;
    private boolean isWeak;
    private String compositionType;
    private String conditions;

    //variable&function 建表 加个标记位
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<VarAndFunc> variables;
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<VarAndFunc> functions;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPropertiesId() {
        return propertiesId;
    }

    public void setPropertiesId(String propertiesId) {
        this.propertiesId = propertiesId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInstance() {
        return isInstance;
    }

    public void setInstance(boolean instance) {
        isInstance = instance;
    }

    public boolean isWeak() {
        return isWeak;
    }

    public void setWeak(boolean weak) {
        isWeak = weak;
    }

    public String getCompositionType() {
        return compositionType;
    }

    public void setCompositionType(String compositionType) {
        this.compositionType = compositionType;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public List<VarAndFunc> getVariables() {
        return variables;
    }

    public void setVariables(List<VarAndFunc> variables) {
        this.variables = variables;
    }

    public List<VarAndFunc> getFunctions() {
        return functions;
    }

    public void setFunctions(List<VarAndFunc> functions) {
        this.functions = functions;
    }
}
