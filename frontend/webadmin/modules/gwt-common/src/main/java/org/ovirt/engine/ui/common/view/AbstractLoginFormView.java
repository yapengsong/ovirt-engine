package org.ovirt.engine.ui.common.view;

import java.util.List;

import org.gwtbootstrap3.client.ui.constants.ColumnOffset;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.gwtbootstrap3.client.ui.html.Span;
import org.ovirt.engine.ui.common.css.PatternflyConstants;
import org.ovirt.engine.ui.common.idhandler.WithElementId;
import org.ovirt.engine.ui.common.widget.HasUiCommandClickHandlers;
import org.ovirt.engine.ui.common.widget.PatternflyUiCommandButton;
import org.ovirt.engine.ui.common.widget.editor.ListModelListBoxWithIconEditor;
import org.ovirt.engine.ui.common.widget.editor.generic.StringEntityModelPasswordBoxEditor;
import org.ovirt.engine.ui.common.widget.editor.generic.StringEntityModelTextBoxEditor;
import org.ovirt.engine.ui.common.widget.panel.AlertPanel;
import org.ovirt.engine.ui.common.widget.panel.AlertPanel.Type;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FocusPanel;

/**
 * Base implementation of the login form.
 */
public abstract class AbstractLoginFormView extends AbstractView {

    interface MotdAnchorTemplate extends SafeHtmlTemplates {
        @Template("<a href=\"{0}\" target=\"blank\">{1}</a>")
        SafeHtml anchor(String url, String text);
    }

    public interface Style extends CssResource {
        String loginMessageError();

        String informationMessage();
    }

    private static final String DEFAULT_LOCALE = "default"; //$NON-NLS-1$

    private static MotdAnchorTemplate template;

    @UiField
    public Style style;

    @UiField
    public FocusPanel loginForm;

    @UiField(provided = true)
    @Path("userName.entity")
    @WithElementId("userName")
    public StringEntityModelTextBoxEditor userNameEditor;

    @UiField(provided = true)
    @Path("password.entity")
    @WithElementId("password")
    public StringEntityModelPasswordBoxEditor passwordEditor;

    @UiField(provided = true)
    @Path("profile.selectedItem")
    @WithElementId("profile")
    public ListModelListBoxWithIconEditor<String> profileEditor;

    @UiField
    @WithElementId
    public PatternflyUiCommandButton  loginButton;


    @UiField
    @WithElementId
    public PatternflyUiCommandButton clientButton;


    @UiField
    @WithElementId
    public Anchor btn32;

    @UiField
    @WithElementId
    public Anchor btn64;

    @UiField
    @WithElementId
    public Anchor btnlinux;

    @UiField
    @WithElementId
    public Span titlefield;

    @UiField
    @WithElementId
    public Paragraph txtfield;


    @UiField
    @Ignore
    public AlertPanel errorMessagePanel;

    public AbstractLoginFormView(EventBus eventBus) {

        // We need this code because resetAndFocus is called when userNameEditor
        // is Disabled
        userNameEditor = new StringEntityModelTextBoxEditor() {
            @Override
            public void setEnabled(boolean enabled) {
                super.setEnabled(enabled);
                if (enabled) {
                    userNameEditor.asValueBox().selectAll();
                    userNameEditor.setFocus(true);
                }
            }
            @Override
            public void markAsValid() {
                super.markAsValid();
                getValidatedWidgetStyle().setBorderColor("#E4E4E4");//$NON-NLS-1$
            }
        };

        passwordEditor = new StringEntityModelPasswordBoxEditor() {
            @Override
            public void markAsValid() {
                super.markAsValid();
                getValidatedWidgetStyle().setBorderColor("#E4E4E4");//$NON-NLS-1$
            }
        };

        profileEditor = new ListModelListBoxWithIconEditor<String>() {
            @Override
            public void markAsValid() {
                super.markAsValid();
                getValidatedWidgetStyle().setBorderColor("#E4E4E4");//$NON-NLS-1$
            }
        };
    }

