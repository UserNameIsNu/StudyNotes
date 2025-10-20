create table goods_info (
   g_id int primary key auto_increment,
   g_name varchar(50),
   stock int
);

insert into goods_info(g_name, stock) values('iPhone13',50),('macbook pro',50);

create table order_info (
   order_id int primary key auto_increment,
   order_status tinyint(1),
   user_id int,
   goods_id int
);
