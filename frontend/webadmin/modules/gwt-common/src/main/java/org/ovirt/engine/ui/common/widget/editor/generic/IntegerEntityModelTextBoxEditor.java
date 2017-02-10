package org.ovirt.engine.ui.common.widget.editor.generic;

import java.math.BigInteger;
import java.util.Arrays;

import org.ovirt.engine.ui.common.widget.VisibilityRenderer;
import org.ovirt.engine.ui.common.widget.parser.generic.ToIntEntityModelParser;
import org.ovirt.engine.ui.uicompat.ConstantsManager;

/**
 * Composite Editor that uses {@link org.ovirt.engine.ui.common.widget.editor.generic.EntityModelTextBox}.
 */
public class IntegerEntityModelTextBoxEditor extends NumberEntityModelTextBoxEditor<Integer> {

    public IntegerEntityModelTextBoxEditor(VisibilityRenderer visibilityRenderer) {
        super(new EntityModelTextBox<Integer>(new ToStringEntityModelRenderer<Integer>(), new ToIntEntityModelParser()), visibilityRenderer);
    }

    public IntegerEntityModelTextBoxEditor() {
        super(new ToStringEntityModelRenderer<Integer>(), new ToIntEntityModelParser());
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
            if (value.compareTo(new BigInteger("" + Integer.MIN_VALUE)) >= 0
                    && value.compareTo(new BigInteger("" + Integer.MAX_VALUE)) <= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
