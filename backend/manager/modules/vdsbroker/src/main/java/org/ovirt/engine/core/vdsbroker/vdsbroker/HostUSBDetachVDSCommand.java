package org.ovirt.engine.core.vdsbroker.vdsbroker;

import org.ovirt.engine.core.common.vdscommands.VdsAndHostUSBVDSParametersBase;

public class HostUSBDetachVDSCommand<P extends VdsAndHostUSBVDSParametersBase> extends VdsBrokerCommand<P> {

    public HostUSBDetachVDSCommand(P parameters) {
        super(parameters);
    }

    @Override
    protected void executeVdsBrokerCommand() {
        status = getBroker().hostUsbDetach(getParameters().getVmId().toString(), getParameters().getDeviveName());
        proceedProxyReturnValue();
    }

}
