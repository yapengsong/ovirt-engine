package org.ovirt.engine.ui.webadmin.section.main.view.tab.host;

import org.ovirt.engine.core.common.businessentities.HostDeviceView;
import org.ovirt.engine.ui.common.system.ClientStorage;
import org.ovirt.engine.ui.common.uicommon.model.SearchableTableModelProvider;
import org.ovirt.engine.ui.common.widget.uicommon.hosts.HostDeviceFilterView;
import org.ovirt.engine.ui.uicommonweb.models.vms.hostdev.HostDeviceListModel;

import com.google.gwt.event.shared.EventBus;

public class HostDeviceModelTable extends HostDeviceModelBaseTable<HostDeviceListModel> {

    private HostDeviceFilterView filterPanel = null;

    public HostDeviceModelTable(
            SearchableTableModelProvider<HostDeviceView, HostDeviceListModel> modelProvider,
            EventBus eventBus, ClientStorage clientStorage) {
        super(modelProvider, eventBus, clientStorage);
        initFilterPanel(modelProvider);
        modelProvider.getModel().getFilter().setUseFilter(filterPanel.getFilterCheckBox().getValue());
    }

    private void initFilterPanel(SearchableTableModelProvider<HostDeviceView, HostDeviceListModel> modelProvider) {
        filterPanel = new HostDeviceFilterView();
        filterPanel.removeEditButton(); //remove edit-button, later will add this function
        filterPanel.getFilterCheckBox().setValue(true);
        filterPanel.filterCheckBoxValueChangeHandler(modelProvider);
        getTable().setTableOverhead(filterPanel);
    }

    @Override
    public void initTable() {
        getTable().setTableTopMargin(20);
        super.initTable();
    }

}
