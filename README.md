## beyond社区

## 资料
[spring文档](https://spring.io/guides)
[Github OAut](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
## 工具

## 脚本
```sql
create table user
(
	id int auto_increment
		primary key,
	account_id varchar(100) null,
	name varchar(50) null,
	token char(36) null,
	gmt_create bigint null,
	gmt_modified bigint null
);


```