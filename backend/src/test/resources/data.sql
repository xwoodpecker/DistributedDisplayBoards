-- boards
INSERT INTO boards(id, boardname, location) VALUES (1, 'testboard1', 'location1');
INSERT INTO boards(id, boardname, location) VALUES (2, 'testboard2', 'location2');
INSERT INTO boards(id, boardname, location) VALUES (3, 'testboard3', 'location3');
INSERT INTO boards(id, boardname, location) VALUES (4, 'testboard4', 'location4');

-- users
INSERT INTO users(id, email, enabled, password, username) VALUES (1, 'User1@mail', 1, '123456', 'User1');
INSERT INTO users(id, email, enabled, password, username) VALUES (2, 'User2@mail', 1, '123456', 'User2');
INSERT INTO users(id, email, enabled, password, username) VALUES (3, 'User3@mail', 1, '123456', 'User3');
INSERT INTO users(id, email, enabled, password, username) VALUES (4, 'Coordinator1@mail', 1, '123456', 'Coordinator1');
INSERT INTO users(id, email, enabled, password, username) VALUES (5, 'Coordinator2@mail', 1, '123456', 'Coordinator2');
INSERT INTO users(id, email, enabled, password, username) VALUES (6, 'Admin@mail', 1, '123456', 'Admin');
INSERT INTO users(id, email, enabled, password, username) VALUES (7, 'User4@mail', 1, '123456', 'User4');
INSERT INTO users(id, email, enabled, password, username) VALUES (8, 'User5@mail', 1, '123456', 'User5');

-- board_groups
INSERT INTO board_groups(id, groupname, board_id, coordinator_id) VALUES (1, 'testgroup1', 1, 4);
INSERT INTO board_groups(id, groupname, board_id, coordinator_id) VALUES (2, 'testgroup2', 2, 5);
INSERT INTO board_groups(id, groupname, board_id, coordinator_id) VALUES (3, 'testgroup3', 2, 8);

-- groups_users
INSERT INTO groups_users(groups_id, users_id) VALUES (1, 1);
INSERT INTO groups_users(groups_id, users_id) VALUES (1, 2);
INSERT INTO groups_users(groups_id, users_id) VALUES (1, 4);
INSERT INTO groups_users(groups_id, users_id) VALUES (2, 3);
INSERT INTO groups_users(groups_id, users_id) VALUES (2, 5);
INSERT INTO groups_users(groups_id, users_id) VALUES (3, 3);
INSERT INTO groups_users(groups_id, users_id) VALUES (3, 5);
INSERT INTO groups_users(groups_id, users_id) VALUES (3, 7);
INSERT INTO groups_users(groups_id, users_id) VALUES (3, 8);

-- messages
INSERT INTO messages(id, active, content, end_date, board_id, user_id, display_time, bg_color) VALUES (1, 1, 'content of message 1', '2024-03-01 17:03:17.000000', 1, 1, 120, 'white');
INSERT INTO messages(id, active, content, end_date, board_id, user_id, display_time, bg_color) VALUES (2, 1, 'content of message 2', '2024-03-01 17:03:17.000000', 1, 1, 80, 'white');
INSERT INTO messages(id, active, content, end_date, board_id, user_id, display_time, bg_color) VALUES (3, 1, 'content of message 3', '2021-03-01 17:03:17.000000', 1, 2, 120, 'white');
INSERT INTO messages(id, active, content, end_date, board_id, user_id, display_time, bg_color) VALUES (4, 1, 'content of message 4', '2024-03-01 17:03:17.000000', 2, 3, 60, 'white');
INSERT INTO messages(id, active, content, end_date, board_id, user_id, display_time, bg_color) VALUES (5, 0, 'content of message 5', '2024-03-01 17:03:17.000000', 2, 5, 120, 'white');
INSERT INTO messages(id, active, content, end_date, board_id, user_id, display_time, bg_color) VALUES (6, 0, 'content of message 6', '2021-03-01 17:03:17.000000', 2, 6, 40, 'white');
INSERT INTO messages(id, active, content, end_date, board_id, user_id, display_time, bg_color) VALUES (7, 0, 'content of message 7', '2021-03-01 17:03:17.000000', 3, 7, 40, 'white');

-- roles
INSERT INTO roles(id, name) VALUES (1, 'SUPERVISOR');
INSERT INTO roles(id, name) VALUES (2, 'COORDINATOR');
INSERT INTO roles(id, name) VALUES (3, 'USER');

-- users_roles
INSERT INTO users_roles(roles_id, users_id) VALUES ('3', 1);
INSERT INTO users_roles(roles_id, users_id) VALUES ('3', 2);
INSERT INTO users_roles(roles_id, users_id) VALUES ('3', 3);
INSERT INTO users_roles(roles_id, users_id) VALUES ('3', 4);
INSERT INTO users_roles(roles_id, users_id) VALUES ('3', 5);
INSERT INTO users_roles(roles_id, users_id) VALUES ('3', 6);
INSERT INTO users_roles(roles_id, users_id) VALUES ('3', 7);
INSERT INTO users_roles(roles_id, users_id) VALUES ('3', 8);
INSERT INTO users_roles(roles_id, users_id) VALUES ('2', 4);
INSERT INTO users_roles(roles_id, users_id) VALUES ('2', 5);
INSERT INTO users_roles(roles_id, users_id) VALUES ('2', 7);
INSERT INTO users_roles(roles_id, users_id) VALUES ('1', 6);