package org.ovirt.engine.ui.uicommonweb.models.vms.hostdev;

import org.ovirt.engine.ui.uicommonweb.models.SearchableListModel;

public abstract class HostDeviceListModelBase extends SearchableListModel {
    @Override
    protected void onEntityChanged() {
        super.onEntityChanged();
        getSearchCommand().execute();
    }

    @Override
    public boolean supportsServerSideSorting() {
        return false;
    }
}
