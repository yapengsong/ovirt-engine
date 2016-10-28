package org.ovirt.engine.ui.uicompat;

import com.google.gwt.core.client.GWT;

public class CommonApplicationConstantsWithLookupManager {

    private static final CommonApplicationConstantsWithLookupManager INSTANCE = new CommonApplicationConstantsWithLookupManager();
    private final CommonApplicationConstantsWithLookup commonApplicationConstantsWithLookup = GWT.create(CommonApplicationConstantsWithLookup.class);

    private CommonApplicationConstantsWithLookupManager() {
    }

    public static CommonApplicationConstantsWithLookupManager getInstance() {
        return INSTANCE;
    }

    public CommonApplicationConstantsWithLookup getCommonApplicationConstantsWithLookup() {
        return commonApplicationConstantsWithLookup;
    }
}
