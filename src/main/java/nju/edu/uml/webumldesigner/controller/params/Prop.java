package nju.edu.uml.webumldesigner.controller.params;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2020/3/4
 */
public class Prop {

    private String className;
    private String classType;
    private String name;
    private boolean isInstance;
    private boolean isWeak;
    private String compositionType;
    private String conditions;
    //TODO暂时没有增加类图的func和params

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
}
