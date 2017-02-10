package org.ovirt.engine.ui.common.widget.editor.generic;

import java.util.Arrays;

import org.ovirt.engine.ui.common.widget.VisibilityRenderer;
import org.ovirt.engine.ui.uicompat.ConstantsManager;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;

public class NumberEntityModelTextBoxOnlyEditor<T extends Number> extends EntityModelTextBoxOnlyEditor<T> {

    public NumberEntityModelTextBoxOnlyEditor(EntityModelTextBox<T> textBox, VisibilityRenderer visibilityRenderer) {
        super(textBox, visibilityRenderer);
        addKeyUpHandler();
    }

    public NumberEntityModelTextBoxOnlyEditor(Renderer<T> renderer, Parser<T> parser) {
        super(renderer, parser);
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
