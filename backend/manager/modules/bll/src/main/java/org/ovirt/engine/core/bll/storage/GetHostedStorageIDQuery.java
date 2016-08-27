package org.ovirt.engine.core.bll.storage;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.bll.QueriesCommandBase;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;
import org.ovirt.engine.core.compat.Guid;

public class GetHostedStorageIDQuery<P extends IdQueryParameters> extends QueriesCommandBase<P> {

    public GetHostedStorageIDQuery(P parameters) {
        super(parameters);
    }

    @Override
    protected void executeQueryCommand() {
        Guid guid = (Guid)Backend
                .getInstance()
                .getResourceManager()
                .RunVdsCommand(
                        VDSCommandType.HSMGetHostedStorageID,
                        new VdsIdVDSCommandParametersBase(getParameters().getId())).getReturnValue();
        getQueryReturnValue().setReturnValue(guid);
    }

}
