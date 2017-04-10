"""EayunOS Version plugin."""


import os

from otopi import plugin, util

from ovirt_engine_setup import constants as osetupcons
from ovirt_engine_setup.engine import constants as oenginecons
from ovirt_engine_setup.engine_common import constants as oengcommcons


@util.export
class Plugin(plugin.PluginBase):
    """EayunOS Version plugin."""

    def __init__(self, context):
        super(Plugin, self).__init__(context=context)

    @plugin.event(
        stage=plugin.Stages.STAGE_MISC,
        before=(
            oengcommcons.Stages.DB_SCHEMA,
        ),
    )
    def _customization(self):
        version = self.environment.get(
            oenginecons.ConfigEnv.EAYUNOS_VERSION
        )
        if version == 'Enterprise':
            self.enterprise_version_setup()
            self.dialog.note(text="EayunOS version: Enterprise")

    def enterprise_version_setup(self):
        # update ovirt-engine files
        os.system("sed -i 's/4\.2 Basic/4\.2 Enterprise/' /usr/share/ovirt-engine/branding/ovirt.brand/messages.properties")
        os.system("sed -i 's/\\\u57FA\\\u7840\\\u7248/\\\u4f01\\\u4e1a\\\u7248/g' /usr/share/ovirt-engine/branding/ovirt.brand/messages_zh_CN.properties")
        os.system("sed -i 's/EayunOS_top_logo_basic\.png/EayunOS_top_logo_enterprise\.png/' /usr/share/ovirt-engine/branding/ovirt.brand/common.css")
        os.system("sed -i '/EayunOSVersion/d' /usr/share/ovirt-engine/dbscripts/upgrade/pre_upgrade/0000_config.sql")
        os.system("echo \"select fn_db_add_config_value('EayunOSVersion','Enterprise','general');\" >> /usr/share/ovirt-engine/dbscripts/upgrade/pre_upgrade/0000_config.sql")
        os.system("echo \"select fn_db_update_config_value('EayunOSVersion','Enterprise','general');\" >> /usr/share/ovirt-engine/dbscripts/upgrade/pre_upgrade/0000_config.sql")
        # make product uuid readable
        os.system("echo \"#! /bin/bash\" > /etc/init.d/systemuuid")
        os.system("echo \"# chkconfig: 2345 10 90\" >> /etc/init.d/systemuuid")
        os.system("echo \"chmod a+r /sys/class/dmi/id/product_uuid\" >> /etc/init.d/systemuuid")
        os.system("chmod a+x /etc/init.d/systemuuid")
        os.system("chkconfig systemuuid on")
        os.system("chmod a+r /sys/class/dmi/id/product_uuid")

    @plugin.event(
        stage=plugin.Stages.STAGE_MISC,
        before=(
            oengcommcons.Stages.DB_SCHEMA,
        ),
        condition=lambda self: (
            not self.environment[oenginecons.EngineDBEnv.NEW_DATABASE]
        ),
    )
    def _setup_installation_time(self):
        os.system("sed -i '/InstallationTime/d' /usr/share/ovirt-engine/dbscripts/upgrade/pre_upgrade/0000_config.sql")
        os.system("echo \"select fn_db_add_config_value('InstallationTime',to_char(current_timestamp,'yyyy-MM-dd HH24:mm:ss'),'general');\" >> /usr/share/ovirt-engine/dbscripts/upgrade/pre_upgrade/0000_config.sql")

