package org.ovirt.engine.core.bll;

import java.util.HashMap;

import org.ovirt.engine.core.common.businessentities.aaa.DbUser;
import org.ovirt.engine.core.common.queries.VdcQueryParametersBase;
import org.ovirt.engine.core.common.utils.SecretKey;
import org.ovirt.engine.core.common.utils.VerifyLicenseStatus;

public class EngineUuidQuery<P extends VdcQueryParametersBase> extends QueriesCommandBase<P> {

    public EngineUuidQuery(P parameters) {
        super(parameters);
    }

    @Override
    protected void executeQueryCommand() {
        String code = null;
        HashMap<String, String> map= new HashMap<String, String>();
                code = VerifyLicenseStatus.getUuid();
                code = SecretKey.part(code);
                code = SecretKey.to32String(code);

                if(VerifyLicenseStatus.getVerifyActiveState(getDbFacade())){
                    map.put("isActive", "true");
                }else{
                    map.put("isActive", "false");
                }

                if(VerifyLicenseStatus.getVerifyExpiredState()){
                    map.put("timeOut", "true");
                }else{
                    map.put("timeOut", "false");
                }
                //
                map.put("code", code);
                log.info(code);

                //是否超级管理员
                DbUser user =getUser();
                DbUser isSuper=getDbFacade().getDbUserDao().getIsSuperUser(user);
                if(isSuper!=null){
                    map.put("isSuper", "true");
                }else{
                    map.put("isSuper", "false");
                }

                log.info(code);

        getQueryReturnValue().setReturnValue(map);
    }
}
