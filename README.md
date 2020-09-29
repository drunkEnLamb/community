## beyond社区

## 部署
### 依赖
- Git
- JDK
- Maven
- Mysql
## 步骤
- yum update
- yum install git
- mkdir App
- cd App/
- git clone https://github.com/drunkEnLamb/community.git
- yum install maven
- mvn -v
- mvn compile package
- cp src/main/resources/application.properties src/main/resources/application-production.properties
- vim src/main/resources/application-production.properties
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
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

```
