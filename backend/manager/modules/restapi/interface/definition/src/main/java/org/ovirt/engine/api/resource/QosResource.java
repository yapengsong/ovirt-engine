package org.ovirt.engine.api.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.QoS;

@Produces({ ApiMediaType.APPLICATION_XML, ApiMediaType.APPLICATION_JSON, ApiMediaType.APPLICATION_X_YAML })
public interface QosResource extends UpdatableResource<QoS> {

    @DELETE
    public Response remove();


}
