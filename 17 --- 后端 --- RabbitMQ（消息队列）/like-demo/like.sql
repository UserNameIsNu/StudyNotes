-- 用户表
create table user_info
(
    user_id   int primary key auto_increment,
    user_name char(20) not null
);
insert into user_info(user_name)
values ('张三'),
       ('李四'),
       ('王五');

-- 帖子表
create table post_info
(
    post_id    int primary key auto_increment,
    title      varchar(50)  not null,
    content    varchar(200) not null,
    like_count bigint default 0, -- 点赞总数
    user_id    int
);
insert into post_info(title, content, user_id)
values ('我的暑假', '暑假太无聊了', 1),
       ('心情不错', '哈哈哈哈哈哈', 2),
       ('心情不好', '今天为什么一直下雨', 3);

-- 点赞表
create table like_info
(
    post_id int not null, -- 帖子id
    user_id int not null -- 点赞人id
);
insert into like_info(post_id, user_id)
values (1, 2),
       (1, 3),
       (2, 1),
       (3, 2);