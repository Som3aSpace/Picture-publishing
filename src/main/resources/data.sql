INSERT INTO Status (status) VALUES
('pending'),
('accepted'),
('rejected');

INSERT INTO Role (id,description,name) VALUES
(1,'normal user','USER'),
(2,'admin','ADMIN');

--adding built-in admin pass is admin123
INSERT  INTO USERS (email,password,userName) VALUES ('admin@admin.yeshtery.com',
                                                     '$2a$10$.W66cPvuN35OWJLQIMSrhOcqiW4cTD3286VpwRxiXmtijNofi9pMm',
                                                     'admin');

INSERT INTO USER_ROLES (USER_ID,ROLE_ID) VALUES (1,1),(1,2);