INSERT INTO account (id, created_date, email, password, role, username)
VALUES (1, '2021-01-01', 'admin@gmail.com', '$2a$10$ssJg2MpBlaqyDWli4yzFJOLT9TSRZV.Jsxkdtv1fqhGmK2Tqzt0D6', 0, 'admin');

INSERT INTO customer_identity (id, created_date, address, first_name, identification_number, identity_category, last_name, account_id)
VALUES (1, '2021-01-01', 'Address', 'Admin', '12345678910', 0, 'Admin', 1);

INSERT INTO company (id, created_date, address, first_name, identification_number, identity_category, last_name, account_id)
VALUES (1, '2021-01-01', 'Address', 'Admin', '12345678910', 0, 'Admin', 1);

INSERT INTO region (id, created_date, name) VALUES ('1', '2021-01-01', 'DKI Jakarta');
INSERT INTO region (id, created_date, name) VALUES ('2', '2021-01-01', 'Jawa Barat');

INSERT INTO city (id, created_date, name, region_id) VALUES (1, '2021-01-01', 'Jakarta Pusat', 1);
INSERT INTO city (id, created_date, name, region_id) VALUES (2, '2021-01-01', 'Jakarta Barat', 1);
INSERT INTO city (id, created_date, name, region_id) VALUES (3, '2021-01-01', 'Jakarta Timur', 1);
INSERT INTO city (id, created_date, name, region_id) VALUES (4, '2021-01-01', 'Jakarta Utara', 1);
INSERT INTO city (id, created_date, name, region_id) VALUES (5, '2021-01-01', 'Jakarta Selatan', 1);
INSERT INTO city (id, created_date, name, region_id) VALUES (6, '2021-01-01', 'Bandung', 2);