    protected void setStyles() {

        errorMessagePanel.removeIcon();
        errorMessagePanel.setVisible(false);
        errorMessagePanel.setType(Type.DANGER);
        // column-offset = 1
        errorMessagePanel.getWidget().addStyleName(ColumnOffset.XS_1.getCssName());
        // column-size=10
        errorMessagePanel.setWidgetColumnSize(ColumnSize.XS_10);
        errorMessagePanel.getElement().setPropertyString("style", "float:none; margin-bottom: 0px;");//$NON-NLS-1$ //$NON-NLS-2$
        loginButton.getElement().removeClassName("btn btn-default");//$NON-NLS-1$
        clientButton.getElement().removeClassName("btn btn-default");//$NON-NLS-1$
        //userNameEditor.getElement().removeClassName(className)
        userNameEditor.asValueBox().removeStyleName("form-control");//$NON-NLS-1$
        userNameEditor.asValueBox().addStyleName("inpt");//$NON-NLS-1$
        userNameEditor.asWidget().getElement().getStyle().clearBorderStyle();

        passwordEditor.asValueBox().removeStyleName("form-control");//$NON-NLS-1$
        passwordEditor.asValueBox().addStyleName("inpt");//$NON-NLS-1$
        passwordEditor.asWidget().getElement().getStyle().clearBorderStyle();

        profileEditor.asListBox().removeStyleName("gwt-ListBox");//$NON-NLS-1$
        profileEditor.asListBox().removeStyleName("form-control");//$NON-NLS-1$
        profileEditor.asListBox().addStyleName("inps");//$NON-NLS-1$
        profileEditor.addStyleNameToIcon(" right-addon-icon prf-icon-style");//$NON-NLS-1$
        //profileEditor.getIconPanel().getElement().setPropertyString("style", "padding-top: 9px; padding-bottom: 9px; margin-top: 1px;");//$NON-NLS-1$ //$NON-NLS-2$
        profileEditor.asWidget().getElement().getStyle().clearBorderStyle();
        profileEditor.asListBox().removeStyleName("inner-addon-input-pull-left ");//$NON-NLS-1$
        profileEditor.removeIconStyleName("left-addon-icon");//$NON-NLS-1$
        //userNameEditor.asValueBox().add


    }

    MotdAnchorTemplate getTemplate() {
        if (template == null) {
            template = GWT.create(MotdAnchorTemplate.class);
        }
        return template;
    }

    public String getMotdAnchorHtml(String url) {
        return getTemplate().anchor(url, url).asString();
    }

    public void clearErrorMessages() {
        errorMessagePanel.clearMessages();
        errorMessagePanel.setVisible(false);
    }

    public void setErrorMessages(List<SafeHtml> messages) {
        errorMessagePanel.setMessages(messages,
                style.loginMessageError(),
                style.informationMessage(),
                PatternflyConstants.TEMP_LINK_COLOR);
        errorMessagePanel.setVisible(true);
    }

    public void resetAndFocus() {
        userNameEditor.asValueBox().selectAll();
        userNameEditor.asValueBox().setFocus(true);
        clearErrorMessages();

    }

    public HasUiCommandClickHandlers getLoginButton() {
        return loginButton;
    }


    public PatternflyUiCommandButton getClientButton() {
        return clientButton;
    }

    public HasKeyPressHandlers getLoginForm() {
        return loginForm;
    }

    public Span getTitlefield() {
        return titlefield;
    }

    public Paragraph getTxtfield() {
        return txtfield;
    }
    public HasClickHandlers getBtn32() {
        return btn32;
    }

    public HasClickHandlers getBtn64() {
        return btn64;
    }

    public HasClickHandlers getBtnlinux() {
        return btnlinux;
    }
    /**
     * <p>
     * Force fire change events on the login form fields.
     * </p>
     * <p>
     * Our editors/models don't get populated from forms unless a change event fires on the form (usually on blur when a
     * user types in a value and tabs away).
     * </p>
     * <p>
     * For the login form, there are third-party SSO applications that "paste" credentials into the form. We want to
     * allow those to work by forcing change events on the form when the form is submitted.
     * </p>
     */
    public void fireChangeEventsOnFields() {
        userNameEditor.fireChangeEvent();
        passwordEditor.fireChangeEvent();
        profileEditor.fireChangeEvent();
    }

    /*
    private void initLabels() {
        addLabel(userNameEditor, "");
        addLabel(passwordEditor, "");
    }

    protected void addLabel(AbstractValidatedWidgetWithLabel widget, String holder) {
        widget.hideLabel();
        widget.asWidget().getElement().setPropertyString("placeholder", holder);
    }
    */
}
