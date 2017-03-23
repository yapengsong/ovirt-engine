package org.ovirt.engine.core.bll;

import org.ovirt.engine.core.common.businessentities.aaa.DbUser;
import org.ovirt.engine.core.common.queries.VdcQueryParametersBase;

public class GetIsSuperUserQuery<P extends VdcQueryParametersBase> extends QueriesCommandBase<P> {

    public GetIsSuperUserQuery(P parameters) {
        super(parameters);
    }

    @Override
    protected void executeQueryCommand() {
        DbUser user =getUser();


        DbUser isSuper=getDbFacade().getDbUserDao().getIsSuperUser(user);
        boolean is=false;
        if(isSuper!=null){
            is=true;
        }

        getQueryReturnValue().setReturnValue(is);

    }
}
