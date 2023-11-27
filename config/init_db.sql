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
CREATE TABLE IF NOT EXISTS public.section (
    id SERIAL CONSTRAINT section_pk PRIMARY KEY,
    type TEXT NOT NULL,
    context TEXT NOT NULL,
    resume_uuid CHAR(36) NOT NULL
        CONSTRAINT section_resume_uuid_fk
            references public.resume(uuid)
            ON UPDATE RESTRICT ON DELETE CASCADE
);
create unique index if not exists section_uuid_type_index
    on public.section (resume_uuid, type);
