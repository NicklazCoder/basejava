create table if not exists public.resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

create table if not exists public.contact
(
    id          serial
        constraint contact_pk
            primary key,
    type        text     not null,
    value       text     not null,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references public.resume(uuid)
            on update restrict on delete cascade
);
create unique index if not exists contact_uuid_type_index
    on public.contact (resume_uuid, type);

