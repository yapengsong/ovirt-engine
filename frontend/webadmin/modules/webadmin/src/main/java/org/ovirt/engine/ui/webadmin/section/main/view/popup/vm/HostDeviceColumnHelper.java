package org.ovirt.engine.ui.webadmin.section.main.view.popup.vm;

import org.ovirt.engine.ui.uicompat.external.StringUtils;
import org.ovirt.engine.ui.webadmin.ApplicationConstants;
import org.ovirt.engine.ui.webadmin.ApplicationMessages;

import com.google.gwt.core.client.GWT;

import java.util.List;

public final class HostDeviceColumnHelper {

    private static final ApplicationConstants constants = GWT.create(ApplicationConstants.class);
    private static final ApplicationMessages messages = GWT.create(ApplicationMessages.class);

    public static String renderNameId(String name, String id) {
        if (StringUtils.isEmpty(name)) {
            return id;
        }
        // we assume that VDSM will never report name != null && id == null
        return messages.nameId(name, id);
    }

    public static String renderVmNamesList(List<String> names) {
        return StringUtils.join(names, ", "); //$NON-NLS-1$
    }

    public static String renderIommuGroup(Integer group) {
        return group == null ? constants.notAvailableLabel() : group.toString();
    }
}
