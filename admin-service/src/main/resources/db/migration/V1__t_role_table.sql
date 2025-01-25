create table t_role
(
    id        bigserial
        primary key,
    actif     boolean,
    libelle   varchar(255) not null,
    module_id bigint       not null
        constraint fk4qpf1b98cesau3fu98snbh55o
            references module
);

alter table t_role
    owner to abbes;

