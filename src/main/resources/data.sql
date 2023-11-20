INSERT INTO users (ID, CREATED_DATE_TIME, MODIFIED_DATE_TIME, DISPLAY_NAME, IMAGE_URL, NICK_NAME, SOCIAL_LOGIN_ID,
                   AUTHORITY)
VALUES (DEFAULT, NOW(), NOW(), '김기가#2220', 'https://image1.png', '김기가', '8719237122200', 'USER'),
       (DEFAULT, NOW(), NOW(), '김하나#1233', 'https://image2.png', '김하나', '2719237112330', 'USER'),
       (DEFAULT, NOW(), NOW(), '김두리#0292', 'https://image3.png', '김두리', '7192371202920', 'USER'),
       (DEFAULT, NOW(), NOW(), '김세모#2921', 'https://image4.png', '김세모', '9213920129212', 'USER'),
       (DEFAULT, NOW(), NOW(), '김네모#8372', 'https://image5.png', '김네모', '9292910283729', 'USER');


INSERT INTO store(id, name, location_name, created_date_time, modified_date_time)
VALUES (DEFAULT, 'AA커피', '홍대입구역점', now(), now()),
       (DEFAULT, 'BB커피', '합정역점', now(), now()),
       (DEFAULT, 'CC커피', '신촌역점', now(), now()),
       (DEFAULT, 'DD커피', '당산역점', now(), now());