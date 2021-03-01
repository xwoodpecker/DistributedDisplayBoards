INSERT INTO boards(id, boardname) VALUES (1, 'testboard1');
INSERT INTO boards(id, boardname) VALUES (2, 'testboard2');

INSERT INTO users(id, email, enabled, password, username) VALUES (1, 'User1@mail', 1, '123456', 'User1');
INSERT INTO users(id, email, enabled, password, username) VALUES (2, 'User2@mail', 1, '123456', 'User2');
INSERT INTO users(id, email, enabled, password, username) VALUES (3, 'User3@mail', 1, '123456', 'User3');
INSERT INTO users(id, email, enabled, password, username) VALUES (4, 'Coordinator1@mail', 1, '123456', 'Coordinator1');
INSERT INTO users(id, email, enabled, password, username) VALUES (5, 'Coordinator2@mail', 1, '123456', 'Coordinator2');
INSERT INTO users(id, email, enabled, password, username) VALUES (6, 'Admin@mail', 1, '123456', 'Admin');

INSERT INTO board_groups(id, groupname, board_id, coordinator_id) VALUES (1, 'testgroup1', 1, 4);
INSERT INTO board_groups(id, groupname, board_id, coordinator_id) VALUES (2, 'testgroup2', 2, 5);

INSERT INTO groups_users(groups_id, users_id) VALUES (1, 1);
INSERT INTO groups_users(groups_id, users_id) VALUES (1, 2);
INSERT INTO groups_users(groups_id, users_id) VALUES (1, 4);
INSERT INTO groups_users(groups_id, users_id) VALUES (2, 3);
INSERT INTO groups_users(groups_id, users_id) VALUES (2, 5);

INSERT INTO messages(id, active, content, end_date, board, sender, display_time, bgColor) VALUES (1, 1, 'content of message 1', '2024-03-01 17:03:17.000000', 1, 1, 120);
INSERT INTO messages(id, active, content, end_date, board, sender, display_time, bgColor) VALUES (2, 1, 'content of message 2', '2024-03-01 17:03:17.000000', 1, 1, 80);
INSERT INTO messages(id, active, content, end_date, board, sender, display_time, bgColor) VALUES (3, 1, 'content of message 3', '2021-03-01 17:03:17.000000', 1, 2, 120);
INSERT INTO messages(id, active, content, end_date, board, sender, display_time, bgColor) VALUES (4, 1, 'content of message 4', '2021-03-01 17:03:17.000000', 2, 3, 60);
INSERT INTO messages(id, active, content, end_date, board, sender, display_time, bgColor) VALUES (5, 0, 'content of message 5', '2024-03-01 17:03:17.000000', 2, 5, 120);
INSERT INTO messages(id, active, content, end_date, board, sender, display_time, bgColor) VALUES (6, 0, 'content of message 6', '2021-03-01 17:03:17.000000', 2, 6, 40);


INSERT INTO roles(name) VALUES ('COORDINATOR');
INSERT INTO roles(name) VALUES ('SUPERVISOR');
INSERT INTO roles(name) VALUES ('USER');

INSERT INTO users_roles(roles_name, users_id) VALUES ('USER', 1);
INSERT INTO users_roles(roles_name, users_id) VALUES ('USER', 2);
INSERT INTO users_roles(roles_name, users_id) VALUES ('USER', 3);
INSERT INTO users_roles(roles_name, users_id) VALUES ('COORDINATOR', 4);
INSERT INTO users_roles(roles_name, users_id) VALUES ('COORDINATOR', 5);
INSERT INTO users_roles(roles_name, users_id) VALUES ('ADMIN', 6);