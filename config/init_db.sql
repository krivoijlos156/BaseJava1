create table if not exists resume
(
    uuid      char(36) not null constraint resume_pk primary key,
    full_name text     not null
);

create table if not exists contact
(
    id          serial   not null constraint contact_pk primary key,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    type        text     not null,
    value       text     not null
);

create unique index contact_uuid_uindex on contact (resume_uuid, type);

create table if not exists section
(
    id          serial   not null constraint section_pk primary key,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    type_s        text     not null,
    value_s       text     not null
);

create unique index section_uuid_uindex on section (resume_uuid, type_s);

