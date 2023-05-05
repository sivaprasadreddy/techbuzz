INSERT INTO users (email, password, name, role, verified, created_at)
VALUES ('sivalabs.in@gmail.com', '$2a$10$0ayT2AX2neetmzmQFzuIA.sQOEyLzNN9jvEHv6/lqBexsqndZGyDK', 'SivaLabs', 'ROLE_ADMIN', true, CURRENT_TIMESTAMP)
;

INSERT INTO categories(name, slug, description, image, display_order)
VALUES ('Java', 'java', 'Java and JVM related technologies', 'java.png', 1),
       ('WebDev', 'webdev', 'Web development using HTML, JS, CSS', 'webdev.png', 2),
       ('Go', 'go', 'Go programming language', 'go.png', 3),
       ('Python', 'python', 'Python programming language', 'python.png', 4),
       ('NodeJS', 'nodejs', 'NodeJS application development', 'nodejs.png', 5),
       ('DevOps', 'devops', 'DevOps culture, practices, tools and techniques', 'devops.png', 6),
       ('Testing', 'testing', 'Software Testing techniques and technologies', 'testing.png', 7),
       ('Career', 'career', 'Career guidance and support', 'career.png', 8),
       ('General', 'general', 'Anything related to software development', 'general.png', 9)
;
