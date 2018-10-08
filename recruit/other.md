#### sleep( ) 和 wait( n)、wait( ) 的区别：

1. sleep 方法：是 Thread 类的静态方法，当前线程将睡眠 n 毫秒，线程进入阻塞状态。当睡眠时间到了，会解除阻塞，进行可运行状态，等待 CPU 的到来。睡眠不释放锁（如果有的话）；
2. wait 方法：是 Object 的方法，必须与 synchronized 关键字一起使用，线程进入阻塞状态，当 notify 或者 notifyall 被调用后，会解除阻塞。但是，只有重新占用互斥锁之后才会进入可运行状态。睡眠时，释放互斥锁。


#### 集合线程安全分类

1. 线程安全(Thread-safe)的集合对象：
 - Vector
 - Hashtable

2. 非线程安全的集合对象
 - ArrayList
 - LinkedList
 - HashMap
 - HashSet
 - TreeMap
 - TreeSet


#### Spring中ApplicationContext和beanfactory区别

1. BeanFacotry是spring中比较原始的Factory。如XMLBeanFactory就是一种典型的BeanFactory。原始的BeanFactory无法支持spring的许多插件，如AOP功能、Web应用等。
2. ApplicationContext接口，它由BeanFactory接口派生而来，因而提供BeanFactory所有的功能。ApplicationContext以一种更向面向框架的方式工作以及对上下文进行分层和实现继承，ApplicationContext包还提供了以下的功能： 
 - MessageSource, 提供国际化的消息访问 
 - 资源访问，如URL和文件 
 - 事件传播 
 - 载入多个（有继承关系）上下文 ，使得每一个上下文都专注于一个特定的层次，比如应用的web层 
3. 其他区别
 - BeanFactroy采用的是延迟加载形式来注入Bean的，即只有在使用到某个Bean时(调用getBean())，才对该Bean进行加载实例化，这样，我们就不能发现一些存在的Spring的配置问题。而ApplicationContext则相反，它是在容器启动时，一次性创建了所有的Bean。这样，在容器启动时，我们就可以发现Spring中存在的配置错误。 
 - BeanFactory和ApplicationContext都支持BeanPostProcessor、BeanFactoryPostProcessor的使用，但两者之间的区别是：BeanFactory需要手动注册，而ApplicationContext则是自动注册


#### 同步与异步传输的区别

1. 异步传输是面向字符的传输，而同步传输是面向比特的传输。
2. 异步传输的单位是字符而同步传输的单位是帧。
3. 异步传输通过字符起止的开始和停止码抓住再同步的机会，而同步传输则是以数据中抽取同步信息。
4. 异步传输对时序的要求较低，同步传输往往通过特定的时钟线路协调时序。
5. 异步传输相对于同步传输效率较低。


#### MVC设计模式

- Model（模型）：用于处理应用程序数据逻辑的部分。通常模型对象负责在数据库中存取数据。
- View（视图）：处理数据显示的部分。通常视图是依据模型数据创建的。
- Controller（控制器）：处理用户交互的部分。通常控制器负责从视图读取数据，控制用户输入，并向模型发送数据。


#### Java 11 新特性

1. 本地变量类型推断

    类似javascript的变量var，声明时不需要具体的数据类型，编译器也可以推断出变量的类型。

2. 字符串加强

    添加了一系列字符串处理方法，包括判断字符串是否为空白（isBlank），去除首尾空格（strip），去除尾部空格（stripTrailing），去除首部空格（stripLeading），复制字符串（repeat），行数统计（lines）。

3. 集合加强

    为集合（List/Set/Map）都添加了of和copyOf方法，他们两个都用来创建不可变的集合，不能进行添加，删除，替换，排序等操作。of每次创建新的不可变集合，copyOf则先判断是否已经是不可变集合，若已经是不可变集合，返回当前集合，反之调用of返回新的不可变集合。

4. Stream 加强

    1）增加单个参数构造方法，可为null；
    2）增加takeWhile和dropWhile方法；
    3）iterate重载。

5. Optional 加强

    添加一些新方法，可以方便地将一个Optional转换为一个Stream，或者当一个空的Optional时给它一个替代的。

6. InputStream 加强

    增加一个有用的方法transferTo，用于将数据直接传输到OutputStream。

7. HTTP Client API

    新增HTTP Client API，该API支持同步和异步。

8. 编译运行源代码

    可以直接使用java命令来编译并运行Java源代码，而不需要先执行javac。


#### 数据库索引类型及其实现方法

数据库索引，是数据库管理系统中一个排序的数据结构，以协助快速查询、更新数据库表中数据。详细点说就是，数据库系统维护着满足特定查找算法的数据结构，这些数据结构以某种方式引用（指向）数据，这样就可以在这些数据结构上实现高级查找算法。这种数据结构，就是索引。

数据库索引类型：

1. 唯一索引

    唯一索引是不允许其中任何两行具有相同索引值的索引。当现有数据中存在重复的键值时，大多数数据库不允许将新创建的唯一索引与表一起保存。数据库还可能防止添加将在表中创建重复键值的新数据。

2. 主键索引

    数据库表经常有一列或列组合，其值唯一标识表中的每一行。该列称为表的主键。在数据库关系图中为表定义主键将自动创建主键索引，主键索引是唯一索引的特定类型。该索引要求主键中的每个值都唯一。

3. 聚集索引

    在聚集索引中，表中行的物理顺序与键值的逻辑（索引）顺序相同。一个表只能包含一个聚集索引。与非聚集索引相比，聚集索引通常提供更快的数据访问速度。

数据库索引实现方法：

1. FULLTEXT：即为全文索引，目前只有MyISAM引擎支持。其可以在CREATE TABLE ，ALTER TABLE，CREATE INDEX 使用，不过目前只有 CHAR、VARCHAR ，TEXT 列上可以创建全文索引。全文索引并不是和MyISAM一起诞生的，它的出现是为了解决WHERE name LIKE “%word%"这类针对文本的模糊查询效率较低的问题。

2. HASH：由于HASH的唯一（几乎100%的唯一）及类似键值对的形式，很适合作为索引。HASH索引可以一次定位，不需要像树形索引那样逐层查找,因此具有极高的效率。但是，这种高效是有条件的，即只在“=”和“in”条件下高效，对于范围查询、排序及组合索引仍然效率不高。

3. BTREE：BTREE索引就是一种将索引值按一定的算法，存入一个树形的数据结构中（二叉树），每次查询都是从树的入口root开始，依次遍历node，获取leaf。这是MySQL里默认和最常用的索引类型。

4. RTREE：RTREE在MySQL很少使用，仅支持geometry数据类型，支持该类型的存储引擎只有MyISAM、BDb、InnoDb、NDb、Archive几种。
