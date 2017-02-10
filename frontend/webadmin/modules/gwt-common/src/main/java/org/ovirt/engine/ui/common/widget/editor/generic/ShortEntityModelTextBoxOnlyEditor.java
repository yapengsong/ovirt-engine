package org.ovirt.engine.ui.common.widget.editor.generic;

import java.math.BigInteger;
import org.ovirt.engine.ui.common.widget.parser.generic.ToShortEntityModelParser;

public class ShortEntityModelTextBoxOnlyEditor extends NumberEntityModelTextBoxOnlyEditor<Short> {
    public ShortEntityModelTextBoxOnlyEditor() {
        super(new ToStringEntityModelRenderer<Short>(), new ToShortEntityModelParser());
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
