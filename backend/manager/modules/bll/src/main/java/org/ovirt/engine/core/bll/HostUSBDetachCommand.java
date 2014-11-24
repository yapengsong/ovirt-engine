package org.ovirt.engine.core.bll;

import org.ovirt.engine.core.common.action.HostUSBParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VdsAndHostUSBVDSParametersBase;
import org.ovirt.engine.core.compat.Guid;

public class HostUSBDetachCommand<T extends HostUSBParameters> extends VdsCommand<T> {

    Guid vdsId = null;

    public HostUSBDetachCommand(T parameters) {
        super(parameters);
    }

    @Override
    protected boolean canDoAction() {
        super.canDoAction();
        vdsId = getDbFacade().getVmDynamicDao().get(getParameters().getVmId()).getRunOnVds();
        if (Guid.isNullOrEmpty(vdsId)) {
            addCanDoActionMessage("Vm is not running.");
            return false;
        }
        return true;
    }

    @Override
    protected void executeCommand() {
        setSucceeded(runVdsCommand(VDSCommandType.HostUSBDetach, new VdsAndHostUSBVDSParametersBase(
                vdsId, getParameters().getVmId(), getParameters().getDeviceName()))
                .getSucceeded());
    }

}
