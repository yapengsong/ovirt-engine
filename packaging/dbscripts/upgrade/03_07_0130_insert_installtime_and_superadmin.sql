select fn_db_add_config_value('InstallationTime',to_char(current_timestamp,'yyyy-MM-dd HH24:mm:ss'),'general');

delete from roles_groups where action_group_id=40000;

insert into roles_groups(role_id,action_group_id) values ('00000000-0000-0000-0000-000000000001',40000);
