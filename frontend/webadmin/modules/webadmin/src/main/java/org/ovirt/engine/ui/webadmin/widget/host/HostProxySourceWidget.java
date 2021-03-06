package org.ovirt.engine.ui.webadmin.widget.host;

import org.ovirt.engine.ui.common.css.OvirtCss;
import org.ovirt.engine.ui.common.widget.uicommon.popup.AbstractModelBoundPopupWidget;
import org.ovirt.engine.ui.uicommonweb.models.hosts.FenceProxyModel;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class HostProxySourceWidget extends AbstractModelBoundPopupWidget<FenceProxyModel>
    implements HasValueChangeHandlers<FenceProxyModel>, HasEnabled {

    interface Driver extends SimpleBeanEditorDriver<FenceProxyModel, HostProxySourceWidget> {
    }

    public interface WidgetUiBinder extends UiBinder<Widget, HostProxySourceWidget> {
        WidgetUiBinder uiBinder = GWT.create(WidgetUiBinder.class);
    }

    private final Driver driver = GWT.create(Driver.class);

    @UiField(provided = true)
    PushButton up;

    @UiField(provided = true)
    PushButton down;

    @UiField
    @Path(value = "entity.value")
    Label proxyLabel;

    @UiField
    @Ignore
    Label orderLabel;

    FenceProxyModel model;

    public HostProxySourceWidget() {
        initPushButton();
        initWidget(WidgetUiBinder.uiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    private void initPushButton(){
        up = new PushButton();
        up.setHTML("<span class='fa fa-angle-up' aria-hidden='true'></span>");//$NON-NLS-1$
        down = new PushButton();
        down.setHTML("<span class='fa fa-angle-down' aria-hidden='true'></span>");//$NON-NLS-1$
    }

    @Override
    public void edit(FenceProxyModel object) {
        driver.edit(object);
        this.model = object;
    }

    @Override
    public FenceProxyModel flush() {
        return driver.flush();
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<FenceProxyModel> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            orderLabel.removeStyleName(OvirtCss.LABEL_DISABLED);
            proxyLabel.removeStyleName(OvirtCss.LABEL_DISABLED);
        } else {
            orderLabel.addStyleName(OvirtCss.LABEL_DISABLED);
            proxyLabel.addStyleName(OvirtCss.LABEL_DISABLED);
        }
    }

    @Override
    public boolean isEnabled() {
        return up.isEnabled();
    }

    public void setOrder(int order) {
        orderLabel.setText(String.valueOf(order));
    }

    public void enableUpButton(boolean enable) {
        up.setEnabled(enable);
    }

    public void enableDownButton(boolean enable) {
        down.setEnabled(enable);
    }

    public void addUpClickHandler(ClickHandler handler) {
        up.addClickHandler(handler);
    }

    public void addDownClickHandler(ClickHandler handler) {
        down.addClickHandler(handler);
    }

    public FenceProxyModel getModel() {
        return model;
    }
}
