package org.ovirt.engine.ui.webadmin.section.login.view;

import org.gwtbootstrap3.client.ui.base.modal.ModalDialog;
import org.ovirt.engine.ui.common.view.AbstractView;
import org.ovirt.engine.ui.webadmin.ApplicationDynamicMessages;
import org.ovirt.engine.ui.webadmin.section.login.presenter.LoginSectionPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class LoginSectionView extends AbstractView implements LoginSectionPresenter.ViewDef {

    interface ViewUiBinder extends UiBinder<Widget, LoginSectionView> {
        ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
    }

    @UiField
    ModalDialog loginFormPanel;

    @Inject
    public LoginSectionView(ApplicationDynamicMessages dynamicMessages) {
        initWidget(ViewUiBinder.uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == LoginSectionPresenter.TYPE_SetLoginForm) {
            setPanelContent(loginFormPanel, content);
        } else {
            super.setInSlot(slot, content);
        }
    }

}
