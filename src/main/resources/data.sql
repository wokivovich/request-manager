
insert into usr(id, password, username) values (1, '$2y$10$cyGeM2kcs2srf8T56uyUFu8Ttlj8XQtPhfT.K8QDtG9BeJIwtZjN2', 'user1');
insert into usr(id, password, username) values (2, '$2y$10$cyGeM2kcs2srf8T56uyUFu8Ttlj8XQtPhfT.K8QDtG9BeJIwtZjN2', 'user2');
insert into usr(id, password, username) values (3, '$2y$10$/E29SR1eS1xO68rE3GYVM.mXVoqKsFCQ9IzLjs15eBjWw3PMSU2fK', 'operator');
insert into usr(id, password, username) values (4, '$2y$10$KMrx7NyK0RrORmadl7KLQuUFz6659O8sH6VBqzDfLG50774bMIGfy', 'admin');

insert into roles(role, user_id) values (0, 1);
insert into roles(role, user_id) values (0, 2);
insert into roles(role, user_id) values (1, 3);
insert into roles(role, user_id) values (2, 4);
