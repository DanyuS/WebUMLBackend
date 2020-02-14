package nju.edu.uml.webumldesigner.entity;

import javax.persistence.*;

@Entity
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;
    private String propertiesId;
    private String className;
    private String classType;
    private String name;
    private String characters;
    private String userCase;
    private String boarder;
    private String container;
    private String object;
    private String state;
    private String msgSender;
    private String msgReceiver;
    private String synchronizer;
    private String verticalLane;
    private String horizontalLane;
    private String begin;
    private String end;
    private String streamEnd;
    private String history;
    private String detailedHistory;
    private String fork;
    private String interfaces;
    private String constraints;
    private String lifeline;
    private String activation;
    private String deletes;
    private String component;
    private String node;
    private String isInstance;
    private String entity;
    private String relationship;
    private String propType;
    private String packages;
    private String comment;
    private String text;
    private String compositionType;
    private String conditions;

    //variable&function 建表 加个标记位
    @OneToOne
    private VarAndFunc variables;
    @OneToOne
    private VarAndFunc functions;


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

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public String getUserCase() {
        return userCase;
    }

    public void setUserCase(String userCase) {
        this.userCase = userCase;
    }

    public String getBoarder() {
        return boarder;
    }

    public void setBoarder(String boarder) {
        this.boarder = boarder;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsgSender() {
        return msgSender;
    }

    public void setMsgSender(String msgSender) {
        this.msgSender = msgSender;
    }

    public String getMsgReceiver() {
        return msgReceiver;
    }

    public void setMsgReceiver(String msgReceiver) {
        this.msgReceiver = msgReceiver;
    }

    public String getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(String synchronizer) {
        this.synchronizer = synchronizer;
    }

    public String getVerticalLane() {
        return verticalLane;
    }

    public void setVerticalLane(String verticalLane) {
        this.verticalLane = verticalLane;
    }

    public String getHorizontalLane() {
        return horizontalLane;
    }

    public void setHorizontalLane(String horizontalLane) {
        this.horizontalLane = horizontalLane;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStreamEnd() {
        return streamEnd;
    }

    public void setStreamEnd(String streamEnd) {
        this.streamEnd = streamEnd;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getDetailedHistory() {
        return detailedHistory;
    }

    public void setDetailedHistory(String detailedHistory) {
        this.detailedHistory = detailedHistory;
    }

    public String getFork() {
        return fork;
    }

    public void setFork(String fork) {
        this.fork = fork;
    }

    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public String getLifeline() {
        return lifeline;
    }

    public void setLifeline(String lifeline) {
        this.lifeline = lifeline;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getDeletes() {
        return deletes;
    }

    public void setDeletes(String deletes) {
        this.deletes = deletes;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getIsInstance() {
        return isInstance;
    }

    public void setIsInstance(String isInstance) {
        this.isInstance = isInstance;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public VarAndFunc getVariables() {
        return variables;
    }

    public void setVariables(VarAndFunc variables) {
        this.variables = variables;
    }

    public VarAndFunc getFunctions() {
        return functions;
    }

    public void setFunctions(VarAndFunc functions) {
        this.functions = functions;
    }
}
