-- create database ssm character set utf8;

drop table if exists tb_user;drop table if exists tb_customer;

create table tb_user(
  id int primary key auto_increment,
  username varchar(100),
  password varchar(100)
) default charset = utf8;

create table tb_customer(
  id int primary key auto_increment,
  name varchar(100),
  telephone varchar(100),
  address varchar(100),
  remark varchar(100)
) default charset = utf8;

insert into tb_user values(1,'admin','admin');


insert into tb_customer values(1,'张三','123456789','你猜','不想写备注');
insert into tb_customer values(2,'张三2','123456789','你猜','不想写备注');
insert into tb_customer values(3,'张三5','123456789','你猜','不想写备注');
insert into tb_customer values(4,'张三15','123456789','你猜','不想写备注');
insert into tb_customer values(5,'张三16','123456789','你猜','不想写备注');
insert into tb_customer values(6,'张三21','123456789','你猜','不想写备注');

