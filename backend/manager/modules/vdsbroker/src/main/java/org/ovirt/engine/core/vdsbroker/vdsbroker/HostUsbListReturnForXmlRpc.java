package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.Map;

import org.ovirt.engine.core.utils.log.Log;
import org.ovirt.engine.core.utils.log.LogFactory;

public class HostUsbListReturnForXmlRpc {

    protected Log log = LogFactory.getLog(getClass());

    private static final String STATUS = "status";
    private static final String USB_LIST = "deviceList";

    public StatusForXmlRpc mStatus;
    public Map<String, Map<String, Object>> mInfoList;

    public HostUsbListReturnForXmlRpc(Map<String, Object> innerMap) {
        mStatus = new StatusForXmlRpc((Map<String, Object>) innerMap.get(STATUS));
        if (innerMap.get(USB_LIST) != null) {
            mInfoList = (Map<String, Map<String, Object>>) innerMap.get(USB_LIST);
        }
    }

}
