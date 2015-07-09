package org.ovirt.engine.ui.webadmin.section.main.view.popup.vm;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.inject.Inject;
import org.ovirt.engine.core.common.businessentities.HostDeviceView;
import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.ui.common.CommonApplicationResources;
import org.ovirt.engine.ui.common.idhandler.ElementIdHandler;
import org.ovirt.engine.ui.common.idhandler.WithElementId;
import org.ovirt.engine.ui.common.view.popup.AbstractModelBoundPopupView;
import org.ovirt.engine.ui.common.widget.HorizontalSplitTable;
import org.ovirt.engine.ui.common.widget.dialog.SimpleDialogPanel;
import org.ovirt.engine.ui.common.widget.editor.EntityModelCellTable;
import org.ovirt.engine.ui.common.widget.editor.ListModelListBoxEditor;
import org.ovirt.engine.ui.common.widget.renderer.NameRenderer;
import org.ovirt.engine.ui.common.widget.table.column.TextColumnWithTooltip;
import org.ovirt.engine.ui.uicommonweb.models.EntityModel;
import org.ovirt.engine.ui.uicommonweb.models.SortedListModel;
import org.ovirt.engine.ui.uicommonweb.models.vms.hostdev.AddVmHostDevicesModel;
import org.ovirt.engine.ui.webadmin.ApplicationConstants;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.hostdev.AddVmHostDevicePopupPresenterWidget;

public class AddVmHostDevicePopupView extends AbstractModelBoundPopupView<AddVmHostDevicesModel> implements AddVmHostDevicePopupPresenterWidget.ViewDef {

    interface Driver extends SimpleBeanEditorDriver<AddVmHostDevicesModel, AddVmHostDevicePopupView> {
    }

    interface ViewUiBinder extends UiBinder<SimpleDialogPanel, AddVmHostDevicePopupView> {
    }

    interface ViewIdHandler extends ElementIdHandler<AddVmHostDevicePopupView> {
    }

    private final Driver driver;

    @UiField(provided = true)
    @Path("pinnedHost.selectedItem")
    @WithElementId
    ListModelListBoxEditor<VDS> pinnedHostEditor;

    @UiField
    @Path("capability.selectedItem")
    @WithElementId
    ListModelListBoxEditor<String> capabilityEditor;

    @UiField(provided = true)
    HorizontalSplitTable splitTable;

    @Ignore
    EntityModelCellTable<SortedListModel<EntityModel<HostDeviceView>>> availableHostDevices;

    @Ignore
    EntityModelCellTable<SortedListModel<EntityModel<HostDeviceView>>> selectedHostDevices;

    private static final ApplicationConstants constants = GWT.create(ApplicationConstants.class);

    @Inject
    public AddVmHostDevicePopupView(EventBus eventBus, Driver driver, ViewUiBinder uiBinder, ViewIdHandler idHandler, CommonApplicationResources resources) {
        super(eventBus, resources);
        initEditors();
        initWidget(uiBinder.createAndBindUi(this));
        idHandler.generateAndSetIds(this);
        this.driver = driver;
        driver.initialize(this);
    }

    private void initEditors() {
        pinnedHostEditor = new ListModelListBoxEditor<VDS>(new NameRenderer<VDS>());
        pinnedHostEditor.setLabel(constants.pinnedHost());

        availableHostDevices = new EntityModelCellTable<SortedListModel<EntityModel<HostDeviceView>>>(true, false, true);
        selectedHostDevices = new EntityModelCellTable<SortedListModel<EntityModel<HostDeviceView>>>(true, false, true);
        splitTable = new HorizontalSplitTable(availableHostDevices,
                selectedHostDevices,
                constants.availableHostDevices(),
                constants.selectedHostDevices());

        initHostDeviceCellTable(availableHostDevices);
        initHostDeviceCellTable(selectedHostDevices);
    }

    private void initHostDeviceCellTable(EntityModelCellTable<SortedListModel<EntityModel<HostDeviceView>>> hostDeviceTable) {
        hostDeviceTable.enableColumnResizing();

        addHostDeviceColumn(hostDeviceTable, constants.deviceName(), "200px", new TextColumnWithTooltip<EntityModel<HostDeviceView>>() { //$NON-NLS-1$
            @Override
            public String getValue(EntityModel<HostDeviceView> hostDevice) {
                return hostDevice.getEntity().getDeviceName();
            }
        });

        addHostDeviceColumn(hostDeviceTable, constants.product(), "350px", new TextColumnWithTooltip<EntityModel<HostDeviceView>>() { //$NON-NLS-1$
            @Override
            public String getValue(EntityModel<HostDeviceView> hostDevice) {
                return HostDeviceColumnHelper.renderNameId(
                        hostDevice.getEntity().getProductName(),
                        hostDevice.getEntity().getProductId());
            }
        });

        addHostDeviceColumn(hostDeviceTable, constants.vendor(), "200px", new TextColumnWithTooltip<EntityModel<HostDeviceView>>() { //$NON-NLS-1$
            @Override
            public String getValue(EntityModel<HostDeviceView> hostDevice) {
                return HostDeviceColumnHelper.renderNameId(
                        hostDevice.getEntity().getVendorName(),
                        hostDevice.getEntity().getVendorId());
            }
        });

        addHostDeviceColumn(hostDeviceTable, constants.currentlyUsedByVm(), "150px", new TextColumnWithTooltip<EntityModel<HostDeviceView>>() { //$NON-NLS-1$
            @Override
            public String getValue(EntityModel<HostDeviceView> hostDevice) {
                return hostDevice.getEntity().getRunningVmName();
            }
        });

        addHostDeviceColumn(hostDeviceTable, constants.attachedToVms(), "150px", new TextColumnWithTooltip<EntityModel<HostDeviceView>>() { //$NON-NLS-1$
            @Override
            public String getValue(EntityModel<HostDeviceView> hostDevice) {
                return HostDeviceColumnHelper.renderVmNamesList(hostDevice.getEntity().getAttachedVmNames());
            }
        });

        addHostDeviceColumn(hostDeviceTable, constants.iommuGroup(), "150px", new TextColumnWithTooltip<EntityModel<HostDeviceView>>() { //$NON-NLS-1$
            @Override
            public String getValue(EntityModel<HostDeviceView> hostDevice) {
                return HostDeviceColumnHelper.renderIommuGroup(hostDevice.getEntity().getIommuGroup());
            }
        });
    }

    private void addHostDeviceColumn(EntityModelCellTable<SortedListModel<EntityModel<HostDeviceView>>> hostDeviceTable,
                                     String header, String width, TextColumnWithTooltip<EntityModel<HostDeviceView>> column) {
        column.makeSortable();
        hostDeviceTable.addColumn(column, header, width);
    }

    @Override
    public void edit(AddVmHostDevicesModel model) {
        splitTable.edit(
                model.getAvailableHostDevices(),
                model.getSelectedHostDevices(),
                model.getAddDeviceCommand(),
                model.getRemoveDeviceCommand());
        driver.edit(model);
    }

    @Override
    public AddVmHostDevicesModel flush() {
        return driver.flush();
    }

    @Override
    public void init(AddVmHostDevicesModel model) {
        availableHostDevices.initModelSortHandler((SortedListModel) model.getAvailableHostDevices());
        selectedHostDevices.initModelSortHandler((SortedListModel) model.getSelectedHostDevices());
    }

}
