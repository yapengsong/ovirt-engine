package org.ovirt.engine.ui.webadmin.section.main.view;

import org.ovirt.engine.core.common.queries.ConfigurationValues;
import org.ovirt.engine.ui.common.view.AbstractPopupView;
import org.ovirt.engine.ui.common.widget.UiCommandButton;
import org.ovirt.engine.ui.common.widget.dialog.PopupNativeKeyPressHandler;
import org.ovirt.engine.ui.common.widget.dialog.SimpleDialogButton;
import org.ovirt.engine.ui.common.widget.dialog.SimpleDialogPanel;
import org.ovirt.engine.ui.common.widget.editor.generic.StringEntityModelTextArea;
import org.ovirt.engine.ui.uicommonweb.dataprovider.AsyncDataProvider;
import org.ovirt.engine.ui.webadmin.ApplicationConstants;
import org.ovirt.engine.ui.webadmin.ApplicationDynamicMessages;
import org.ovirt.engine.ui.webadmin.gin.AssetProvider;
import org.ovirt.engine.ui.webadmin.section.main.presenter.AboutPopupPresenterWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

public class AboutPopupView extends AbstractPopupView<SimpleDialogPanel> implements AboutPopupPresenterWidget.ViewDef {

    interface ViewUiBinder extends UiBinder<SimpleDialogPanel, AboutPopupView> {
        ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
    }

    @UiField
    Label titleLabel;

    @UiField
    InlineLabel versionLabel;

    @UiField
    InlineLabel pollCode;

    @UiField
    StringEntityModelTextArea activeCode;

    @UiField
    SimpleDialogButton closeButton;

    @UiField
    Label copyrightNotice;

    @UiField
    UiCommandButton activatButton;

    @UiField
    HTMLPanel cnterprise;

    @UiField
    HTMLPanel superUser;

    @UiField
    Label licenseNotice;

    @UiField
    InlineLabel pollText;

    @UiField
    InlineLabel activText;

    private final ApplicationDynamicMessages dynamicMessages;

    private final static ApplicationConstants constants = AssetProvider.getConstants();


    @Inject
    public AboutPopupView(EventBus eventBus, ApplicationDynamicMessages dynamicMessages) {
        super(eventBus);
        initWidget(ViewUiBinder.uiBinder.createAndBindUi(this));
        this.dynamicMessages = dynamicMessages;
        String vsersion=(String) AsyncDataProvider.getInstance().getConfigValuePreConverted(ConfigurationValues.EayunOSVersion);
        if(!"Enterprise".equals(vsersion)){//$NON-NLS-1$
            cnterprise.setVisible(false);
        }

        localize();
    }

    void localize() {
        closeButton.setText(constants.closeButtonLabel());
        titleLabel.setText(constants.aboutPopupCaption());
        copyrightNotice.setText(dynamicMessages.copyRightNotice());
        activatButton.setTitle(constants.activation());
        pollText.setText(constants.pollCode());
        activText.setText(constants.activCode());
        //
    }

    @Override
    public void setVersion(String text) {
        versionLabel.setText(dynamicMessages.ovirtVersionAbout(text));
            versionLabel.setText(dynamicMessages.ovirtVersionAbout(text));
    }

    @Override
    public HasClickHandlers getCloseButton() {
        return closeButton;
    }

    @Override
    public HasClickHandlers getCloseIconButton() {
        return asWidget().getCloseIconButton();
    }

    @Override
    public void setPopupKeyPressHandler(PopupNativeKeyPressHandler handler) {
        asWidget().setKeyPressHandler(handler);
    }

    @Override
    public void setPollCode(String text) {
        pollCode.setText(text);
    }

    @Override
    public void setLicenseNotice(String text) {
        licenseNotice.setText(text);
    }


    @Override
    public HasClickHandlers getActivatButton() {
        return activatButton;
    }

    @Override
    public StringEntityModelTextArea getActiveCode() {
        return activeCode;
    }

    @Override
    public HTMLPanel getSuperUser() {
        return superUser;
    }

}
