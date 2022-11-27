INSERT INTO users(username, password, enabled)
VALUES (1, 'user','pass',true);

INSERT INTO users(username, password, enabled)
VALUES (2, 'admin','pass',true);

INSERT INTO authorities(username, authority)
VALUES ('user','ROLE_USER');

INSERT INTO authorities(username, authority)
VALUES ('admin','ROLE_ADMIN');