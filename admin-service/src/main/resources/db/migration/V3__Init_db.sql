create database "admin-db"
    with owner abbes;

create table public.authority_type
(
    id      bigserial
        primary key,
    actif   boolean,
    libelle varchar(255) not null
);

alter table public.authority_type
    owner to abbes;

create table public.module
(
    id          bigserial
        primary key,
    actif       boolean,
    color       varchar(255) not null,
    icon        varchar(255) not null,
    module_code varchar(255) not null
        constraint uk_ashbv96mr87vtstjpdmuhk2bf
            unique,
    module_name varchar(255) not null
        constraint uk_ao25w2hy445wesxeo9wfvsp77
            unique,
    uri         varchar(255) not null
);

alter table public.module
    owner to abbes;

create table public.authority
(
    id                bigserial
        primary key,
    actif             boolean,
    libelle           varchar(255) not null,
    authority_type_id bigint       not null
        constraint fkh62ioicty050s04igmrbpi66d
            references public.authority_type,
    module_id         bigint       not null
        constraint fk19hg8ly11e919ed80efg9b1nm
            references public.module
);

alter table public.authority
    owner to abbes;

create table public.t_group
(
    id      bigserial
        primary key,
    actif   boolean,
    libelle varchar(255) not null
);

alter table public.t_group
    owner to abbes;

create table public.profile
(
    id       bigserial
        primary key,
    actif    boolean,
    libelle  varchar(255) not null,
    group_id bigint
        constraint fk4fhg6mf6wigk93ob9l0nuex3v
            references public.t_group,
    user_id  bigint       not null
);

alter table public.profile
    owner to abbes;

create table public.profile_authority
(
    id           bigserial
        primary key,
    granted      boolean,
    authority_id bigint not null
        constraint fkbmxt9jbtblyyta1n20rxfawm2
            references public.authority,
    profile_id   bigint not null
        constraint fk8wkjybdax9n2tb788k704o6e0
            references public.profile
);

alter table public.profile_authority
    owner to abbes;

create table public.profile_modules
(
    profile_id bigint not null
        constraint fkmk7gkgmeqgvl56cp08nvuqk8x
            references public.profile,
    modules_id bigint not null
        constraint fkexwnh0bhmhqyw7cor7boybjep
            references public.module,
    primary key (profile_id, modules_id)
);

alter table public.profile_modules
    owner to abbes;

create table public.t_role
(
    id        bigserial
        primary key,
    actif     boolean,
    libelle   varchar(255) not null,
    module_id bigint       not null
        constraint fk4qpf1b98cesau3fu98snbh55o
            references public.module
);

alter table public.t_role
    owner to abbes;

create table public.profile_roles
(
    profile_id bigint not null
        constraint fkgt2d29hgt9t816v5hm9u56h1d
            references public.profile,
    roles_id   bigint not null
        constraint fk657ggxo16nm4jrpwoyhdw9bc3
            references public.t_role,
    primary key (profile_id, roles_id)
);

alter table public.profile_roles
    owner to abbes;

create table public.t_role_authorities
(
    role_id        bigint not null
        constraint fkf7d7cgwtmsv7htgiub9me3uan
            references public.t_role,
    authorities_id bigint not null
        constraint fkist6op774ulmlbsudhbvcyxyw
            references public.authority,
    primary key (role_id, authorities_id)
);

alter table public.t_role_authorities
    owner to abbes;

create table public.t_user
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
            references public.profile
);

alter table public.t_user
    owner to abbes;

alter table public.profile
    add constraint fkji1141ofp15qe9v2f6l1b5wu6
        foreign key (user_id) references public.t_user;

INSERT INTO public.authority_type (id, actif, libelle) VALUES (1, true, 'Field');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (2, true, 'Module');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (3, true, 'Authority');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (4, true, 'Role');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (5, true, 'User');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (6, true, 'Group');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (7, true, 'Authority Type');

INSERT INTO public.module (id, actif, color, icon, module_code, module_name, uri) VALUES (1, null, '#11009E', 'fa-solid fa-user-tie', 'ADMIN_MODULE', 'Admin Module', 'www.kyrios.dz/admin');

INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (2, true, 'AUTHORITY_CREATE', 3, 1);
INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (3, true, 'AUTHORITY_TYPE_CREATE', 7, 1);
INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (4, true, 'USER_PROFILE_MODULE_ADD', 5, 1);
INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (5, true, 'USER_PROFILE_ROLE_ADD', 5, 1);
INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (6, true, 'USER_PROFILE_AUTHORITY_GRANT', 5, 1);
INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (1, true, 'MODULE_CREATE', 2, 1);

INSERT INTO public.profile (id, actif, libelle, group_id, user_id) VALUES (1, null, 'Default profile', null, 2);




INSERT INTO public.t_user (id, actif, email, first_name, last_name, phone_number, user_name, uuid, profile_id) VALUES (1, true, 'e-learn@gmail.com', 'E', 'Learn', null, 'e-learn', 'd66005b0-7994-44ec-b57e-692865dd2664', null);
INSERT INTO public.t_user (id, actif, email, first_name, last_name, phone_number, user_name, uuid, profile_id) VALUES (2, true, 'abbes@gmail.com', 'Abbes', 'Larbaoui', '0659423060', 'abbes', '3502bc64-8460-4032-8d3a-842c6c8a72e5', 1);
INSERT INTO public.t_user (id, actif, email, first_name, last_name, phone_number, user_name, uuid, profile_id) VALUES (5, true, 'default_user@example.com', 'Default', 'User', null, 'default_user', '82c4cb2b-1fca-4f7d-9836-7e6f853eef8c', null);
