package org.ovirt.engine.core.bll.scheduling.commands;

import java.util.List;

import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.errors.EngineMessage;
import org.ovirt.engine.core.common.scheduling.AffinityGroup;
import org.ovirt.engine.core.common.scheduling.parameters.AffinityGroupCRUDParameters;
import org.ovirt.engine.core.common.utils.ListUtils;


public class EditAffinityGroupCommand extends AffinityGroupCRUDCommand {

    public EditAffinityGroupCommand(AffinityGroupCRUDParameters parameters) {
        super(parameters);
    }

    @Override
    protected boolean canDoAction() {
        if (getAffinityGroup() == null) {
            return failCanDoAction(EngineMessage.ACTION_TYPE_FAILED_INVALID_AFFINITY_GROUP_ID);
        }
        if (!getParameters().getAffinityGroup().getClusterId().equals(getClusterId())) {
            return failCanDoAction(EngineMessage.ACTION_TYPE_FAILED_CANNOT_CHANGE_CLUSTER_ID);
        }
        if (!getAffinityGroup().getName().equals(getParameters().getAffinityGroup().getName()) &&
                getAffinityGroupDao().getByName(getParameters().getAffinityGroup().getName()) != null) {
            return failCanDoAction(EngineMessage.ACTION_TYPE_FAILED_AFFINITY_GROUP_NAME_EXISTS);
        }
        if (!detailCompare()) {
            return failCanDoAction(EngineMessage.ACTION_TYPE_FAILED_AFFINITY_GROUP_CONTENT_REPETITION);
        }
        return validateParameters();
    }

    @Override
    protected void executeCommand() {
        getAffinityGroupDao().update(getParameters().getAffinityGroup());
        setSucceeded(true);
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        return getSucceeded() ? AuditLogType.USER_UPDATED_AFFINITY_GROUP
                : AuditLogType.USER_FAILED_TO_UPDATE_AFFINITY_GROUP;
    }

    @Override
    protected void setActionMessageParameters() {
        super.setActionMessageParameters();
        addCanDoActionMessage(EngineMessage.VAR__ACTION__UPDATE);
    }

    private boolean detailCompare() {
        List<AffinityGroup> res = getAffinityGroupDao().getAllAffinityGroupsByClusterId(getClusterId());
        if (res == null) {
            return true;
        } else {
            for (AffinityGroup ag : res) {
                if (!ag.getId().equals(getParameters().getAffinityGroupId())
                        && compare(getParameters().getAffinityGroup(), ag)) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean compare(AffinityGroup a, AffinityGroup b) {
        if ((a.isEnforcing() == b.isEnforcing()) && (a.isPositive() == b.isPositive())
                && ListUtils.listsEqual(a.getEntityIds(), b.getEntityIds())) {
            return true;
        } else {
            return false;
        }
    }
}
