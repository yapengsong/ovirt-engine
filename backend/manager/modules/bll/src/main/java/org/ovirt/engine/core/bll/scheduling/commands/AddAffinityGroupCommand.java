package org.ovirt.engine.core.bll.scheduling.commands;

import java.util.List;
import org.apache.commons.collections.ListUtils;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.errors.EngineMessage;
import org.ovirt.engine.core.common.scheduling.AffinityGroup;
import org.ovirt.engine.core.common.scheduling.parameters.AffinityGroupCRUDParameters;
import org.ovirt.engine.core.compat.Guid;

public class AddAffinityGroupCommand extends AffinityGroupCRUDCommand {

    public AddAffinityGroupCommand(AffinityGroupCRUDParameters parameters) {
        super(parameters);
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    protected boolean canDoAction() {
        if (getAffinityGroupDao().getByName(getParameters().getAffinityGroup().getName()) != null) {
            return failCanDoAction(EngineMessage.ACTION_TYPE_FAILED_AFFINITY_GROUP_NAME_EXISTS);
        }
        List<Guid> vmIdList = getAffinityGroup().getEntityIds(); //前台传来的新建的 affinity 设计到的所有虚拟机的集合
        boolean isEnforcing = getAffinityGroup().isEnforcing();
        boolean isPositive = getAffinityGroup().isPositive();
        for(AffinityGroup ag : getAffinityGroupDao().getAllAffinityGroupsByClusterId(getAffinityGroup().getClusterId())) {
            List<Guid> list1 = ListUtils.subtract(vmIdList, ag.getEntityIds());
            List<Guid> list2 = ListUtils.subtract(ag.getEntityIds(), vmIdList);
            if (list1.isEmpty() && list2.isEmpty() && isEnforcing == ag.isEnforcing() && isPositive == ag.isPositive()) {
                return failCanDoAction(EngineMessage.ACTION_TYPE_FAILED_AFFINITY_GROUP_CONTENT_REPETITION);
            }
        }
        return validateParameters();
    }

    @Override
    protected AffinityGroup getAffinityGroup() {
        return getParameters().getAffinityGroup();
    }

    @Override
    protected void executeCommand() {
        getAffinityGroup().setId(Guid.newGuid());
        getAffinityGroupDao().save(getAffinityGroup());
        getReturnValue().setActionReturnValue(getAffinityGroup().getId());
        setSucceeded(true);
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        return getSucceeded() ? AuditLogType.USER_ADDED_AFFINITY_GROUP : AuditLogType.USER_FAILED_TO_ADD_AFFINITY_GROUP;
    }

    @Override
    protected void setActionMessageParameters() {
        super.setActionMessageParameters();
        addCanDoActionMessage(EngineMessage.VAR__ACTION__ADD);
    }
}
