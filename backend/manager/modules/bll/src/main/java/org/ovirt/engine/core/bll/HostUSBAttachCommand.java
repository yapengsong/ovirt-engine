package org.ovirt.engine.core.bll;

import org.ovirt.engine.core.common.action.HostUSBParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VdsAndHostUSBVDSParametersBase;
import org.ovirt.engine.core.compat.Guid;

public class HostUSBAttachCommand<T extends HostUSBParameters> extends VdsCommand<T> {

    Guid vdsId = null;

    public HostUSBAttachCommand(T parameters) {
        super(parameters);
    }

    @Override
    protected boolean canDoAction() {
        super.canDoAction();
        log.info("whattt: " + getDbFacade().getVmDynamicDao().get(getParameters().getVmId()));
        vdsId = getDbFacade().getVmDynamicDao().get(getParameters().getVmId()).getRunOnVds();
        if (Guid.isNullOrEmpty(vdsId)) {
            addCanDoActionMessage("Vm is not running.");
            return false;
        }
        return true;
    }

    @Override
    protected void executeCommand() {
        setSucceeded(runVdsCommand(VDSCommandType.HostUSBAttach, new VdsAndHostUSBVDSParametersBase(
                vdsId, getParameters().getVmId(), getParameters().getDeviceName()))
                .getSucceeded());
    }

}
