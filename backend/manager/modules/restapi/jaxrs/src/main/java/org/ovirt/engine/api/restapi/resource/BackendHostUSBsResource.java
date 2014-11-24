package org.ovirt.engine.api.restapi.resource;

import java.util.List;

import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.HostUSB;
import org.ovirt.engine.api.model.HostUSBs;
import org.ovirt.engine.api.resource.HostUSBsResource;
import org.ovirt.engine.core.common.businessentities.HostUSBDevice;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.common.queries.VdcQueryType;
import org.ovirt.engine.core.compat.Guid;

public class BackendHostUSBsResource
    extends AbstractBackendCollectionResource<HostUSB, HostUSBDevice>
        implements HostUSBsResource {

    public BackendHostUSBsResource(String hostId) {
        super(HostUSB.class, HostUSBDevice.class);
        this.hostId = hostId;
    }

    private String hostId;

    @Override
    public HostUSBs list() {
        return mapCollection(
                getBackendCollection(
                        VdcQueryType.GetHostUSBsById,
                        new IdQueryParameters(Guid.createGuidFromString(hostId))),
                true);
    }

    private HostUSBs mapCollection(List<HostUSBDevice> backendCollection,
            boolean b) {
        HostUSBs hostUSBs = getMapper(List.class, HostUSBs.class).map(backendCollection, null);
        return hostUSBs;
    }

    @Override
    protected Response performRemove(String id) {
        return null;
    }

    @Override
    protected HostUSB doPopulate(HostUSB model, HostUSBDevice entity) {
        return model;
    }

}
