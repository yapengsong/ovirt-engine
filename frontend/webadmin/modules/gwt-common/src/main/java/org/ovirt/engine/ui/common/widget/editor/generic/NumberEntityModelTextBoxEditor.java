package org.ovirt.engine.ui.common.widget.editor.generic;

import java.util.Arrays;

import org.ovirt.engine.ui.common.widget.VisibilityRenderer;
import org.ovirt.engine.ui.uicompat.ConstantsManager;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;

public class NumberEntityModelTextBoxEditor<T extends Number> extends EntityModelTextBoxEditor<T> {
    public NumberEntityModelTextBoxEditor(EntityModelTextBox<T> contentWidget, VisibilityRenderer visibilityRenderer) {
        super(contentWidget, visibilityRenderer);
        addKeyUpHandler();
    }

    public NumberEntityModelTextBoxEditor(Renderer<T> renderer, Parser<T> parser) {
        super(renderer, parser);
        addKeyUpHandler();
    }

    public NumberEntityModelTextBoxEditor(Renderer<T> renderer, Parser<T> parser, VisibilityRenderer visibilityRenderer) {
        super(renderer, parser, visibilityRenderer);
        addKeyUpHandler();
    }

    @Override
    protected void handleInvalidState() {
        //Be sure to call super.handleInvalidstate to make sure the editor valid state is properly updated.
        super.handleInvalidState();
        markAsInvalid(Arrays.asList(ConstantsManager.getInstance().getConstants().thisFieldMustContainNumberInvalidReason()));
    }

    private void addKeyUpHandler() {
        getContentWidget().addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                specifiedTypeNumber();
            }
        });
    }

    protected void specifiedTypeNumber() {
        String text = getContentWidget().getText();
        // But not support negative number
        text = text.replaceAll("\\D", "");//$NON-NLS-1$//$NON-NLS-2$
        if ((!"".equals(text)) && (!isNumberType(text))) {
            text = text.substring(0, text.length() - 1);
        }
        getContentWidget().setText(text);
        this.asEditor().flush();
    }

    /**
     * Sub-class must override this method for specified number type
     * @param value
     * @return
     */
    protected boolean isNumberType(String value) {
        return true;
    }

}
