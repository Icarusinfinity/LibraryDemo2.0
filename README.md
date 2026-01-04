图书馆管理系统运行说明和依赖清单
运行说明
1. 环境要求
Java版本: Java 17或更高版本
数据库: MySQL 5.7或更高版本
构建工具: Maven 3.6或更高版本
操作系统: Windows/Linux/macOS
2. 数据库配置
安装并启动MySQL数据库服务
创建数据库 library (可选，如不存在会自动创建)
修改 src/main/resources/application.properties 中的数据库连接信息：
properties
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
3. 项目构建和运行
克隆或下载项目代码
使用Maven构建项目:
bash
cd d:\IDEA\ideaproject\Library
mvn clean compile
运行项目:
bash
mvn spring-boot:run
访问系统:
项目启动后，默认端口为8080
访问地址: http://localhost:8080
API文档: 参考项目根目录的API接口文档.md
4. 默认账户信息
管理员账户:
用户名: admin (或邮箱: admin@example.com)
密码: 123456
5. 系统功能入口
用户管理: /api/users
图书管理: /api/books
借阅管理: /api/borrow, /api/return
认证接口: /api/auth/login, /api/auth/register
统计报表: /api/dashboard
依赖清单
1. 核心依赖
Spring Boot: 4.0.1
spring-boot-starter-web
MyBatis: 3.0.3
mybatis-spring-boot-starter
MySQL连接器: 8.0.x
mysql-connector-j
Lombok: 1.18.x
用于简化实体类代码
2. 开发依赖
Spring Boot DevTools: 运行时开发工具
Spring Boot Test: 测试支持
3. Maven插件配置
maven-compiler-plugin: 编译Java代码
spring-boot-maven-plugin: Spring Boot应用打包和运行
Lombok注解处理器: 处理Lombok注解
4. 完整pom.xml依赖
xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>3.0.3</version>
    </dependency>
    
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
5. 配置文件
application.properties: 主配置文件
schema.sql: 数据库初始化脚本
6. 系统端口
默认端口: 8080
可在 application.properties 中修改 server.port 参数
7. 注意事项
确保MySQL服务已启动并可连接
首次运行会自动执行数据库初始化脚本
如遇到数据库连接问题，请检查用户名密码是否正确
系统需要允许PublicKeyRetrieval，已在连接字符串中配置相应参数
