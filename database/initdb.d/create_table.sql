create table if not exists public.users
(
    id              bigserial
    primary key,
    email           varchar(255)
    constraint uk_6dotkott2kjsp8vw4d0m25fb7
    unique,
    nickname        varchar(255),
    oauth_provider  varchar(255),
    profile_image   bytea,
    profile_message varchar(255)
    );

alter table public.users
    owner to ecopercent;

create table if not exists public.items
(
    id                  bigserial
    primary key,
    brand               varchar(255),
    category            varchar(255),
    current_usage_count integer,
    goal_usage_count    integer,
    image               bytea,
    is_title            boolean,
    latest_data         timestamp(6),
    nickname            varchar(255),
    price               integer,
    purchase_data       date,
    registration_date   timestamp(6),
    type                varchar(255),
    usage_count_per_day integer,
    user_id             bigint
    constraint fkft8pmhndq1kntvyfaqcybhxvx
    references public.users
    );

alter table public.items
    owner to ecopercent;
