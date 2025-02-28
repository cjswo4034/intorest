INSERT INTO category(id, group_id, kor_name, eng_name)
VALUES (default, null, '스포츠', 'sports'),
       (default, null, '금융', 'finance'),
       (default, null, '게임', 'game'),
       (default, null, '사교', 'social'),
       (default, null, '예술-문화', 'art-culture'),
       (default, null, '창작-제작', 'creation-making');

INSERT INTO category(id, group_id, kor_name, eng_name)
VALUES (default, 1, '야구', 'baseball'),
       (default, 1, '축구', 'soccer'),
       (default, 1, '농구', 'basketball'),
       (default, 2, '주식', 'stocks'),
       (default, 2, '부동산', 'real-estate'),
       (default, 2, '가상화폐', 'virtual-currency'),
       (default, 3, '롤', 'league-of-legends'),
       (default, 3, '스타', 'starcraft'),
       (default, 3, '보드게임', 'board-game'),
       (default, 4, '음주', 'drinking'),
       (default, 4, '파티', 'party'),
       (default, 4, '여행', 'travel'),
       (default, 5, '영화', 'movie'),
       (default, 5, '음악', 'music'),
       (default, 5, '책', 'book'),
       (default, 6, '공예', 'craft'),
       (default, 6, '뜨개질', 'knitting'),
       (default, 6, '드로잉', 'drawing');

INSERT INTO member(id, email, nickname, password, is_oauth2, member_status)
VALUES (default, 'email@email.com', 'nickname', '{bcrypt}$2a$10$uK5c7Mt3RB3kiaguTujatuRcjR/ARzMLcGqTuYDnO8H3l2g6XN28y', false, 'MEMBER_ACTIVE'),
       (default, 'email2@email.com', 'nickname2', '{bcrypt}$2a$10$uK5c7Mt3RB3kiaguTujatuRcjR/ARzMLcGqTuYDnO8H3l2g6XN28y', false, 'MEMBER_ACTIVE');

INSERT INTO member_roles(member_id, roles)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_USER');

INSERT INTO file_info(id, member_id, file_url, file_index, created_at)
VALUES (default, 1, 'http://domain.com/bucket/basepath/profile1.png', 0, CURRENT_TIMESTAMP),
       (default, 2, 'http://domain.com/bucket/basepath/profile2.png', 0, CURRENT_TIMESTAMP);

INSERT INTO showcase(id, member_id, category_id, content)
VALUES (default, 1, 7, 'content1'),
       (default, 1, 8, 'content2'),
       (default, 1, 9, 'content3'),
       (default, 1, 10, 'content4'),
       (default, 1, 7, 'content5'),
       (default, 2, 8, 'content6'),
       (default, 2, 9, 'content7'),
       (default, 2, 10, 'content8'),
       (default, 2, 7, 'content9');

