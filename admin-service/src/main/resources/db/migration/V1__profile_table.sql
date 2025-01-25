create table profile
(
    id       bigserial
        primary key,
    actif    boolean,
    libelle  varchar(255) not null,
    group_id bigint
        constraint fk4fhg6mf6wigk93ob9l0nuex3v
            references t_group,
    user_id  bigint       not null
        constraint fkji1141ofp15qe9v2f6l1b5wu6
            references t_user
);

alter table profile
    owner to abbes;

INSERT INTO public.profile (id, actif, libelle, group_id, user_id) VALUES (1, null, 'Default profile', null, 2);
