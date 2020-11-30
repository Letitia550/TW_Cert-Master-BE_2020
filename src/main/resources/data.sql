DELETE FROM REQUESTS;
DELETE FROM CERTIFICATIONS;
DELETE FROM CATEGORIES;
DELETE FROM USERS;
DELETE FROM ROLES;

MERGE INTO categories KEY (id)
VALUES (1, 'Project Management'),
       (2, 'Software Testing'),
       (3, 'Software Development'),
       (4, 'Database Administration'),
       (5, 'Social Skills'),
       (6, 'Cyber Defense'),
       (7, 'Management, Audit, Legal'),
       (8, 'Industrial Control Systems'),
       (9, 'Penetration Testing'),
       (10, 'Incident Response and Forensics');


MERGE INTO certifications KEY (id)
VALUES (1, 'PMP', 2, (SELECT id FROM categories WHERE name = 'Project Management'), 500);

MERGE INTO certifications KEY (id)
VALUES (2, 'ISTQB', 3, (SELECT id FROM categories WHERE name = 'Software Testing'), 400);

MERGE INTO certifications KEY (id)
VALUES (3, 'CSDP', 1, (SELECT id FROM categories WHERE name = 'Software Development'), 600);

MERGE INTO certifications KEY (id)
VALUES (4, 'MCSA', 1, (SELECT id FROM categories WHERE name = 'Database Administration'), 550);

MERGE INTO certifications KEY (id)
VALUES (5, 'Social Skills', 2, (SELECT id FROM categories WHERE name = 'Social Skills'), 546);

MERGE INTO certifications KEY (id)
VALUES (6, 'GSEC: GIAC Security Essentials', 3, (SELECT id FROM categories WHERE name = 'Cyber Defense'), 700);

MERGE INTO certifications KEY (id)
VALUES (7, 'GSLC: GIAC Security Leadership', 4, (SELECT id FROM categories WHERE name = 'Management, Audit, Legal'), 457);

MERGE INTO certifications KEY (id)
VALUES (8, 'GRID: GIAC Response and Industrial Defense', 2, (SELECT id FROM categories WHERE name = 'Industrial Control Systems'), 400);

MERGE INTO certifications KEY (id)
VALUES (9, 'GPYC: GIAC Python Coder', 4, (SELECT id FROM categories WHERE name = 'Penetration Testing'), 560);

MERGE INTO certifications KEY (id)
VALUES (10, 'GCTI: GIAC Cyber Threat Intelligence', 1, (SELECT id FROM categories WHERE name = 'Incident Response and Forensics'), 420);

MERGE INTO certifications KEY (id)
VALUES (11, 'GSSP-JAVA: GIAC Secure Software Programmer-Java', 2, (SELECT id FROM categories WHERE name = 'Software Development'), 560);

MERGE INTO roles KEY (id)
VALUES  (1, 'Admin'),
        (2, 'Normal User');

MERGE INTO users KEY (id)
VALUES (1, 'Jeffrey Way', 'jeffreyway@gmail.com', (SELECT id FROM roles WHERE name = 'Admin'));

MERGE INTO users KEY (id)
VALUES (2, 'Barry Allen', 'barryallen@gmail.com', (SELECT id FROM roles WHERE name = 'Normal User'));

MERGE INTO users KEY (id)
VALUES (3, 'Ciera Ramirez', 'cieraramirez@gmail.com', (SELECT id FROM roles WHERE name = 'Normal User'));

MERGE INTO users KEY (id)
VALUES (4, 'Jill Mitchel', 'jillmitchel@gmail.com', (SELECT id FROM roles WHERE name = 'Normal User'));

MERGE INTO users KEY (id)
VALUES (5, 'Ricardo Gomez', 'ricardogomez@gmail.com', (SELECT id FROM roles WHERE name = 'Normal User'));

MERGE INTO users KEY (id)
VALUES (6, 'Max Mitchel', 'maxmitchel@gmail.com', (SELECT id FROM roles WHERE name = 'Normal User'));

MERGE INTO requests KEY (id)
VALUES (1, 5, 2, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                             ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                             ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                             ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (2, 3, 3, 'Pending', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                            ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                            ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                            ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (3, 2, 1, 'Rejected', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                             ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                             ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                             ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (4, 2, 10, 'Pending', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                             ' or randomised words which don''t look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                             ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                             ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (5, 3, 4, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                             ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt ' ||
                             'anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                             ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (6, 4, 3, 'Pending', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, ' ||
                             'or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt ' ||
                             'anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                             ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (7, 5, 1, 'Pending', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                            ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                            ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                            ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (8, 6, 4, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, ' ||
                             'or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                             ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                             ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (9, 6, 5, 'Pending', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                             ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt ' ||
                             'anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                             ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (10, 5, 6, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                              ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                              ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                              ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (11, 2, 7, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                              ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt ' ||
                              'anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                              ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (12, 3, 8, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                             ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt ' ||
                             'anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                             ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (13, 4, 9, 'Pending', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                              ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                              ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                              ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (14, 3, 10, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                               ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt ' ||
                               'anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                               ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (14, 4, 6, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                              ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt ' ||
                              'anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                              ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (15, 2, 2, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                              ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt ' ||
                              'anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                              ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (16, 3, 3, 'Pending', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                             ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                             ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                             ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (17, 5, 5, 'Pending', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                              ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                              ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                              ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (18, 4, 1, 'Rejected', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                              ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                              ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                              ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (19, 5, 10, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, ' ||
                               'or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                               ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                               ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (20, 6, 7, 'Pending', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                              ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                              ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, ' ||
                              'making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (21, 6, 10, 'Approved', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                               ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                               ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                               ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (22, 3, 8, 'Pending', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, ' ||
                             'or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt ' ||
                             'anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                             ' making this the first true generator on the Internet.');

MERGE INTO requests KEY (id)
VALUES (23, 2, 9, 'Rejected', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour,' ||
                              ' or randomised words which dont look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt' ||
                              ' anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,' ||
                              ' making this the first true generator on the Internet.');











