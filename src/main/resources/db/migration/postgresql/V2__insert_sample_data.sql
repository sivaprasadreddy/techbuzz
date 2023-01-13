INSERT INTO users (email, password, name, role, auth_provider, created_at)
VALUES ('sivaprasadreddy.k@gmail.com', '$2a$10$ZuGgeoawgOg.6AM3QEGZ3O4QlBSWyRx3A70oIcBjYPpUB8mAZWY16', 'Admin', 'ROLE_ADMIN', 'LOCAL',
        CURRENT_TIMESTAMP)
;

INSERT INTO categories(name, slug, description, image, display_order)
VALUES ('Java', 'java', 'Java News/Tech/Discussion/etc', 'java.png', 1),
       ('WebDev', 'webdev', 'Web Development', 'webdev.png', 2),
       ('Go', 'go', 'The Go Programming Language', 'go.png', 3),
       ('Python', 'python', 'Python Programming Language', 'python.png', 3),
       ('Cloud', 'cloud', 'Cloud Computing', 'cloud.png', 5),
       ('DevOps', 'devops', 'DevOps culture, practices, techniques', 'devops.png', 6),
       ('Testing', 'testing', 'Testing Techniques', 'testing.png', 7),
       ('Career', 'career', 'Career Guidance and Support', 'career.png', 8),
       ('General', 'general', 'General Topics', 'general.png', 9)
;
