create table t_user
(
    id           bigserial
        primary key,
    actif        boolean,
    email        varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    phone_number varchar(255),
    user_name    varchar(255) not null,
    uuid         varchar(255) not null,
    profile_id   bigint
        constraint uk_jyu17qwmfjck0wqw4uhvxs2ct
            unique
        constraint fkcx0at9lg0t2lpgepyooyc96wa
            references profile
);

alter table t_user
    owner to abbes;

INSERT INTO public.t_user (id, actif, email, first_name, last_name, phone_number, user_name, uuid, profile_id) VALUES (1, true, 'e-learn@gmail.com', 'E', 'Learn', null, 'e-learn', 'd66005b0-7994-44ec-b57e-692865dd2664', null);
INSERT INTO public.t_user (id, actif, email, first_name, last_name, phone_number, user_name, uuid, profile_id) VALUES (2, true, 'abbes@gmail.com', 'Abbes', 'Larbaoui', '0659423060', 'abbes', '3502bc64-8460-4032-8d3a-842c6c8a72e5', 1);
INSERT INTO public.t_user (id, actif, email, first_name, last_name, phone_number, user_name, uuid, profile_id) VALUES (5, true, 'default_user@example.com', 'Default', 'User', null, 'default_user', '82c4cb2b-1fca-4f7d-9836-7e6f853eef8c', null);
