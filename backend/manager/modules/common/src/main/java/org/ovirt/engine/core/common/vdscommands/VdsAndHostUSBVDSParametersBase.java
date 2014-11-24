package org.ovirt.engine.core.common.vdscommands;

import org.ovirt.engine.core.compat.Guid;

public class VdsAndHostUSBVDSParametersBase extends VdsAndVmIDVDSParametersBase {
    private String deviveName;

    public VdsAndHostUSBVDSParametersBase(Guid vdsId, Guid vmId,
            String deviveName) {
        super(vdsId, vmId);
        this.deviveName = deviveName;
    }

    public VdsAndHostUSBVDSParametersBase() {
        super();
        deviveName = "";
    }

    public String getDeviveName() {
        return deviveName;
    }

}
