package org.ovirt.engine.core.common.businessentities;

import java.util.Date;

import org.ovirt.engine.core.common.action.VdcActionParametersBase;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.action.VdcReturnValueBase;
import org.ovirt.engine.core.common.utils.ObjectUtils;
import org.ovirt.engine.core.compat.CommandStatus;
import org.ovirt.engine.core.compat.Guid;

public class CommandEntity implements BusinessEntity<Guid> {

    private static final long serialVersionUID = 5293055556971973650L;
    private Guid userId;
    private Guid commandId;
    private Guid parentCommandId;
    private Guid rootCommandId;
    private Guid jobId;
    private Guid stepId;
    private VdcActionType commandType;
    private VdcActionParametersBase commandParameters;
    private VdcReturnValueBase returnValue;
    private Date createdAt;
    private CommandStatus commandStatus;
    private boolean callbackEnabled;
    private boolean callbackNotified;
    private boolean executed;

    public CommandEntity() {
        commandStatus = CommandStatus.UNKNOWN;
        createdAt = new Date();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (Guid.isNullOrEmpty(commandId) ? 0 : commandId.hashCode());
        result = prime * result + ((commandType == null) ? 0 : commandType.hashCode());
        result = prime * result + (Guid.isNullOrEmpty(parentCommandId) ? 0 : parentCommandId.hashCode());
        result = prime * result + (Guid.isNullOrEmpty(rootCommandId) ? 0 : rootCommandId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommandEntity other = (CommandEntity) obj;
        return ObjectUtils.objectsEqual(commandId, other.commandId)
                && commandType == other.commandType
                && ObjectUtils.objectsEqual(getGuid(parentCommandId), getGuid(other.parentCommandId))
                && ObjectUtils.objectsEqual(getGuid(rootCommandId), getGuid(other.rootCommandId));
    }

    private Guid getGuid(Guid guid) {
        return Guid.isNullOrEmpty(guid) ? Guid.Empty : guid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public VdcActionParametersBase getCommandParameters() {
        return this.commandParameters;
    }

    public void setCommandParameters(VdcActionParametersBase value) {
        this.commandParameters = value;
    }

    public Guid getId() {
        return commandId;
    }

    public void setId(Guid commandId) {
        this.commandId = commandId;
    }

    public Guid getParentCommandId() {
        return parentCommandId;
    }

    public void setParentCommandId(Guid parentCommandId) {
        this.parentCommandId = parentCommandId;
    }

    public Guid getRootCommandId() {
        return rootCommandId;
    }

    public void setRootCommandId(Guid rootCommandId) {
        this.rootCommandId = rootCommandId;
    }

    public VdcActionType getCommandType() {
        return commandType;
    }

    public void setCommandType(VdcActionType type) {
        this.commandType = type;
    }

    public CommandStatus getCommandStatus() {
        return commandStatus;
    }

    public void setCommandStatus(CommandStatus commandStatus) {
        this.commandStatus = commandStatus;
    }

    public boolean isCallbackEnabled() {
        return callbackEnabled;
    }

    public void setCallbackEnabled(boolean callbackEnabled) {
        this.callbackEnabled = callbackEnabled;
    }

    public boolean isCallbackNotified() {
        return callbackNotified;
    }

    public void setCallbackNotified(boolean callbackNotified) {
        this.callbackNotified = callbackNotified;
    }

    public static CommandEntity buildCommandEntity(Guid userId,
                                                   Guid commandId,
                                                   Guid parentCommandId,
                                                   Guid rootCommandId,
                                                   Guid jobId,
                                                   Guid stepId,
                                                   VdcActionType actionType,
                                                   VdcActionParametersBase params,
                                                   CommandStatus status,
                                                   boolean callbackEnabled,
                                                   VdcReturnValueBase returnValue) {
        CommandEntity entity = new CommandEntity();
        entity.setUserId(userId);
        entity.setId(commandId);
        entity.setParentCommandId(parentCommandId);
        entity.setRootCommandId(rootCommandId);
        entity.setJobId(jobId);
        entity.setStepId(stepId);
        entity.setCommandType(actionType);
        entity.setCommandParameters(params);
        entity.setCommandStatus(status);
        entity.setCallbackEnabled(callbackEnabled);
        entity.setReturnValue(returnValue);
        return entity;
    }

    public VdcReturnValueBase getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(VdcReturnValueBase returnValue) {
        this.returnValue = returnValue;
    }

    public Guid getJobId() {
        return jobId;
    }

    public void setJobId(Guid jobId) {
        this.jobId = jobId;
    }

    public Guid getStepId() {
        return stepId;
    }

    public void setStepId(Guid stepId) {
        this.stepId = stepId;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public Guid getUserId() {
        return userId;
    }

    public void setUserId(Guid userId) {
        this.userId = userId;
    }
}
