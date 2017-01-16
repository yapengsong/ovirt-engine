package org.ovirt.engine.ui.uicommonweb.validation;

import org.ovirt.engine.ui.uicompat.ConstantsManager;

@SuppressWarnings("unused")
public class CustomLengthValidation implements IValidation {
    private int privateMaxLength;

    public int getMaxLength() {
        return privateMaxLength;
    }

    public void setMaxLength(int value) {
        privateMaxLength = value;
    }

    public CustomLengthValidation() {
        setMaxLength(Integer.MAX_VALUE);
    }

    public CustomLengthValidation(int maxLength) {
        setMaxLength(maxLength);
    }

    @Override
    public ValidationResult validate(Object value) {
        ValidationResult result = new ValidationResult();

        if (value != null && value instanceof String && ((String) value).length() > getMaxLength()) {
            result.setSuccess(false);
            result.getReasons().add(ConstantsManager.getInstance()
                    .getMessages()
                    .lenValidationFieldMusnotExceedOther(getMaxLength()));
        }

        return result;
    }
}

