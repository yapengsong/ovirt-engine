package org.ovirt.engine.core.bll;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.HostUSBDevice;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;

public class GetHostUSBsByIdQuery<P extends IdQueryParameters> extends QueriesCommandBase<P> {

    public GetHostUSBsByIdQuery(P parameters) {
        super(parameters);
    }

    @Override
    protected void executeQueryCommand() {
        List<HostUSBDevice> hostUSBDevices =
                (List<HostUSBDevice>) getBackend().getResourceManager().RunVdsCommand(
                        VDSCommandType.HostUSBList,
                        new VdsIdVDSCommandParametersBase(getParameters().getId())).getReturnValue();
        getQueryReturnValue().setReturnValue(hostUSBDevices);
    }

}
