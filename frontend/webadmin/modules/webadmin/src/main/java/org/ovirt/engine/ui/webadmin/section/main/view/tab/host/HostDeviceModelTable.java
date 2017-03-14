package org.ovirt.engine.ui.webadmin.section.main.view.tab.host;

import org.ovirt.engine.core.common.businessentities.HostDeviceView;
import org.ovirt.engine.ui.common.system.ClientStorage;
import org.ovirt.engine.ui.common.uicommon.model.SearchableTableModelProvider;
import org.ovirt.engine.ui.common.widget.uicommon.hosts.hostdev.HostDeviceFilterWidget;
import org.ovirt.engine.ui.uicommonweb.models.vms.hostdev.HostDeviceListModel;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;

public class HostDeviceModelTable extends HostDeviceModelBaseTable<HostDeviceListModel> {

    private HostDeviceFilterWidget filterPanel;

    public HostDeviceModelTable(
            SearchableTableModelProvider<HostDeviceView, HostDeviceListModel> modelProvider,
            EventBus eventBus, ClientStorage clientStorage) {
        super(modelProvider, eventBus, clientStorage);
        filterPanel = new HostDeviceFilterWidget();

        initTableOverhead();
    }

    protected void initTableOverhead() {
        filterPanel.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                getModel().setFilter(filterPanel.getCheckBox().getValue());
            }
        });
        getTable().setTableOverhead(filterPanel);
        getTable().setTableTopMargin(20);
    }
}
