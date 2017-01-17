package org.ovirt.engine.ui.userportal.section.login.view;

import org.ovirt.engine.ui.common.idhandler.ElementIdHandler;
import org.ovirt.engine.ui.common.view.AbstractLoginFormView;
import org.ovirt.engine.ui.uicommonweb.models.userportal.UserPortalLoginModel;
import org.ovirt.engine.ui.userportal.ApplicationConstants;
import org.ovirt.engine.ui.userportal.ApplicationDynamicMessages;
import org.ovirt.engine.ui.userportal.ApplicationTemplates;
import org.ovirt.engine.ui.userportal.gin.AssetProvider;
import org.ovirt.engine.ui.userportal.section.login.presenter.LoginFormPresenterWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.inject.Inject;

public class LoginFormView extends AbstractLoginFormView implements LoginFormPresenterWidget.ViewDef {

    interface Driver extends SimpleBeanEditorDriver<UserPortalLoginModel, LoginFormView> {
    }

    interface ViewUiBinder extends UiBinder<FocusPanel, LoginFormView> {
        ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
    }

    interface ViewIdHandler extends ElementIdHandler<LoginFormView> {
        ViewIdHandler idHandler = GWT.create(ViewIdHandler.class);
    }

    private final Driver driver = GWT.create(Driver.class);

    private final static ApplicationTemplates templates = AssetProvider.getTemplates();
    private final static ApplicationConstants constants = AssetProvider.getConstants();

    @Inject
    public LoginFormView(EventBus eventBus, ApplicationDynamicMessages dynamicMessages) {
        super(eventBus);


        initWidget(ViewUiBinder.uiBinder.createAndBindUi(this));
        localize();

        setStyles();

        ViewIdHandler.idHandler.generateAndSetIds(this);
        driver.initialize(this);
    }

    @Override
    protected void setStyles() {
        super.setStyles();
    }


    void localize() {
        //loginText.setText(constants.loginTextLabel());
        //userNameEditor.setPlaceHolder(constants.loginFormUserNameLabel());
        //passwordEditor.setPlaceHolder(constants.loginFormPasswordLabel());
        //connectAutomaticallyEditor.setLabel(constants.loginFormConnectAutomaticallyLabel());
        loginButton.setLabel(constants.loginButtonLabel());
        userRoleButton.setLabel(constants.userRole());
        adminRoleButton.setLabel(constants.adminRole());
        clientButton.setLabel(constants.clientDownload());
        btn32.setText(constants.btn32());
        btn64.setText(constants.btn64());
        btnlinux.setText(constants.btnlinux());
        userNameEditor.asValueBox().getElement().setPropertyString("placeholder" ,  constants.inputusername());//$NON-NLS-1$
        passwordEditor.asValueBox().getElement().setPropertyString("placeholder" , constants.inputpwd());//$NON-NLS-1$
        titlefield.setText(constants.titlefield());
        txtfield.setText(constants.txtfield());
    }

    @Override
    public void edit(UserPortalLoginModel object) {
        driver.edit(object);
    }

    @Override
    public UserPortalLoginModel flush() {
        return driver.flush();
    }

}
