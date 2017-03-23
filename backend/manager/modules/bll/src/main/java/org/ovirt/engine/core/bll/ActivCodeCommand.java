package org.ovirt.engine.core.bll;

import javax.inject.Inject;

import org.ovirt.engine.core.bll.profiles.CpuProfileHelper;
import org.ovirt.engine.core.common.action.ActivCodeParameters;
import org.ovirt.engine.core.common.businessentities.VdcOption;
import org.ovirt.engine.core.common.errors.EngineMessage;
import org.ovirt.engine.core.common.utils.SecretKey;
import org.ovirt.engine.core.common.utils.VerifyLicenseStatus;
import org.ovirt.engine.core.utils.transaction.TransactionMethod;
import org.ovirt.engine.core.utils.transaction.TransactionSupport;
import org.slf4j.LoggerFactory;

public class ActivCodeCommand<T extends ActivCodeParameters> extends VmCommand<T> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ActivCodeCommand.class);
    private boolean dedicatedHostWasCleared;

    @Inject
    private CpuProfileHelper cpuProfileHelper;

    public ActivCodeCommand(T params) {
        super(params);
    }

    @Override
    protected boolean canDoAction() {

        if (getParameters().getActivCode() == null) {
            return failCanDoAction(EngineMessage.ACTION_TYPE_FAILED_CLUSTER_CAN_NOT_BE_EMPTY);
        }


        String activCode= getParameters().getActivCode();
        try {
            activCode=SecretKey.decode(activCode);
            activCode=SecretKey.to16String(activCode);
            activCode=activCode.replace("-", "");
            String UUID = VerifyLicenseStatus.getUuid();
            UUID = UUID.replace("-", "");
            log.info(UUID);
            log.info(activCode);

            if (UUID.equals(activCode)) {
                return true;
            } else {
                return failCanDoAction(EngineMessage.ACTION_ACTIVCODE_FAILED);
            }

        } catch (Exception e) {
            log.info("异常出现");
            return failCanDoAction(EngineMessage.ACTION_ACTIVCODE_FAILED);
        }



    }

    @Override
    protected void executeCommand() {

                TransactionSupport.executeInNewTransaction(new TransactionMethod<Void>() {
                    @Override
                    public Void runInTransaction() {
                        saveActivCode();
                        setSucceeded(true);
                        return null;
                    }
                });
    }

    protected void saveActivCode() {
        VdcOption vdcOption=new VdcOption();

       VdcOption option=getDbFacade().getVdcOptionDao().getByNameAndVersion("ActivCode", "general");

        if(option==null){
            vdcOption.setoption_value(getParameters().getActivCode());
            vdcOption.setoption_name("ActivCode");
            vdcOption.setversion("general");
            getDbFacade().getVdcOptionDao().save(vdcOption);
        }else{
            option.setoption_value(getParameters().getActivCode());
            getDbFacade().getVdcOptionDao().update(vdcOption);
        }
    }


}
