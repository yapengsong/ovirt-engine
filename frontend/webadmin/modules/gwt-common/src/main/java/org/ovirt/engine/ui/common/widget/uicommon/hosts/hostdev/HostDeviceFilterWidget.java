package org.ovirt.engine.ui.common.widget.uicommon.hosts.hostdev;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.ovirt.engine.ui.common.CommonApplicationConstants;
import org.ovirt.engine.ui.common.gin.AssetProvider;
import org.ovirt.engine.ui.common.idhandler.ElementIdHandler;
import org.ovirt.engine.ui.common.idhandler.WithElementId;
import org.ovirt.engine.ui.common.widget.WidgetWithInfo;
import org.ovirt.engine.ui.common.widget.tooltip.TooltipConfig.Width;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
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

    @UiField(provided = true)
    WidgetWithInfo filterline;

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

        filterline = new WidgetWithInfo(enableFilter);
        filterline.setExplanation(SafeHtmlUtils.fromTrustedString(createSpiceInvocationInfo()));
        filterline.addInfoIconStyle("cpv_infoIcon_pfly_fix"); //$NON-NLS-1$
        filterline.setInfoIconTooltipMaxWidth(Width.W420);

        editButton = new Button();
        editButton.setText(constants.editFilterOfHostDevice());
        editButton.setVisible(false);
    }

    private String createSpiceInvocationInfo() {
        return new KeyValueHtmlRowMaker(constants.filter(), constants.filterexplain()).toString();
    }
    private class KeyValueHtmlRowMaker {

        private String html;

        private KeyValueHtmlRowMaker(String key, String val) {
            html = "<b>" + key + "</b>: " + val; //$NON-NLS-1$ //$NON-NLS-2$
        }
        //可以在以后添加新的解释
        public KeyValueHtmlRowMaker append(String key, String val) {
            html += "<br/>" + new KeyValueHtmlRowMaker(key, val).toString(); //$NON-NLS-1$
            return this;
        }

        @Override
        public String toString() {
            return html;
        }
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
