insert into audience (name) values ('404-1');
insert into audience (name) values ('405-1');
insert into audience (name) values ('406-1');

insert into computer (number, audience_id, ip_address, processor, videocard, ram, total_space)
values (404600, 1, '192.168.31.1', 'Intel Core i7 4770k', 'GTX 1050', '16gb', '1tb');
insert into computer (number, audience_id, ip_address, processor, videocard, ram, total_space)
values (405600, 2, '192.168.32.3', 'Intel Core i9 9800', 'RX 580', '8gb', '1tb');
insert into computer (number, audience_id, ip_address, processor, ram, total_space)
values (406600, 3, '192.168.33.5', 'Intel Core i5 7700', '32gb', '1tb');
insert into computer (number, audience_id, ip_address, processor, ram, total_space)
values (900000, null, '192.168.33.5', 'Intel Core i5 7700', '32gb', '1tb');
insert into computer (number, audience_id, ip_address, processor, ram, total_space)
values (900001, null, '192.168.33.5', 'Intel Core i5 7700', '32gb', '1tb');

insert into subject_area (name) values ('IDE');
insert into subject_area (name) values ('OC');

insert into software_technical_details (name, subject_area_id, description, required_space)
values ('IntelliJIDEA', 1, 'Популярная IDE для разработки на языке программирования JAVA', '3GB');
insert into software_technical_details (name, subject_area_id, description, required_space)
values ('Microsoft Windows 10', 2, 'Операционная система', '10GB');
insert into software_technical_details (name, subject_area_id, description, required_space)
values ('Visual studio', 1, 'Популярная IDE для разработки на языке программирования С#', '8GB');

insert into employee (name, surname, patronymic)
values ('Геннадий', 'Звонков', 'Сергеевич');
insert into employee (name, surname, patronymic)
values ('Дмитрий', 'Старов', 'Ярославович');
insert into employee (name, surname, patronymic)
values ('Анатолий', 'Круглов', 'Фёдорович');

insert into licence_type (name) values ('Бесплатное');
insert into licence_type (name) values ('Платное');
insert into licence_type (name) values ('План-подписка');

insert into licence_details (licence_key, date_start, date_end, price, count) values ('xxxx-xxxx-xxxx-xxxx', '2022-11-05', '2024-11-05', 5000.24, 100);
insert into licence_details (licence_key, date_start, date_end, price, count) values ('yyyy-yyyy-yyyy-yyyy', '2023-05-10', '2024-05-10', 2000.48, 50);
insert into licence_details (licence_key, date_start, date_end, price, count) values ('yxxy-yxxy-yxxy-yxxy', '2023-10-04', '2025-10-04', 999.99, 30);

insert into licence (licence_details_id, licence_type_id, employee_id) values(1, 2, 3);
insert into licence (licence_details_id, licence_type_id, employee_id) values(2, 3, 1);
insert into licence (licence_details_id, licence_type_id, employee_id) values(3, 2, 2);

insert into software(software_technical_details_id, licence_id) values (3,1);
insert into software(software_technical_details_id, licence_id) values (2,2);
insert into software(software_technical_details_id, licence_id) values (1,3);

insert into computer_softwares values (1, 1);
insert into computer_softwares values (1, 2);
insert into computer_softwares values (1, 3);
insert into computer_softwares values (2, 1);
insert into computer_softwares values (2, 2);
insert into computer_softwares values (2, 3);
insert into computer_softwares values (3, 1);
insert into computer_softwares values (3, 2);
insert into computer_softwares values (3, 3);