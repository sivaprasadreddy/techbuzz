create sequence user_id_seq start with 1 increment by 5;
create sequence category_id_seq start with 1 increment by 5;
create sequence post_id_seq start with 1 increment by 5;
create sequence vote_id_seq start with 1 increment by 5;

create table users
(
    id                 bigint           DEFAULT nextval('user_id_seq') not null,
    email              varchar not null,
    password           varchar not null,
    name               varchar not null,
    role               varchar not null,
    verified           bool    not null default false,
    verification_token varchar,
    created_at         timestamp,
    updated_at         timestamp,
    primary key (id),
    CONSTRAINT user_email_unique UNIQUE (email)
);

create table categories
(
    id            bigint DEFAULT nextval('category_id_seq') not null,
    name          varchar                                   not null,
    slug          varchar                                   not null,
    description   varchar                                   not null,
    image         varchar,
    display_order numeric                                   not null,
    created_at    timestamp,
    updated_at    timestamp,
    primary key (id),
    CONSTRAINT category_name_unique UNIQUE (name),
    CONSTRAINT category_slug_unique UNIQUE (slug)
);

create table posts
(
    id         bigint DEFAULT nextval('post_id_seq') not null,
    title      varchar                               not null,
    url        varchar,
    content    text                                  not null,
    created_by bigint                                not null REFERENCES users (id),
    cat_id     bigint                                not null REFERENCES categories (id),
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);

create table votes
(
    id         bigint DEFAULT nextval('vote_id_seq') not null,
    user_id    bigint                                not null REFERENCES users (id),
    post_id    bigint                                not null REFERENCES posts (id),
    value      numeric                               not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id),
    UNIQUE (user_id, post_id)
);