package org.ovirt.engine.ui.uicommonweb.models.vms.hostdev;

import java.util.Collection;

import org.ovirt.engine.core.common.businessentities.HostDeviceView;
import org.ovirt.engine.core.common.queries.VdcQueryParametersBase;
import org.ovirt.engine.core.common.queries.VdcQueryReturnValue;
import org.ovirt.engine.core.common.queries.VdcQueryType;
import org.ovirt.engine.ui.frontend.AsyncQuery;
import org.ovirt.engine.ui.frontend.Frontend;
import org.ovirt.engine.ui.frontend.INewAsyncCallback;
import org.ovirt.engine.ui.uicommonweb.models.SearchableListModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostDeviceFilterUtil;

public abstract class HostDeviceListModelBase<E> extends SearchableListModel<E, HostDeviceView> {

    private HostDeviceFilterUtil<HostDeviceView> filter;

    public HostDeviceListModelBase(){
        setFilter(new HostDeviceFilterUtil<HostDeviceView>());
        filter.setUseFilter(true);
    }

    @Override
    protected void onEntityChanged() {
        super.onEntityChanged();
        getSearchCommand().execute();
    }

    @Override
    public boolean supportsServerSideSorting() {
        return false;
    }

    public HostDeviceFilterUtil<HostDeviceView> getFilter() {
        return filter;
    }

    public void setFilter(HostDeviceFilterUtil<HostDeviceView> filter) {
        this.filter = filter;
    }

    @Override
    public void syncSearch(VdcQueryType vdcQueryType, VdcQueryParametersBase vdcQueryParametersBase) {
        AsyncQuery _asyncQuery = new AsyncQuery();
        _asyncQuery.setModel(this);
        _asyncQuery.asyncCallback = new INewAsyncCallback() {
            @Override
            public void onSuccess(Object model, Object ReturnValue) {
                filter.setOrigineItem(((Collection<HostDeviceView>) ((VdcQueryReturnValue) ReturnValue).getReturnValue()));
                filter.initItemAfterFilter();
                execFilterOrNot(filter.isUseFilter());
            }
        };
        vdcQueryParametersBase.setRefresh(getIsQueryFirstTime());
        Frontend.getInstance().runQuery(vdcQueryType, vdcQueryParametersBase, _asyncQuery);
        setIsQueryFirstTime(false);
    }

    // Bind modify the items with the useListFilter'modify
    public void execFilterOrNot(boolean useListFilter) {
        setItems(filter.execFilterOrNot(useListFilter));
    }
}
