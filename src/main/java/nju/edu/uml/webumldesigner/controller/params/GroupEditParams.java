package nju.edu.uml.webumldesigner.controller.params;

public class GroupEditParams {
    String editMethod;
    IdParams idParams;
    NewNodeParam newNodeParam;
    NodeParams nodeParams;
    LineParams lineParams;
    VarAndFuncParams varAndFuncParams;

    public String getEditMethod() {
        return editMethod;
    }

    public void setEditMethod(String editMethod) {
        this.editMethod = editMethod;
    }

    public IdParams getIdParams() {
        return idParams;
    }

    public void setIdParams(IdParams idParams) {
        this.idParams = idParams;
    }

    public NewNodeParam getNewNodeParam() {
        return newNodeParam;
    }

    public void setNewNodeParam(NewNodeParam newNodeParam) {
        this.newNodeParam = newNodeParam;
    }

    public NodeParams getNodeParams() {
        return nodeParams;
    }

    public void setNodeParams(NodeParams nodeParams) {
        this.nodeParams = nodeParams;
    }

    public LineParams getLineParams() {
        return lineParams;
    }

    public void setLineParams(LineParams lineParams) {
        this.lineParams = lineParams;
    }

    public VarAndFuncParams getVarAndFuncParams() {
        return varAndFuncParams;
    }

    public void setVarAndFuncParams(VarAndFuncParams varAndFuncParams) {
        this.varAndFuncParams = varAndFuncParams;
    }
}
