## 学习社区

## 资料
  [Spring 文档](https://spring.io/guides)  
  [Spring Web](https://spring.io/guides/gs/serving-web-content/ )  
  [es 社区](https://elasticsearch.cn/explore)  
  [Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)  
  [Bootstrap](https://v3.bootcss.com/getting-started/)  
  [Github OAuth Document](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
  [Spring](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)  
  [themeleaf](https://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html#expressions-on-selections-asterisk-syntax)  
  [octotree](https://www.octotree.io)

  
## 工具
  [Git](https://git-scm.com/downloads)  
  [Visual-Paradigm](https://www.visual-paradigm.com)  
  [Flyway](https://flywaydb.org/getstated/firststeps/maven)  
  [lombok](https://projectlombok.org/features/all)  
  [MyBatis Generator](http://mybatis.org/generator/)  
  [Json](http://jsoneditoronline.org/)
  
## 脚本
```sql
 CREATE TABLE USER
    ID INTEGER DEFAULT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR (50),
    TOKEN VARCHAR (36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
```

```bash

 mvn flyway:migrate
 mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```