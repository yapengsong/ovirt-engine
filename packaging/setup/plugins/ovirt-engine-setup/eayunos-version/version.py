"""EayunOS Version plugin."""


import os

from otopi import plugin, util

from ovirt_engine_setup import constants as osetupcons
from ovirt_engine_setup.engine import constants as oenginecons
from ovirt_engine_setup.engine import vdcoption
from ovirt_engine_setup.engine_common import constants as oengcommcons
from ovirt_engine_setup.engine_common import database


@util.export
class Plugin(plugin.PluginBase):
    """EayunOS Version plugin."""

    def __init__(self, context):
        super(Plugin, self).__init__(context=context)

    @plugin.event(
        stage=plugin.Stages.STAGE_CUSTOMIZATION,
        before=(
            osetupcons.Stages.DIALOG_TITLES_E_PRODUCT_OPTIONS,
        ),
        after=(
            osetupcons.Stages.DIALOG_TITLES_S_PRODUCT_OPTIONS,
        ),
    )
    def _customization(self):
        self.version = self.environment.get(
            oenginecons.ConfigEnv.EAYUNOS_VERSION
        )
        if self.version == 'Enterprise':
            self.enterprise_version_setup()
            self.dialog.note(text="EayunOS version: Enterprise")

    def enterprise_version_setup(self):
        os.system("sed -i 's/4\.2 Basic/4\.2 Enterprise/' /usr/share/ovirt-engine/branding/ovirt.brand/messages.properties")
        os.system("sed -i 's/4\.2 \\\u57FA\\\u7840\\\u7248/4\.2 \\\u4f01\\\u4e1a\\\u7248/' /usr/share/ovirt-engine/branding/ovirt.brand/messages_zh_CN.properties")
        os.system("sed -i 's/EayunOS_top_logo\.png/EayunOS_top_logo_enterprise\.png/' /usr/share/ovirt-engine/branding/ovirt.brand/common.css")

    @plugin.event(
        stage=plugin.Stages.STAGE_VALIDATION,
        after=(
            oengcommcons.Stages.DB_CREDENTIALS_AVAILABLE_EARLY,
        ),
    )
    def update_vdc_option(self):
        statement = database.Statement(
            dbenvkeys=oenginecons.Const.ENGINE_DB_ENV_KEYS,
            environment=self.environment,
        )
        vdcoption.VdcOption(
            statement=statement,
        ).updateVdcOptions(
            options=(
                {
                    'name': 'EayunOSVersion',
                    'value': self.version,
                },
            ),
            ownConnection=True,
        )
