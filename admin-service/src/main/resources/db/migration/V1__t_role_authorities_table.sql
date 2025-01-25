create table t_role_authorities
(
    role_id        bigint not null
        constraint fkf7d7cgwtmsv7htgiub9me3uan
            references t_role,
    authorities_id bigint not null
        constraint fkist6op774ulmlbsudhbvcyxyw
            references authority,
    primary key (role_id, authorities_id)
);

alter table t_role_authorities
    owner to abbes;

