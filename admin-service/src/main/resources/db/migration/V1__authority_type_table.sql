create table authority_type
(
    id      bigserial
        primary key,
    actif   boolean,
    libelle varchar(255) not null
);

alter table authority_type
    owner to abbes;

INSERT INTO public.authority_type (id, actif, libelle) VALUES (1, true, 'Field');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (2, true, 'Module');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (3, true, 'Authority');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (4, true, 'Role');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (5, true, 'User');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (6, true, 'Group');
INSERT INTO public.authority_type (id, actif, libelle) VALUES (7, true, 'Authority Type');
