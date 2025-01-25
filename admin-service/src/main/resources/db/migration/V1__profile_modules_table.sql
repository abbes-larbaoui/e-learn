create table profile_modules
(
    profile_id bigint not null
        constraint fkmk7gkgmeqgvl56cp08nvuqk8x
            references profile,
    modules_id bigint not null
        constraint fkexwnh0bhmhqyw7cor7boybjep
            references module,
    primary key (profile_id, modules_id)
);

alter table profile_modules
    owner to abbes;