INSERT INTO file_info(id, showcase_id, file_url, file_index, created_at)
VALUES (default, 1, 'http://domain.com/bucket/basepath/filename123.png', 1, CURRENT_TIMESTAMP),
       (default, 1, 'http://domain.com/bucket/basepath/filename223.png', 2, CURRENT_TIMESTAMP),
       (default, 2, 'http://domain.com/bucket/basepath/filename31.png', 2, CURRENT_TIMESTAMP),
       (default, 2, 'http://domain.com/bucket/basepath/filename32.png', 3, CURRENT_TIMESTAMP),
       (default, 3, 'http://domain.com/bucket/basepath/filename423.png', 3, CURRENT_TIMESTAMP),
       (default, 3, 'http://domain.com/bucket/basepath/filename413.png', 4, CURRENT_TIMESTAMP),
       (default, 3, 'http://domain.com/bucket/basepath/filename431.png', 5, CURRENT_TIMESTAMP),
       (default, 4, 'http://domain.com/bucket/basepath/filename51.png', 4, CURRENT_TIMESTAMP),
       (default, 4, 'http://domain.com/bucket/basepath/filename52.png', 5, CURRENT_TIMESTAMP),
       (default, 4, 'http://domain.com/bucket/basepath/filename53.png', 6, CURRENT_TIMESTAMP),
       (default, 4, 'http://domain.com/bucket/basepath/filename54.png', 7, CURRENT_TIMESTAMP),
       (default, 5, 'http://domain.com/bucket/basepath/filename61.png', 5, CURRENT_TIMESTAMP),
       (default, 5, 'http://domain.com/bucket/basepath/filename62.png', 6, CURRENT_TIMESTAMP),
       (default, 5, 'http://domain.com/bucket/basepath/filename63.png', 7, CURRENT_TIMESTAMP),
       (default, 5, 'http://domain.com/bucket/basepath/filename64.png', 8, CURRENT_TIMESTAMP),
       (default, 5, 'http://domain.com/bucket/basepath/filename65.png', 9, CURRENT_TIMESTAMP),
       (default, 6, 'http://domain.com/bucket/basepath/filename71.png', 6, CURRENT_TIMESTAMP),
       (default, 6, 'http://domain.com/bucket/basepath/filename72.png', 7, CURRENT_TIMESTAMP),
       (default, 6, 'http://domain.com/bucket/basepath/filename73.png', 8, CURRENT_TIMESTAMP),
       (default, 6, 'http://domain.com/bucket/basepath/filename74.png', 9, CURRENT_TIMESTAMP),
       (default, 6, 'http://domain.com/bucket/basepath/filename75.png', 10, CURRENT_TIMESTAMP),
       (default, 6, 'http://domain.com/bucket/basepath/filename76.png', 11, CURRENT_TIMESTAMP),
       (default, 7, 'http://domain.com/bucket/basepath/filename821.png', 7, CURRENT_TIMESTAMP),
       (default, 7, 'http://domain.com/bucket/basepath/filename82.png', 8, CURRENT_TIMESTAMP),
       (default, 7, 'http://domain.com/bucket/basepath/filename83.png', 9, CURRENT_TIMESTAMP),
       (default, 7, 'http://domain.com/bucket/basepath/filename84.png', 10, CURRENT_TIMESTAMP),
       (default, 7, 'http://domain.com/bucket/basepath/filename85.png', 11, CURRENT_TIMESTAMP),
       (default, 7, 'http://domain.com/bucket/basepath/filename86.png', 12, CURRENT_TIMESTAMP),
       (default, 7, 'http://domain.com/bucket/basepath/filename87.png', 13, CURRENT_TIMESTAMP),
       (default, 8, 'http://domain.com/bucket/basepath/filename91.png', 8, CURRENT_TIMESTAMP),
       (default, 8, 'http://domain.com/bucket/basepath/filename92.png', 9, CURRENT_TIMESTAMP),
       (default, 8, 'http://domain.com/bucket/basepath/filename93.png', 10, CURRENT_TIMESTAMP),
       (default, 8, 'http://domain.com/bucket/basepath/filename94.png', 11, CURRENT_TIMESTAMP),
       (default, 8, 'http://domain.com/bucket/basepath/filename95.png', 12, CURRENT_TIMESTAMP),
       (default, 8, 'http://domain.com/bucket/basepath/filename96.png', 13, CURRENT_TIMESTAMP),
       (default, 8, 'http://domain.com/bucket/basepath/filename97.png', 14, CURRENT_TIMESTAMP),
       (default, 8, 'http://domain.com/bucket/basepath/filename98.png', 15, CURRENT_TIMESTAMP),
       (default, 9, 'http://domain.com/bucket/basepath/filename11.png', 9, CURRENT_TIMESTAMP),
       (default, 9, 'http://domain.com/bucket/basepath/filename12.png', 10, CURRENT_TIMESTAMP),
       (default, 9, 'http://domain.com/bucket/basepath/filename13.png', 11, CURRENT_TIMESTAMP),
       (default, 9, 'http://domain.com/bucket/basepath/filename14.png', 12, CURRENT_TIMESTAMP),
       (default, 9, 'http://domain.com/bucket/basepath/filename15.png', 13, CURRENT_TIMESTAMP),
       (default, 9, 'http://domain.com/bucket/basepath/filename16.png', 14, CURRENT_TIMESTAMP),
       (default, 9, 'http://domain.com/bucket/basepath/filename17.png', 15, CURRENT_TIMESTAMP),
       (default, 9, 'http://domain.com/bucket/basepath/filename18.png', 16, CURRENT_TIMESTAMP),
       (default, 9, 'http://domain.com/bucket/basepath/filename19.png', 17, CURRENT_TIMESTAMP);

INSERT INTO showcase_comment(id, member_id, showcase_id, content)
VALUES (default, 1, 1, 'content1'),
       (default, 2, 2, 'content2'),
       (default, 1, 2, 'content3'),
       (default, 2, 3, 'content4'),
       (default, 1, 3, 'content5'),
       (default, 2, 3, 'content6'),
       (default, 1, 4, 'content7'),
       (default, 2, 4, 'content8'),
       (default, 1, 4, 'content9'),
       (default, 2, 4, 'content10'),
       (default, 1, 5, 'content11'),
       (default, 2, 5, 'content12'),
       (default, 1, 5, 'content13'),
       (default, 2, 5, 'content14'),
       (default, 1, 5, 'content15'),
       (default, 2, 6, 'content16'),
       (default, 1, 6, 'content17'),
       (default, 2, 6, 'content18'),
       (default, 1, 6, 'content19'),
       (default, 2, 6, 'content20'),
       (default, 1, 6, 'content20'),
       (default, 2, 7, 'content21'),
       (default, 1, 7, 'content22'),
       (default, 2, 7, 'content23'),
       (default, 1, 7, 'content24'),
       (default, 2, 7, 'content25'),
       (default, 1, 7, 'content26'),
       (default, 2, 7, 'content27'),
       (default, 1, 8, 'content28'),
       (default, 2, 8, 'content29'),
       (default, 1, 8, 'content30'),
       (default, 2, 8, 'content31'),
       (default, 1, 8, 'content32'),
       (default, 2, 8, 'content33'),
       (default, 1, 8, 'content34'),
       (default, 2, 8, 'content35'),
       (default, 1, 9, 'content36');