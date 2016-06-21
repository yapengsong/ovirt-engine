package org.ovirt.engine.ui.common.widget.uicommon.hosts;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.ovirt.engine.core.common.businessentities.HostDeviceView;
import org.ovirt.engine.ui.common.CommonApplicationConstants;
import org.ovirt.engine.ui.common.gin.AssetProvider;
import org.ovirt.engine.ui.common.uicommon.model.SearchableTableModelProvider;
import org.ovirt.engine.ui.uicommonweb.models.vms.hostdev.HostDeviceListModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class HostDeviceFilterView extends Composite {

    private static HostDeviceFilterViewUiBinder uiBinder = GWT.create(HostDeviceFilterViewUiBinder.class);

    interface HostDeviceFilterViewUiBinder extends UiBinder<Widget, HostDeviceFilterView> {
    }

    interface Style extends CssResource {
        String filterPanel();
    }

    public HostDeviceFilterView() {
        initWidget(uiBinder.createAndBindUi(this));

        initDefultWidget();
        initStyle();
        localize();
    }

    private final static CommonApplicationConstants constants = AssetProvider.getConstants();

    @UiField
    Style style;
    @UiField
    FlowPanel filterPanel;
    @UiField
    SimplePanel filterCheckBoxContainer;
    @UiField
    SimplePanel editButtonContainer;

    CheckBox filterCheckBox;
    Button editButton;

    private void initDefultWidget() {
        filterCheckBox = new CheckBox();
        editButton = new Button();
        filterCheckBoxContainer.add(filterCheckBox);
        editButtonContainer.add(editButton);
    }

    private void initStyle() {
        filterPanel.addStyleName(style.filterPanel());
    }

    public void removeEditButton() {
        if (editButton != null) {
            filterPanel.remove(editButtonContainer);
        }
    }

    public Button getEditButton() {
        return editButton;
    }

    public CheckBox getFilterCheckBox() {
        return filterCheckBox;
    }

    public SimplePanel getEditButtonContainer() {
        return editButtonContainer;
    }

    public SimplePanel getFilterCheckBoxContainer() {
        return filterCheckBoxContainer;
    }

    private void localize() {
        getFilterCheckBox().setText(constants.enableFilterOfHostDevice());
        if (getEditButton() != null) {
            getEditButton().setText(constants.editFilterOfHostDevice());
        }
    }

    public void editButtonClickHandler() {
        getEditButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

            }
        });
    }

    public void filterCheckBoxValueChangeHandler(
            final SearchableTableModelProvider<HostDeviceView, HostDeviceListModel> modelProvider) {
        getFilterCheckBox().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                modelProvider.getModel().execFilterOrNot(getFilterCheckBox().getValue());
            }
        });
    }
}
