package org.ovirt.engine.ui.common.widget.editor.generic;

import java.math.BigInteger;
import java.util.Arrays;

import org.ovirt.engine.ui.common.widget.VisibilityRenderer;
import org.ovirt.engine.ui.common.widget.parser.generic.ToShortEntityModelParser;
import org.ovirt.engine.ui.uicompat.ConstantsManager;

/**
 * Composite Editor that uses {@link EntityModelTextBox}.
 */
public class ShortEntityModelTextBoxEditor extends NumberEntityModelTextBoxEditor<Short> {

    public ShortEntityModelTextBoxEditor(VisibilityRenderer visibilityRenderer) {
        super(new EntityModelTextBox<Short>(new ToStringEntityModelRenderer<Short>(), new ToShortEntityModelParser()), visibilityRenderer);
    }

    public ShortEntityModelTextBoxEditor() {
        super(new ToStringEntityModelRenderer<Short>(), new ToShortEntityModelParser());
    }

    @Override
    protected void handleInvalidState() {
        //Be sure to call super.handleInvalidstate to make sure the editor valid state is properly updated.
        super.handleInvalidState();
        markAsInvalid(Arrays.asList(ConstantsManager.getInstance().getConstants().thisFieldMustContainIntegerNumberInvalidReason()));
    }

    @Override
    protected boolean isNumberType(String text) {
        if (text.length() <= 20) {
            BigInteger value = new BigInteger(text);
            if (value.compareTo(new BigInteger("" + Short.MIN_VALUE)) >= 0
                    && value.compareTo(new BigInteger("" + Short.MAX_VALUE)) <= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
