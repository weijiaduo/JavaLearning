## CVTE 面试


#### 自我介绍


#### 介绍一下你比较熟悉的项目


#### Spring 的两个特性，IoC 的实现原理

IoC利用java反射机制，AOP利用代理模式。

实现AOP的技术，主要分为两大类：一是采用动态代理技术，利用截取消息的方式，对该消息进行装饰，以取代原有对象行为的执行；二是采用静态织入的方式，引入特定的语法创建“方面”，从而使得编译器可以在编译期间织入有关“方面”的代码，属于静态代理。


#### IoC Bean 注入的几种方式

1. 构造器注入
2. Setter方法注入
3. 接口注入


#### Spring Bean 的作用域

1. singleton：这是默认的范围，这种范围确保不管接受到多少个请求，每个容器中只有一个bean的实例，单例的模式由bean factory自身来维护。
2. prototype：原型范围与单例范围相反，为每一个bean请求提供一个新创建的实例。
3. request：Web应用中使用，就是在一次request请求中只会创建一个bean实例，请求完成以后，bean会失效并被垃圾回收器回收。
4. session：Web应用中使用，就是每个session中只创建一个bean实例，在session过期后，bean会随之失效。


#### 怎么防止 sql 攻击

>所谓SQL注入，就是通过把SQL命令插入到Web表单递交或输入域名或页面请求的查询字符串，最终达到欺骗服务器执行恶意的SQL命令。

1. 永远不要信任用户的输入。对用户的输入进行校验，可以通过正则表达式，或限制长度；对单引号和双”-“进行转换等；
2. 永远不要使用动态拼装sql，可以使用参数化的sql或者直接使用存储过程进行数据查询存取；
3. 永远不要使用管理员权限的数据库连接，为每个应用使用单独的权限有限的数据库连接；
4. 不要把机密信息直接存放，加密或者hash掉密码和敏感的信息；
5. 应用的异常信息应该给出尽可能少的提示，最好使用自定义的错误信息对原始错误信息进行包装。


#### synchronized 和 Lock 的区别

1. 存在层次：synchronized是Java关键字，属于JVM层面；Lock是一个类；
2. 锁的释放：synchronized执行同步代码块时会自动加锁和解锁，发生异常时JVM会释放锁；Lock发生异常时候，不会主动释放占有的锁，必须手动unlock来释放锁。因此Lock需要在try中加锁，以及在finally中释放锁；
3. 锁的状态：synchronized不可判断锁状态；Lock可以判断锁状态；
4. 可中断锁:使用synchronized，除非该线程成功获取到锁，否则将一直阻塞住;而Lock锁提供了lockInterruptibly()接口，可以中断锁等待，跳过这部分去做其他事;
5. 公平锁：synchronized是非公平锁，无法保证线程按照申请锁的顺序获得锁；而Lock锁提供了可选参数，可以配置成公平锁（默认是非公平锁）。


#### lock 什么时候加锁和解锁

Lock发生异常时候，不会主动释放占有的锁，必须手动unlock来释放锁，可能引起死锁的发生。所以最好将同步代码块用try catch包起来，finally中写入unlock，避免死锁的发生。


#### synchronized 是乐观锁还是悲观锁

从处理问题的方式上说，Synchronized互斥同步属于一种悲观的并发策略，总是认为只要不去做正确的同步措施（例如加锁），那就肯定会出现问题，无论共享数据是否真的会出现竞争，它都要进行加锁（这里讨论的是概念模型，实际上虚拟机会优化掉很大一部分不必要的加锁）、用户态核心态转换、维护锁计数器和检查是否有被阻塞的线程需要唤醒等操作。


#### synchronized 从以前的重量级锁变轻了，怎么实现的

synchronized进行很多了优化，有适应自旋，锁消除，锁粗化，轻量级锁，偏向锁等等。导致在Java1.6上synchronized的性能并不比Lock差。


#### CAS 的原理

 简单的来说，CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则不执行更新，但是无论是否更新了V的值，都会返回V的旧值。

