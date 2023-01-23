create table users
(
    id                 bigserial not null,
    email              varchar   not null,
    password           varchar   not null,
    name               varchar   not null,
    auth_provider      varchar   not null,
    role               varchar   not null,
    verified           bool      not null default false,
    verification_token varchar,
    created_at         timestamp,
    updated_at         timestamp,
    primary key (id),
    CONSTRAINT user_email_unique UNIQUE (email)
);

create table categories
(
    id            bigserial not null,
    name          varchar   not null,
    slug          varchar   not null,
    description   varchar   not null,
    image         varchar,
    display_order numeric   not null,
    created_at    timestamp,
    updated_at    timestamp,
    primary key (id),
    CONSTRAINT category_name_unique UNIQUE (name),
    CONSTRAINT category_slug_unique UNIQUE (slug)
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

create table votes
(
    id         bigserial not null,
    user_id    bigint    not null REFERENCES users (id),
    post_id    bigint    not null REFERENCES posts (id),
    value      numeric   not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id),
    UNIQUE (user_id, post_id)
);