insert into address(id, line_one, street_name, town, province, postal_code, country)
VALUES (1, '123 Bedford View Estate', '12 Rivers Street', 'Pretoria', 'Gauteng', '1897', 'ZA');

insert into customer(id, name, phone_no, email, dob, address_id)
VALUES (1, 'Joseph', '+27 47 781 3065', 'josi@example.com', '1993-05-11', 1);

insert into product_type(id, description, name)
VALUES (1, 'RETIREMENT', '401k retirement account');
insert into product_type(id, description, name)
VALUES (2, 'SAVINGS', 'Tax-free savings account');

insert into product(id, balance, account_number, customer_id, product_type_id)
VALUES (1, 500000.00, 12345, 1, 1);
insert into product(id, balance, account_number, customer_id, product_type_id)
VALUES (2, 36000.00, 12345, 1, 2);