#### volatile 能不能防止线程安全

volatile只能保证可见性，不能保证线程安全。在不符合以下两条规则的运算场景中，必须要加锁来保持原子性：
- 运算结果并不依赖于变量的当前值，或者能够确保只有单一线程修改变量的值；
- 变量不需要与其他的状态变量共同参与不变约束。


#### Mysql 数据库索引的结构

Mysql目前主要有以下几种索引类型：FULLTEXT，HASH，BTREE，RTREE。

1. FULLTEXT：即为全文索引，目前只有MyISAM引擎支持。其可以在CREATE TABLE ，ALTER TABLE，CREATE INDEX 使用，不过目前只有 CHAR、VARCHAR ，TEXT 列上可以创建全文索引。全文索引并不是和MyISAM一起诞生的，它的出现是为了解决WHERE name LIKE “%word%"这类针对文本的模糊查询效率较低的问题。

2. HASH：由于HASH的唯一（几乎100%的唯一）及类似键值对的形式，很适合作为索引。HASH索引可以一次定位，不需要像树形索引那样逐层查找,因此具有极高的效率。但是，这种高效是有条件的，即只在“=”和“in”条件下高效，对于范围查询、排序及组合索引仍然效率不高。

3. BTREE：BTREE索引就是一种将索引值按一定的算法，存入一个树形的数据结构中（二叉树），每次查询都是从树的入口root开始，依次遍历node，获取leaf。这是MySQL里默认和最常用的索引类型。

4. RTREE：RTREE在MySQL很少使用，仅支持geometry数据类型，支持该类型的存储引擎只有MyISAM、BDb、InnoDb、NDb、Archive几种。


#### Mysql 的数据类型

主要包括以下五大类：
1. 整数类型：BIT、BOOL、TINY INT、SMALL INT、MEDIUM INT、INT、 BIG INT
2. 浮点数类型：FLOAT、DOUBLE、DECIMAL
3. 字符串类型：CHAR、VARCHAR、TINY TEXT、TEXT、MEDIUM TEXT、LONGTEXT、TINY BLOB、BLOB、MEDIUM BLOB、LONG BLOB
4. 日期类型：Date、DateTime、TimeStamp、Time、Year
5. 其他数据类型：BINARY、VARBINARY、ENUM、SET、Geometry、Point、MultiPoint、LineString、MultiLineString、Polygon、GeometryCollection等


#### HTTP状态码

1**：信息性状态码

2**：成功状态码
- 200：OK 请求正常处理
- 204：No Content请求处理成功，但没有资源可返回
- 206：Partial Content对资源的某一部分的请求

3**：重定向状态码
- 301：Moved Permanently 永久重定向
- 302：Found 临时性重定向
- 304：Not Modified 缓存中读取

4**：客户端错误状态码
- 400：Bad Request 请求报文中存在语法错误
- 401：Unauthorized需要有通过Http认证的认证信息
- 403：Forbidden访问被拒绝
- 404：Not Found无法找到请求资源

5**：服务器错误状态码
- 500：Internal Server Error 服务器端在执行时发生错误
- 503：Service Unavailable 服务器处于超负载或者正在进行停机维护


#### 最近在学什么，看什么书

Spring或者SpringMVC依然有许多东西需要我们进行配置，这样不仅徒增工作量而且在跨平台部署时容易出问题。由于存在这些问题，Spring Boot应运而生，使用Spring Boot可以让我们快速创建一个基于Spring的项目，而让这个Spring项目跑起来我们只需要很少的配置就可以了。Spring Boot主要有如下核心功能：
- 独立运行的Spring项目：Spring Boot可以以jar包的形式来运行。
- 内嵌Servlet容器：Spring Boot可以内嵌Tomcat，这样我们无需以war包的形式部署项目。
- 提供starter简化Maven配置：Spring Boot 通过starter能够帮助我们简化Maven配置。
- 自动配置Spring
- 准生产的应用监控
- 无代码生成和xml配置

#### 有什么想问的
