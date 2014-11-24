package org.ovirt.engine.core.common.action;

import org.ovirt.engine.core.compat.Guid;

public class HostUSBParameters extends VdsActionParameters {
    private static final long serialVersionUID = -3242743346669825720L;
    private String deviceName;
    private Guid vmId;

    public HostUSBParameters(String deviceName, Guid vmId) {
        this.deviceName = deviceName;
        this.vmId = vmId;
    }

    public HostUSBParameters() {
        super();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Guid getVmId() {
        return vmId;
    }

}
