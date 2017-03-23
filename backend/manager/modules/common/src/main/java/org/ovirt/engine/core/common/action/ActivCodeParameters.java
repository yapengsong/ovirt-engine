package org.ovirt.engine.core.common.action;

public class ActivCodeParameters extends VmOperationParameterBase {
    private String activCode;

    public ActivCodeParameters() {
    }

    public ActivCodeParameters(String activCode) {
        this.activCode = activCode;
    }



    public String getActivCode() {
        return activCode;
    }

    public void setActivCode(String activCode) {
        this.activCode = activCode;
    }



}
