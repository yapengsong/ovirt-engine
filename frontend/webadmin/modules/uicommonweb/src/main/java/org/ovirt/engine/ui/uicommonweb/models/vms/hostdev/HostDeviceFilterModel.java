package org.ovirt.engine.ui.uicommonweb.models.vms.hostdev;

import java.util.Map;

import org.ovirt.engine.core.common.businessentities.HostDevice;
import org.ovirt.engine.core.common.queries.ConfigurationValues;
import org.ovirt.engine.ui.uicommonweb.dataprovider.AsyncDataProvider;
import org.ovirt.engine.ui.uicommonweb.models.EntityModel;

import com.google.gwt.regexp.shared.RegExp;

/**
 * Created by wxt on 10/10/16.
 */
public class HostDeviceFilterModel<T extends HostDevice> extends EntityModel {

    private EntityModel<Boolean> privateEnableFilter;

    public void setEnableFilter(EntityModel<Boolean> enableFilter) {
        this.privateEnableFilter = enableFilter;
    }

    public EntityModel<Boolean> getEnableFilter() {
        return privateEnableFilter;
    }

    public HostDeviceFilterModel() {
        setEnableFilter(new EntityModel<>(true));
        initMapAndKeys();
    }

    private String[] keys;
    private Map<String, String> blackList;
    private Map<String, String> whiteList;

    private void initMapAndKeys() {
        keys = new String[2];
        keys[0] = "vendor";//$NON-NLS-1$
        keys[1] = "product";//$NON-NLS-1$
        blackList = (Map<String, String>) AsyncDataProvider.getInstance().getConfigValuePreConverted(ConfigurationValues.HostDeviceBlackList);
        whiteList = (Map<String, String>) AsyncDataProvider.getInstance().getConfigValuePreConverted(ConfigurationValues.HostDeviceWhiteList);
    }

    /**
     * 该方法必须在启用过滤之后调用
     *
     * @param item
     *            待匹配的行数据对象
     * @return true:通过过滤; false:未通过过滤
     */
    public boolean rowMatch(T item) {
        if ((whiteList != null) && !whiteList.isEmpty()) { // 白不空
            if (rowMatch(item, whiteList, keys)) { // 白匹配
                return true;
            }
        } else { // 白为空
            if (blackList != null && !blackList.isEmpty()) { // 黑不空
                if (!rowMatch(item, blackList, keys)) { // 黑不匹配
                    return true;
                }
            } else { // 黑为空
                return true;
            }
        }
        return false;
    }

    /**
     * @param enableFilter
     *            是否启用过滤
     * @param item
     *            待匹配的行数据对象
     * @return true:通过过滤; false:未通过过滤
     */
    public boolean rowMatch(boolean enableFilter, T item) {
        getEnableFilter().setEntity(enableFilter);
        return enableFilter ? rowMatch(item) : true;
    }

    private boolean rowMatch(T item, Map<String, String> filterList, String[] keys) {
        if (filterList != null) {
            boolean vendorMatch = cellPattern(item.getVendorName(), getValueFromFilterList(filterList, keys[0]));
            boolean productMatch = cellPattern(item.getProductName(), getValueFromFilterList(filterList, keys[1]));
            if (vendorMatch || productMatch) {
                return true;
            }
        }
        return false;
    }

    private boolean cellPattern(String value, String regexp) {
        if (regexp == null || "".equals(regexp)) {//$NON-NLS-1$
            return false;
        }
        RegExp pattern = RegExp.compile(regexp, "i");//$NON-NLS-1$
        boolean result = pattern.test(value);
        return result;
    }

    private String getValueFromFilterList(Map<String, String> filterList, String key) {
        if (filterList != null) {
            if (filterList.containsKey(key)) {
                return filterList.get(key);
            }
        }
        return null;
    }
}
