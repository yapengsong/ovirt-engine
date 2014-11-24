package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.ArrayList;
import java.util.List;

import org.ovirt.engine.core.common.businessentities.HostUSBDevice;
import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;

public class HostUSBListVDSCommand<P extends VdsIdVDSCommandParametersBase> extends VdsBrokerCommand<P> {
    protected HostUsbListReturnForXmlRpc mHostUsbListReturn;

    public HostUSBListVDSCommand(P parameters) {
        super(parameters);
    }

    @Override
    protected void executeVdsBrokerCommand() {
        mHostUsbListReturn = getBroker().hostUsbList();
        proceedProxyReturnValue();
        List<HostUSBDevice> returnUSBDevices = new ArrayList<HostUSBDevice>();
        for (String devname : mHostUsbListReturn.mInfoList.keySet()) {
            HostUSBDevice hostUSBDevice =
                    VdsBrokerObjectsBuilder.buildHostUSBDeviceData(devname, mHostUsbListReturn.mInfoList.get(devname));
            returnUSBDevices.add(hostUSBDevice);
        }
        setReturnValue(returnUSBDevices);
    }

    @Override
    protected StatusForXmlRpc getReturnStatus() {
        return mHostUsbListReturn.mStatus;
    }

}
