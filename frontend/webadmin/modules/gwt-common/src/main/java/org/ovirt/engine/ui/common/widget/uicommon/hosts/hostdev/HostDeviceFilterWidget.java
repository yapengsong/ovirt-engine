package org.ovirt.engine.ui.common.widget.uicommon.hosts.hostdev;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.ovirt.engine.ui.common.CommonApplicationConstants;
import org.ovirt.engine.ui.common.gin.AssetProvider;
import org.ovirt.engine.ui.common.idhandler.ElementIdHandler;
import org.ovirt.engine.ui.common.idhandler.WithElementId;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;

/**
 * Created by wxt on 10/10/16.
 */
public class HostDeviceFilterWidget extends Composite {

    interface ViewUiBinder extends UiBinder<FlowPanel, HostDeviceFilterWidget> {
        ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
    }

    interface ViewIdHandler extends ElementIdHandler<HostDeviceFilterWidget> {
        ViewIdHandler idHandler = GWT.create(ViewIdHandler.class);
    }

    @UiField
    FlowPanel filterPanel;

    @UiField
    @WithElementId("enableFilter")
    SimplePanel checkBoxContainer;

    @UiField
    @WithElementId("editFilter")
    SimplePanel editButtonContainer;

    @UiField(provided = true)
    CheckBox enableFilter;
    @UiField(provided = true)
    Button editButton;

    private final static CommonApplicationConstants constants = AssetProvider.getConstants();

    @Inject
    public HostDeviceFilterWidget() {
        initFilterWidget();

        initWidget(ViewUiBinder.uiBinder.createAndBindUi(this));
        ViewIdHandler.idHandler.generateAndSetIds(this);
    }

    private void initFilterWidget() {
        enableFilter = new CheckBox();
        enableFilter.setText(constants.enableFilterOfHostDevice());
        enableFilter.setValue(true);
        editButton = new Button();
        editButton.setText(constants.editFilterOfHostDevice());
        editButton.setVisible(false);
    }

    public void addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
        enableFilter.addValueChangeHandler(handler);
    }

    public CheckBox getCheckBox() {
        return enableFilter;
    }

    public Button getEditButton() {
        return editButton;
    }
}
