INSERT INTO users (email, password, name, role, verified, created_at)
VALUES ('admin@gmail.com', '$2a$10$ZuGgeoawgOg.6AM3QEGZ3O4QlBSWyRx3A70oIcBjYPpUB8mAZWY16', 'Admin', 'ROLE_ADMIN', true,
        CURRENT_TIMESTAMP),
       ('siva@gmail.com', '$2a$10$CIXGKN9rPfV/mmBMYas.SemoT9mfVUUwUxueFpU3DcWhuNo5fexYC', 'Siva', 'ROLE_ADMIN', true,
        CURRENT_TIMESTAMP),
       ('user@gmail.com', '$2a$10$9.asbEZnVSA24cavY2xStO1FQS54WZnxUzSxqYepEoCFYAvUVnVr6', 'Demo', 'ROLE_USER', true,
        CURRENT_TIMESTAMP)
;

INSERT INTO categories(name, slug, description, image, display_order)
VALUES ('Java', 'java', 'Java and JVM related technology news, blogs, discussions etc', 'java.png', 1),
       ('WebDev', 'webdev', 'Web Development using HTML, JS, CSS and its rich ecosystem of frameworks and libraries', 'webdev.png', 2),
       ('Go', 'go', 'The Go Programming Language', 'go.png', 3),
       ('Python', 'python', 'Python Programming Language', 'python.png', 4),
       ('NodeJS', 'nodejs', 'NodeJS application development', 'nodejs.png', 5),
       ('DevOps', 'devops', 'DevOps culture, practices, techniques', 'devops.png', 6),
       ('Testing', 'testing', 'Software Testing Techniques and technologies', 'testing.png', 7),
       ('Career', 'career', 'Career Guidance and Support', 'career.png', 8),
       ('General', 'general', 'Anything related to Software Development', 'general.png', 9)
;
