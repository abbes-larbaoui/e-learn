create table t_group
(
    id      bigserial
        primary key,
    actif   boolean,
    libelle varchar(255) not null
);

alter table t_group
    owner to abbes;

