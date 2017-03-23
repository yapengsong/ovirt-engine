package org.ovirt.engine.ui.webadmin.section.main.presenter;

import java.util.HashMap;
import java.util.Map;

import org.ovirt.engine.core.common.action.ActivCodeParameters;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.action.VdcReturnValueBase;
import org.ovirt.engine.core.common.queries.ConfigurationValues;
import org.ovirt.engine.ui.common.presenter.AbstractPopupPresenterWidget;
import org.ovirt.engine.ui.common.widget.editor.generic.StringEntityModelTextArea;
import org.ovirt.engine.ui.frontend.AsyncQuery;
import org.ovirt.engine.ui.frontend.Frontend;
import org.ovirt.engine.ui.frontend.INewAsyncCallback;
import org.ovirt.engine.ui.uicommonweb.ErrorPopupManager;
import org.ovirt.engine.ui.uicommonweb.TypeResolver;
import org.ovirt.engine.ui.uicommonweb.dataprovider.AsyncDataProvider;
import org.ovirt.engine.ui.uicompat.ConstantsManager;
import org.ovirt.engine.ui.uicompat.FrontendActionAsyncResult;
import org.ovirt.engine.ui.uicompat.IFrontendActionAsyncCallback;
import org.ovirt.engine.ui.webadmin.ApplicationConstants;
import org.ovirt.engine.ui.webadmin.gin.AssetProvider;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;

/**
 * Implements the WebAdmin about dialog.
 */
public class AboutPopupPresenterWidget extends AbstractPopupPresenterWidget<AboutPopupPresenterWidget.ViewDef> {

    private final static ApplicationConstants constants = AssetProvider.getConstants();

    public interface ViewDef extends AbstractPopupPresenterWidget.ViewDef {

        void setVersion(String version);

        void setPollCode(String pollCode);

        void setLicenseNotice(String notice);

        HasClickHandlers getActivatButton();

        StringEntityModelTextArea getActiveCode();

        HTMLPanel getSuperUser();

    }

    @Inject
    public AboutPopupPresenterWidget(EventBus eventBus, ViewDef view) {
        super(eventBus, view);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        String vsersion=(String) AsyncDataProvider.getInstance().getConfigValuePreConverted(ConfigurationValues.EayunOSVersion);

        AsyncQuery _asyncQuery = new AsyncQuery();
        _asyncQuery.setModel(this);
        _asyncQuery.asyncCallback = new INewAsyncCallback() {
            @Override
            public void onSuccess(Object model, Object result) {
                String version = (String) result;

                getView().setVersion(version);

            }
        };

        AsyncDataProvider.getInstance().getRpmVersion(_asyncQuery);

        if("Enterprise".equals(vsersion)){//$NON-NLS-1$

            AsyncQuery _asyncQuery2 = new AsyncQuery();
            _asyncQuery2.setModel(this);
            _asyncQuery2.asyncCallback = new INewAsyncCallback() {
                @Override
                public void onSuccess(Object model, Object result) {
                    String data= result.toString();
                    data=data.replace(" ", "");//$NON-NLS-1$ //$NON-NLS-2$
                    data=data.substring(1, data.length()-1);
                    Map<String, String> map = new HashMap<String, String>();
                    String[] d=data.split(",");//$NON-NLS-1$
                    for (String s : d) {
                        String[] m=s.split("=");//$NON-NLS-1$

                            map.put(m[0], m[1]);
                    }

                    String isActive=map.get("isActive");//$NON-NLS-1$
                    String timeOut=map.get("timeOut");//$NON-NLS-1$
                    String code=map.get("code");//$NON-NLS-1$
                    String isSuper=map.get("isSuper");//$NON-NLS-1$
                    if("false".equals(isSuper)){//$NON-NLS-1$
                        getView().getSuperUser().setVisible(false);
                    }

                    if("true".equals(isActive)){//$NON-NLS-1$
                        getView().setLicenseNotice(constants.activeState());//$NON-NLS-1$
                    }else if("true".equals(timeOut)){//$NON-NLS-1$
                        getView().setLicenseNotice(constants.pastDue());//$NON-NLS-1$
                    }else{
                        getView().setLicenseNotice(constants.trialStatus());//$NON-NLS-1$
                    }
                    getView().setPollCode(code);//$NON-NLS-1$

                }
            };
            AsyncDataProvider.getInstance().getUuid(_asyncQuery2);

        }

    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(getView().getActivatButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                getView().getActiveCode().getValue();
                ActivCodeParameters parameters=new ActivCodeParameters(getView().getActiveCode().getValue());

                Frontend.getInstance().runAction(VdcActionType.ActivCode,
                        parameters,
                        new IFrontendActionAsyncCallback() {
                            @Override
                            public void executed(FrontendActionAsyncResult result) {
                                result.getState();
                                VdcReturnValueBase vrvb = result.getReturnValue();
                                if (vrvb.getSucceeded()) {// 激活成功与否
                                    getView().setLicenseNotice(constants.activeState());//$NON-NLS-1$
                                    final ErrorPopupManager popupManager = (ErrorPopupManager) TypeResolver
                                    .getInstance().resolve(ErrorPopupManager.class);
                                    popupManager.show(ConstantsManager.getInstance()
                                    .getConstants()
                                    .activSucceeded());

                                }
                            }
                        },
                        this);
            }

        }));
    }

}
