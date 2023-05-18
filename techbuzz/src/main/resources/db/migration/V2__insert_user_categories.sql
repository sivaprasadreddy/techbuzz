INSERT INTO users (email, password, name, role, verified, created_at)
VALUES ('sivalabs.in@gmail.com', '$2a$10$0ayT2AX2neetmzmQFzuIA.sQOEyLzNN9jvEHv6/lqBexsqndZGyDK', 'SivaLabs', 'ROLE_ADMIN', true, CURRENT_TIMESTAMP)
;

INSERT INTO categories(id, name, slug, description, image, display_order)
VALUES (1, 'Java', 'java', 'Java and JVM related technologies', 'java.png', 1),
       (2, 'WebDev', 'webdev', 'Web development using HTML, JS, CSS', 'webdev.png', 2),
       (3, 'Go', 'go', 'Go programming language', 'go.png', 3),
       (4, 'Python', 'python', 'Python programming language', 'python.png', 4),
       (5, 'NodeJS', 'nodejs', 'NodeJS application development', 'nodejs.png', 5),
       (6, 'DevOps', 'devops', 'DevOps culture, practices, tools and techniques', 'devops.png', 6),
       (7, 'Testing', 'testing', 'Software Testing techniques and technologies', 'testing.png', 7),
       (8, 'Career', 'career', 'Career guidance and support', 'career.png', 8),
       (9, 'General', 'general', 'Anything related to software development', 'general.png', 9)
;
