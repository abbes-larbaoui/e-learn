create table module
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

alter table module
    owner to abbes;

INSERT INTO public.module (id, actif, color, icon, module_code, module_name, uri) VALUES (1, null, '#11009E', 'fa-solid fa-user-tie', 'ADMIN_MODULE', 'Admin Module', 'www.kyrios.dz/admin');
