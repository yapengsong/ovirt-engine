package org.ovirt.engine.ui.uicommonweb.validation;

import org.ovirt.engine.core.compat.LongCompat;
import org.ovirt.engine.ui.uicompat.ConstantsManager;

@SuppressWarnings("unused")
public class LongValidation implements IValidation {
    private long privateMaximum;

    public long getMaximum() {
        return privateMaximum;
    }

    public void setMaximum(long value) {
        privateMaximum = value;
    }

    private long privateMinimum;

    public long getMinimum() {
        return privateMinimum;
    }

    public void setMinimum(long value) {
        privateMinimum = value;
    }

    public LongValidation() {
        setMaximum(Long.MAX_VALUE);
        setMinimum(Long.MIN_VALUE);
    }

    public LongValidation(long min, long max) {
        setMinimum(min);
        setMaximum(max);
    }

    @Override
    public ValidationResult validate(Object value) {
        ValidationResult result = new ValidationResult();

        if (value != null && ((value instanceof String && !((String) value).equals("")) || value instanceof Long)) { //$NON-NLS-1$
            // Do not use org.apache.commons.lang.math.NumberUtils. When the value is invalidation, return null but not
            // default value
            Long longValue = value instanceof String ? LongCompat.tryParse((String) value) : (Long) value;
            String msg = ""; //$NON-NLS-1$
            String prefixMsg =
                    ConstantsManager.getInstance().getConstants().thisFieldMustContainNumberInvalidReason();
            if (longValue == null) {
                result.setSuccess(false);
                msg =
                        ConstantsManager.getInstance()
                                .getMessages()
                                .longValidationNumberBetweenInvalidReason(prefixMsg, getMinimum(), getMaximum());
                result.getReasons().add(msg);
            } else if (longValue < getMinimum() || longValue > getMaximum()) {
                if (getMinimum() != Long.MIN_VALUE && getMaximum() != Long.MAX_VALUE) {
                    msg =
                            ConstantsManager.getInstance()
                                    .getMessages()
                                    .longValidationNumberBetweenInvalidReason(prefixMsg, getMinimum(), getMaximum());
                } else if (getMinimum() != Long.MIN_VALUE) {
                    msg =
                            ConstantsManager.getInstance()
                                    .getMessages()
                                    .longValidationNumberGreaterInvalidReason(prefixMsg, getMinimum());
                } else if (getMaximum() != Long.MAX_VALUE) {
                    msg =
                            ConstantsManager.getInstance()
                                    .getMessages()
                                    .longValidationNumberLessInvalidReason(prefixMsg, getMaximum());
                }

                result.setSuccess(false);
                result.getReasons().add(msg);
            }
        }

        return result;
    }
}
