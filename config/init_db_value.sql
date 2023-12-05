INSERT INTO resume(uuid, full_name) VALUES ('ee7bd0ab-542e-43e1-9096-11522b2000ad', 'Name1');
INSERT INTO resume(uuid, full_name) VALUES ('c5069a8e-68fd-4501-b41b-22e6eb57233f', 'Name2');
INSERT INTO resume(uuid, full_name) VALUES ('95100986-0ad3-435d-bd07-e1bdd7eddc8c', 'Name3');
INSERT INTO contact(type, value, resume_uuid) VALUES ('SKYPE', 'skypeTest', 'ee7bd0ab-542e-43e1-9096-11522b2000ad');
INSERT INTO contact(type, value, resume_uuid) VALUES ('SKYPE', 'skype_1', 'c5069a8e-68fd-4501-b41b-22e6eb57233f');
INSERT INTO contact(type, value, resume_uuid) VALUES ('PHONE', '+375291232391', 'c5069a8e-68fd-4501-b41b-22e6eb57233f');
INSERT INTO contact(type, value, resume_uuid) VALUES ('MAIL', 'av@by.ru', 'ee7bd0ab-542e-43e1-9096-11522b2000ad');
INSERT INTO contact(type, value, resume_uuid) VALUES ('MAIL', 'skype_1@mail.ru', 'c5069a8e-68fd-4501-b41b-22e6eb57233f');
INSERT INTO contact(type, value, resume_uuid) VALUES ('MAIL', 'test@yandex.ru', '95100986-0ad3-435d-bd07-e1bdd7eddc8c');