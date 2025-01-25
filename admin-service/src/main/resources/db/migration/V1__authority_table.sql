create table authority
(
    id                bigserial
        primary key,
    actif             boolean,
    libelle           varchar(255) not null,
    authority_type_id bigint       not null
        constraint fkh62ioicty050s04igmrbpi66d
            references authority_type,
    module_id         bigint       not null
        constraint fk19hg8ly11e919ed80efg9b1nm
            references module
);

alter table authority
    owner to abbes;

INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (2, true, 'AUTHORITY_CREATE', 3, 1);
INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (3, true, 'AUTHORITY_TYPE_CREATE', 7, 1);
INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (4, true, 'USER_PROFILE_MODULE_ADD', 5, 1);
INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (5, true, 'USER_PROFILE_ROLE_ADD', 5, 1);
INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (6, true, 'USER_PROFILE_AUTHORITY_GRANT', 5, 1);
INSERT INTO public.authority (id, actif, libelle, authority_type_id, module_id) VALUES (1, true, 'MODULE_CREATE', 2, 1);
