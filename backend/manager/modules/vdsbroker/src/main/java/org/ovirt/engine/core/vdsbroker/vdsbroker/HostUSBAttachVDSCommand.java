package org.ovirt.engine.core.vdsbroker.vdsbroker;

import org.ovirt.engine.core.common.vdscommands.VdsAndHostUSBVDSParametersBase;

public class HostUSBAttachVDSCommand<P extends VdsAndHostUSBVDSParametersBase> extends VdsBrokerCommand<P> {

    public HostUSBAttachVDSCommand(P parameters) {
        super(parameters);
    }

    @Override
    protected void executeVdsBrokerCommand() {
        status = getBroker().hostUsbAttach(getParameters().getVmId().toString(), getParameters().getDeviveName());
        proceedProxyReturnValue();
    }

}
