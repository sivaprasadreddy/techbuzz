INSERT INTO users (email, password, name, role, verified, created_at)
VALUES ('admin@gmail.com', '$2a$10$ZuGgeoawgOg.6AM3QEGZ3O4QlBSWyRx3A70oIcBjYPpUB8mAZWY16', 'Admin', 'ROLE_ADMIN', true, CURRENT_TIMESTAMP),
       ('user@gmail.com', '$2a$10$9.asbEZnVSA24cavY2xStO1FQS54WZnxUzSxqYepEoCFYAvUVnVr6', 'Demo', 'ROLE_USER', true, CURRENT_TIMESTAMP)
;