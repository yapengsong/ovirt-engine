package org.ovirt.engine.core.bll;

import java.util.ArrayList;
import java.util.List;

import org.ovirt.engine.core.common.businessentities.HostUSBDevice;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;
import org.ovirt.engine.core.compat.Guid;

public class GetVmHostUSBsQuery<P extends IdQueryParameters> extends QueriesCommandBase<P> {

    public GetVmHostUSBsQuery(P parameters) {
        super(parameters);
    }

    @Override
    protected void executeQueryCommand() {
        Guid vdsId = getDbFacade().getVmDynamicDao().get(getParameters().getId()).getRunOnVds();
        List<HostUSBDevice> vmUSBDevices = new ArrayList<HostUSBDevice>();

        if (!Guid.isNullOrEmpty(vdsId)) {
            List<HostUSBDevice> hostUSBDevices =
                    (List<HostUSBDevice>) getBackend().getResourceManager().RunVdsCommand(
                            VDSCommandType.HostUSBList,
                            new VdsIdVDSCommandParametersBase(vdsId)).getReturnValue();
            for (HostUSBDevice hostUSBDevice : hostUSBDevices) {
                if (hostUSBDevice.getVmId() != null &&
                        hostUSBDevice.getVmId().equals(getParameters().getId())) {
                    vmUSBDevices.add(hostUSBDevice);
                }
            }
        }

        getQueryReturnValue().setReturnValue(vmUSBDevices);
    }

}
