package org.ovirt.engine.core.bll;

import org.ovirt.engine.core.common.businessentities.HostDeviceView;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.dao.HostDeviceDao;

import java.util.List;

public class GetExtendedHostDevicesByHostIdQuery<P extends IdQueryParameters> extends QueriesCommandBase<P> {

    public GetExtendedHostDevicesByHostIdQuery(P parameters) {
        super(parameters);
    }

    private HostDeviceDao hostDeviceDao = DbFacade.getInstance().getHostDeviceDao();

    @Override
    protected void executeQueryCommand() {
        List<HostDeviceView> hostDeviceList = hostDeviceDao.getExtendedHostDevicesByHostId(getParameters().getId());
        getQueryReturnValue().setReturnValue(hostDeviceList);
    }
}
