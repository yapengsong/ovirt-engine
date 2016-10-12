package org.ovirt.engine.ui.uicommonweb.models.vms.hostdev;

import java.util.ArrayList;
import java.util.Collection;
import org.ovirt.engine.core.common.businessentities.HostDeviceView;
import org.ovirt.engine.core.common.queries.VdcQueryParametersBase;
import org.ovirt.engine.core.common.queries.VdcQueryReturnValue;
import org.ovirt.engine.core.common.queries.VdcQueryType;
import org.ovirt.engine.ui.frontend.AsyncQuery;
import org.ovirt.engine.ui.frontend.Frontend;
import org.ovirt.engine.ui.frontend.INewAsyncCallback;
import org.ovirt.engine.ui.uicommonweb.models.SearchableListModel;

public abstract class HostDeviceListModelBase<E> extends SearchableListModel<E, HostDeviceView> {

    private HostDeviceFilterModel<HostDeviceView> filterModel;

    public HostDeviceListModelBase() {
        filterModel = new HostDeviceFilterModel<>();
    }


    public HostDeviceFilterModel<HostDeviceView> getFilterModel() {
        return filterModel;
    }

    public void setFilterModel(HostDeviceFilterModel<HostDeviceView> filterModel) {
        this.filterModel = filterModel;
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

    @Override
    public void syncSearch(VdcQueryType vdcQueryType, VdcQueryParametersBase vdcQueryParametersBase) {
        AsyncQuery _asyncQuery = new AsyncQuery();
        _asyncQuery.setModel(this);
        _asyncQuery.asyncCallback = new INewAsyncCallback() {
            @Override
            public void onSuccess(Object model, Object ReturnValue) {
                Collection<HostDeviceView> result = ((Collection<HostDeviceView>) ((VdcQueryReturnValue) ReturnValue).getReturnValue());
                if(getFilterModel().getEnableFilter().getEntity()){
                    Collection<HostDeviceView> items = new ArrayList<>();
                    for(HostDeviceView item : result){
                        if(getFilterModel().rowMatch(item)){
                            items.add(item);
                        }
                    }
                    setItems(items);
                } else {
                    setItems(result);
                }
            }
        };
        vdcQueryParametersBase.setRefresh(getIsQueryFirstTime());
        Frontend.getInstance().runQuery(vdcQueryType, vdcQueryParametersBase, _asyncQuery);
        setIsQueryFirstTime(false);
    }
}
