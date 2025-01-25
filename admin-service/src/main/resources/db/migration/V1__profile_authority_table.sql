create table profile_authority
(
    id           bigserial
        primary key,
    granted      boolean,
    authority_id bigint not null
        constraint fkbmxt9jbtblyyta1n20rxfawm2
            references authority,
    profile_id   bigint not null
        constraint fk8wkjybdax9n2tb788k704o6e0
            references profile
);

alter table profile_authority
    owner to abbes;

