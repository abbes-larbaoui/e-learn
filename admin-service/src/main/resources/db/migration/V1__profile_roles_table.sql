create table profile_roles
(
    profile_id bigint not null
        constraint fkgt2d29hgt9t816v5hm9u56h1d
            references profile,
    roles_id   bigint not null
        constraint fk657ggxo16nm4jrpwoyhdw9bc3
            references t_role,
    primary key (profile_id, roles_id)
);

alter table profile_roles
    owner to abbes;

