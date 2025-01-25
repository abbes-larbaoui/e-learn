INSERT INTO t_user (actif, email, first_name, last_name, user_name, uuid)
VALUES (
           true,
           '${default_email}',
           '${default_first_name}',
           '${default_last_name}',
           '${default_user_name}',
           '${default_user_uuid}'
       );