create table users
(
    id            bigserial not null,
    email         varchar   not null,
    password      varchar   not null,
    name          varchar   not null,
    auth_provider varchar   not null,
    role          varchar   not null,
    created_at    timestamp,
    updated_at    timestamp,
    primary key (id),
    CONSTRAINT user_email_unique UNIQUE (email)
);

create table categories
(
    id         bigserial not null,
    name       varchar   not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id),
    CONSTRAINT category_name_unique UNIQUE (name)
);

create table posts
(
    id         bigserial not null,
    title      varchar   not null,
    url        varchar,
    content    text      not null,
    created_by bigint    not null REFERENCES users (id),
    cat_id     bigint    not null REFERENCES categories (id),
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);

