package org.ovirt.engine.core.bll.hostdev;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ovirt.engine.core.bll.NonTransactiveCommandAttribute;
import org.ovirt.engine.core.bll.RefreshHostInfoCommandBase;
import org.ovirt.engine.core.bll.context.CommandContext;
import org.ovirt.engine.core.common.FeatureSupported;
import org.ovirt.engine.core.common.action.VdsActionParameters;
import org.ovirt.engine.core.common.businessentities.Entities;
import org.ovirt.engine.core.common.businessentities.HostDevice;
import org.ovirt.engine.core.common.businessentities.VmDevice;
import org.ovirt.engine.core.common.businessentities.VmDeviceGeneralType;
import org.ovirt.engine.core.common.errors.VdcBllMessages;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.common.vdscommands.VdsIdAndVdsVDSCommandParametersBase;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.dao.HostDeviceDao;
import org.ovirt.engine.core.dao.VmDeviceDAO;
import org.ovirt.engine.core.utils.transaction.TransactionMethod;
import org.ovirt.engine.core.utils.transaction.TransactionSupport;
import org.ovirt.engine.core.vdsbroker.ResourceManager;

@NonTransactiveCommandAttribute
public class RefreshHostDevicesCommand<T extends VdsActionParameters> extends RefreshHostInfoCommandBase<T> {

    private ResourceManager resourceManager = ResourceManager.getInstance();

    private HostDeviceDao hostDeviceDao = DbFacade.getInstance().getHostDeviceDao();

    private VmDeviceDAO vmDeviceDao = DbFacade.getInstance().getVmDeviceDao();

    private HostDeviceManager hostDeviceManager = HostDeviceManager.getInstance();

    private Map<String, HostDevice> oldMap;

    private Map<String, HostDevice> fetchedMap;

    public RefreshHostDevicesCommand(T parameters) {
        super(parameters);
    }

    public RefreshHostDevicesCommand(T parameters, CommandContext commandContext) {
        super(parameters, commandContext);
    }

    @Override
    protected void executeCommand() {
        if (!FeatureSupported.hostDevicePassthrough(getVds().getVdsGroupCompatibilityVersion())) {
            // Do nothing if the host doesn't support the HostDevListByCaps verb
            setSucceeded(true);
            return;
        }

        VDSReturnValue vdsReturnValue = resourceManager.runVdsCommand(VDSCommandType.HostDevListByCaps, new VdsIdAndVdsVDSCommandParametersBase(getVds()));

        if (!vdsReturnValue.getSucceeded()) {
            return;
        }

        List<HostDevice> fetchedDevices = (List<HostDevice>) vdsReturnValue.getReturnValue();
        List<HostDevice> oldDevices = hostDeviceDao.getHostDevicesByHostId(getVdsId());
        List<VmDevice> vmDevices = vmDeviceDao.getVmDeviceByType(VmDeviceGeneralType.HOSTDEV);

        fetchedMap = Entities.entitiesByName(fetchedDevices);
        oldMap = Entities.entitiesByName(oldDevices);
        Map<String, VmDevice> vmDeviceMap = Entities.vmDevicesByDevice(vmDevices);

        final List<HostDevice> newDevices = new ArrayList<>();
        final List<HostDevice> changedDevices = new ArrayList<>();

        for (Map.Entry<String, HostDevice> entry : fetchedMap.entrySet()) {
            HostDevice device = entry.getValue();
            if (oldMap.containsKey(entry.getKey())) {
                if (!oldMap.get(entry.getKey()).equals(device)) {
                    changedDevices.add(device);
                }
            } else {
                newDevices.add(device);
            }
        }

        final List<HostDevice> removedDevices = new ArrayList<>();
        final List<VmDevice> removedVmDevices = new ArrayList<>();
        for (Map.Entry<String, HostDevice> entry : oldMap.entrySet()) {
            final String deviceName = entry.getKey();
            if (!fetchedMap.containsKey(deviceName)) {
                removedDevices.add(entry.getValue());

                if (vmDeviceMap.containsKey(deviceName)) {
                    VmDevice vmDevice = vmDeviceMap.get(deviceName);
                    log.warnFormat("Removing VM[{}]'s hostDevice[{}] because it no longer exists on host {}",
                            vmDevice.getVmId(), deviceName, getVds());
                    removedVmDevices.add(vmDevice);
                }
            }
        }

        try {
            hostDeviceManager.acquireHostDevicesLock(getVdsId());
            TransactionSupport.executeInNewTransaction(new TransactionMethod<Void>() {
                @Override
                public Void runInTransaction() {

                    hostDeviceDao.removeAllInBatch(removedDevices);
                    hostDeviceDao.saveAll(newDevices);
                    hostDeviceDao.updateAllInBatch(changedDevices);

                    vmDeviceDao.removeAllInBatch(removedVmDevices);

                    return null;
                }
            });
        } finally {
            hostDeviceManager.releaseHostDevicesLock(getVdsId());
        }

        setSucceeded(true);
    }

    @Override
    protected void setActionMessageParameters() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__REFRESH);
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__HOST_DEVICES);
    }
}
