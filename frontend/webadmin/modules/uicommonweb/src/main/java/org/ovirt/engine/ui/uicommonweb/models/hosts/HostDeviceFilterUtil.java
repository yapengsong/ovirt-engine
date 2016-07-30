package org.ovirt.engine.ui.uicommonweb.models.hosts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.ovirt.engine.core.common.businessentities.HostDevice;
import org.ovirt.engine.core.common.queries.ConfigurationValues;
import org.ovirt.engine.ui.uicommonweb.dataprovider.AsyncDataProvider;

import com.google.gwt.regexp.shared.RegExp;

public class HostDeviceFilterUtil<T extends HostDevice> {

    // Whether use the list filter
    private boolean useFilter;
    private Collection<T> origineItem;
    private Collection<T> itemAfterFilter;

    private String[] keys;
    private Map<String, String> blackList;
    private Map<String, String> whiteList;

    public HostDeviceFilterUtil(){
        initMapAndKeys();
        initItemAfterFilter();
    }

    private void initMapAndKeys(){
        keys = new String[3];
        keys[0] = "name";//$NON-NLS-1$
        keys[1] = "vendor";//$NON-NLS-1$
        keys[2] = "product";//$NON-NLS-1$
        blackList = (Map<String, String>) AsyncDataProvider.getInstance().getConfigValuePreConverted(ConfigurationValues.HostDeviceBlackList);
        whiteList = (Map<String, String>) AsyncDataProvider.getInstance().getConfigValuePreConverted(ConfigurationValues.HostDeviceWhiteList);
    }

    public Collection<T> execFilterOrNot(boolean useFilter) {
        setUseFilter(useFilter);
        if (isUseFilter()) {
            initItemAfterFilter();
            return itemAfterFilter;
        }
        return origineItem;
    }

    // Filter the host devices from name, vendor, product
    // The JSON content's format: {"name":"regexp1","vendor":"regexp2","product":"regexp3"}
    public void initItemAfterFilter() {
        itemAfterFilter = new ArrayList<T>();
        if (origineItem != null) {
            for (T item : origineItem) {
                if((whiteList != null) && !whiteList.isEmpty()){ //白不空
                    if(rowMatch(item, whiteList, keys)) { //白匹配
                        itemAfterFilter.add(item);
                    } else { // 白不匹配
                        if(blackList != null && !blackList.isEmpty()){ //黑不空
                            if(rowMatch(item, blackList, keys)){ //黑匹配

                            } else { //黑不匹配
                                itemAfterFilter.add(item);
                            }
                        } else { //黑为空

                        }
                    }
                } else { //白为空
                    if(blackList != null && !blackList.isEmpty()){ //黑不空
                        if(rowMatch(item, blackList, keys)){ //黑匹配

                        } else { //黑不匹配
                            itemAfterFilter.add(item);
                        }
                    } else { //黑为空
                        itemAfterFilter.add(item);
                    }
                }
            }
        }

    }

    private boolean rowMatch(T item, Map<String, String> filterList, String[] keys) {
        if (filterList != null) {
            boolean nameMatch = cellPattern(item.getName(), getValueFromFilterList(filterList, keys[0]));
            boolean vendorMatch = cellPattern(item.getVendorName(), getValueFromFilterList(filterList, keys[1]));
            boolean productMatch = cellPattern(item.getProductName(), getValueFromFilterList(filterList, keys[2]));
            if (nameMatch || vendorMatch || productMatch) {
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

    public boolean isUseFilter() {
        return useFilter;
    }

    public void setUseFilter(boolean useListFilter) {
        this.useFilter = useListFilter;
    }

    public Collection<T> getOrigineItem() {
        return origineItem;
    }

    public void setOrigineItem(Collection<T> hostDevicesColle) {
        this.origineItem = hostDevicesColle;
    }

    public Collection<T> getItemAfterFilter() {
        return itemAfterFilter;
    }

    public void setItemAfterFilter(Collection<T> itemAfterFilter) {
        this.itemAfterFilter = itemAfterFilter;
    }

}
