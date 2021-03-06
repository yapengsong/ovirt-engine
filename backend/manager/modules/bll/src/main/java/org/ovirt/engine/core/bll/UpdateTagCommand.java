package org.ovirt.engine.core.bll;

import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.action.TagsOperationParameters;
import org.ovirt.engine.core.common.businessentities.Tags;
import org.ovirt.engine.core.common.errors.EngineMessage;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;

public class UpdateTagCommand<T extends TagsOperationParameters> extends TagsCommandOperationBase<T> {
    public UpdateTagCommand(T parameters) {
        super(parameters);

    }

    @Override
    protected void executeCommand() {
        TagsDirector.getInstance().UpdateTag(getTag());
        DbFacade.getInstance().getTagDao().update(getTag());
        setSucceeded(true);
    }

    @Override
    protected boolean canDoAction() {
        // we fetch by new name to see if it is in use
        Tags tag = DbFacade.getInstance().getTagDao()
                .getByName(getParameters().getTag().gettag_name());
        if (tag != null && !tag.gettag_id().equals(getParameters().getTag().gettag_id())) {
            addCanDoActionMessage(EngineMessage.TAGS_SPECIFY_TAG_IS_IN_USE);
            return false;
        }
        // we fetch by id to see if the tag is realy read-only
        tag = DbFacade.getInstance().getTagDao().get(getParameters().getTag().gettag_id());
        if (tag.getIsReadonly() != null && tag.getIsReadonly()) {
            addCanDoActionMessage(EngineMessage.TAGS_CANNOT_EDIT_READONLY_TAG);
            return false;
        }
        return true;
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        return getSucceeded() ? AuditLogType.USER_UPDATE_TAG : AuditLogType.USER_UPDATE_TAG_FAILED;
    }
}
