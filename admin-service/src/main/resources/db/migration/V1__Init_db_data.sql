----------------------------------------------- tables -----------------------------------------------
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

----------------------------------------------- data -----------------------------------------------

-- Disable foreign key checks
SET session_replication_role = 'replica';


INSERT INTO public.authority_type (actif, libelle) VALUES (true, 'Field');
INSERT INTO public.authority_type (actif, libelle) VALUES (true, 'Module');
INSERT INTO public.authority_type (actif, libelle) VALUES (true, 'Authority');
INSERT INTO public.authority_type (actif, libelle) VALUES (true, 'Role');
INSERT INTO public.authority_type (actif, libelle) VALUES (true, 'User');
INSERT INTO public.authority_type (actif, libelle) VALUES (true, 'Group');
INSERT INTO public.authority_type (actif, libelle) VALUES (true, 'Authority Type');

INSERT INTO public.module (actif, color, icon, module_code, module_name, uri) VALUES (true, '#11009E', 'fa-solid fa-user-tie', 'ADMIN_MODULE', 'Admin Module', 'www.kyrios.dz/admin');

-- Insert authorities
INSERT INTO public.authority (actif, libelle, authority_type_id, module_id)
SELECT true, 'MODULE_CREATE', at.id, m.id FROM public.authority_type at, public.module m WHERE at.libelle = 'Module' AND m.module_code = 'ADMIN_MODULE';

INSERT INTO public.authority (actif, libelle, authority_type_id, module_id)
SELECT true, 'AUTHORITY_CREATE', at.id, m.id FROM public.authority_type at, public.module m WHERE at.libelle = 'Authority' AND m.module_code = 'ADMIN_MODULE';

INSERT INTO public.authority (actif, libelle, authority_type_id, module_id)
SELECT true, 'AUTHORITY_TYPE_CREATE', at.id, m.id FROM public.authority_type at, public.module m WHERE at.libelle = 'Authority Type' AND m.module_code = 'ADMIN_MODULE';

INSERT INTO public.authority (actif, libelle, authority_type_id, module_id)
SELECT true, 'USER_PROFILE_MODULE_ADD', at.id, m.id FROM public.authority_type at, public.module m WHERE at.libelle = 'User' AND m.module_code = 'ADMIN_MODULE';

INSERT INTO public.authority (actif, libelle, authority_type_id, module_id)
SELECT true, 'USER_PROFILE_ROLE_ADD', at.id, m.id FROM public.authority_type at, public.module m WHERE at.libelle = 'User' AND m.module_code = 'ADMIN_MODULE';

INSERT INTO public.authority (actif, libelle, authority_type_id, module_id)
SELECT true, 'USER_PROFILE_AUTHORITY_GRANT', at.id, m.id FROM public.authority_type at, public.module m WHERE at.libelle = 'User' AND m.module_code = 'ADMIN_MODULE';

-- Insert profile
INSERT INTO public.profile (actif, libelle, group_id, user_id) VALUES (true, 'Default profile', null, 1);

-- Insert default user
INSERT INTO public.t_user (actif, email, first_name, last_name, phone_number, user_name, uuid, profile_id)
VALUES (true, 'default_user@example.com', 'Default', 'User', '0666554433', 'default_user', 'check-keycloak-and-update-it', 1);

-- Insert profile_modules
INSERT INTO public.profile_modules (profile_id, modules_id) VALUES (1, 1);

-- Insert profile authorities
INSERT INTO public.profile_authority (granted, authority_id, profile_id)
SELECT true, id, 1 FROM public.authority WHERE libelle = 'MODULE_CREATE';

INSERT INTO public.profile_authority (granted, authority_id, profile_id)
SELECT true, id, 1 FROM public.authority WHERE libelle = 'AUTHORITY_CREATE';

INSERT INTO public.profile_authority (granted, authority_id, profile_id)
SELECT true, id, 1 FROM public.authority WHERE libelle = 'AUTHORITY_TYPE_CREATE';

INSERT INTO public.profile_authority (granted, authority_id, profile_id)
SELECT true, id, 1 FROM public.authority WHERE libelle = 'USER_PROFILE_MODULE_ADD';

INSERT INTO public.profile_authority (granted, authority_id, profile_id)
SELECT true, id, 1 FROM public.authority WHERE libelle = 'USER_PROFILE_ROLE_ADD';

INSERT INTO public.profile_authority (granted, authority_id, profile_id)
SELECT true, id, 1 FROM public.authority WHERE libelle = 'USER_PROFILE_AUTHORITY_GRANT';


-- Re-enable foreign key checks
SET session_replication_role = 'origin';