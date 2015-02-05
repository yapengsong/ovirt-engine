select fn_db_update_config_value('ManagementNetwork', 'eayunosmgmt', 'general');

UPDATE network
SET name = 'eayunosmgmt'
WHERE id = '00000000-0000-0000-0000-000000000009';
