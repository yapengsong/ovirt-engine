package org.ovirt.engine.api.restapi.resource;

import java.util.List;

import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.HostUSB;
import org.ovirt.engine.api.model.HostUSBs;
import org.ovirt.engine.api.resource.VmHostUSBsResource;
import org.ovirt.engine.core.common.action.HostUSBParameters;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.businessentities.HostUSBDevice;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.common.queries.VdcQueryType;
import org.ovirt.engine.core.compat.Guid;

public class BackendVmHostUSBsResource
    extends AbstractBackendCollectionResource<HostUSB, HostUSBDevice>
        implements VmHostUSBsResource {

    public BackendVmHostUSBsResource(Guid vmId) {
        super(HostUSB.class, HostUSBDevice.class);
        this.vmId = vmId;
    }

    private Guid vmId;

    @Override
    public HostUSBs list() {
        return mapCollection(
                getBackendCollection(
                        VdcQueryType.GetVmHostUSBs,
                        new IdQueryParameters(vmId)),
                true);
    }

    private HostUSBs mapCollection(List<HostUSBDevice> backendCollection,
            boolean b) {
        HostUSBs hostUSBs = getMapper(List.class, HostUSBs.class).map(backendCollection, null);
        return hostUSBs;
    }

    @Override
    public Response add(HostUSB hostUSB) {
        return performAction(VdcActionType.HostUSBAttach, new HostUSBParameters(hostUSB.getDevname(), vmId));
    }

    @Override
    protected Response performRemove(String id) {
        return performAction(VdcActionType.HostUSBDetach, new HostUSBParameters(id, vmId));
    }

    @Override
    protected HostUSB doPopulate(HostUSB model, HostUSBDevice entity) {
        return model;
    }

}
