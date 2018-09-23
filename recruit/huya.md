## 虎牙直播

#### Java 类型


#### ArrayList, LinkedList, Vector 区别

- Arraylist和Vector是采用数组方式存储数据，此数组元素数大于实际存储的数据以便增加插入元素，都允许直接序号索引元素，但是插入数据要涉及到数组元素移动等内存操作，所以插入数据慢，查找有下标，所以查询数据快。
- Vector的实现中由于使用了synchronized来保证线程安全，所以性能上比ArrayList要差。
- LinkedList使用双向链表实现存储，按序号索引数据需要进行向前或向后遍历，但是插入数据时只需要记录本项前后项即可，插入数据较快。


#### HashMap, Hashtable, ConcurrentHashMap 区别以及内部实现原理

HashMap：
- JDK1.7版本是底层数组+链表，JDK1.8版本是底层数组+链表+红黑树，可以存储null键和null值；
- 线程不安全。

Hashtable：
- 底层数组+链表实现，无论key还是value都不能为null；
- 线程安全，实现线程安全的方式是在修改数据时锁住整个Hashtable，效率低。

ConcurrentHashMap：
- JDK1.7版本的数据结构是Segment+HashEntry，并发控制使用的是ReentrantLock；JDK1.8版本的数据结构是Node数组+链表+红黑树的数据结构来实现，并发控制使用的是Synchronized和CAS；
- Segment是一种可重入的锁ReentrantLock，每个Segment守护一个HashEntry数组里的元素，当对HashEntry数组的数据进行修改时，必须首先获得对应的Segment锁。


#### HashMap 在 1.7 和 1.8 的实现

- JDK7之前hashmap又叫散列链表：基于一个数组以及多个链表的实现，hash值冲突的时候，就将对应节点以链表的形式存储。
- JDK8中，当同一个hash值（Table上元素）的链表节点数不小于8时，将不再以单链表的形式存储了，会被调整成一颗红黑树。这就是JDK7与JDK8中HashMap实现的最大区别。


#### Linux 中的IO类型

- 阻塞IO（blocking IO）
- 非阻塞IO（nonblocking IO）
- IO复用（IO multiplexing）
- 信号驱动IO（signal driven IO）
- 异步IO（asynchronous IO）


#### nio 包在 1.8 有什么改变


#### 进程和线程的区别

1. 进程是操作系统资源分配的基本单位，而线程是任务调度和执行的基本单位。
2. 一个程序至少有一个进程，而线程是进程的一个实体，一个进程至少有一个线程。
3. 线程的划分尺度小于进程，线程上下文切换的开销小，多线程程序的并发性更高。
4. 进程有独立的地址空间，线程没有单独的地址空间（同一进程内的线程共享进程的地址空间）。
5. 多进程环境中，任何一个进程的终止，不会影响到其他进程。而多线程环境中，父线程终止，全部子线程被迫终止(没有了资源)。而任何一个子线程终止一般不会影响其他线程，除非子线程执行了exit()系统调用。任何一个子线程执行exit()，全部线程同时灭亡。


#### 进程间通信（IPC，InterProcess Communication）和线程间通信

进程间的通信方式有以下8种：
- 共享内存
- 消息队列
- 信号量
- 有名管道
- 无名管道
- 信号
- 文件
- socket

线程间的通信方式不仅可沿用上述进程间的通信方式，而且还有自己独特的6种：
- 互斥量
- 自旋锁
- 条件变量
- 读写锁
- 线程信号
- 全局变量


#### 线程的实现方式

1. 继承Thread类，重写run方法
2. 实现Runnable接口，实现Runnable接口的实现类的实例对象作为Thread构造函数的target
3. 使用定时器
4. 通过Callable和FutureTask创建线程
5. 通过线程池创建线程


#### 线程池的定义以及类型

Java通过Executors提供四种线程池，分别为：

- newCachedThreadPool：创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
- newFixedThreadPool：创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
- newScheduledThreadPool：创建一个定长线程池，支持定时及周期性任务执行。
- newSingleThreadExecutor：创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。


使用线程池的好处:

1. 减少在创建和销毁线程上所花的时间以及系统资源的开销；
2. 如不使用线程池，有可能造成系统创建大量线程而导致消耗完系统内存以及”过度切换”。


#### Spring Ioc 和 AOP 的定义以及实现原理


#### SpringMVC 中前端访问后台时的基本流程

1. 用户请求
2. DispatcherServlet
3. 处理器映射（HandlerMapping）：由Handler与其拦截器共同组成，用到IoC
4. 处理器适配器（HandlerAdapter）：通过上下文环境找到适配器，运行Handler与其拦截器
5. 模型与视图（ModelAndView）
6. 视图解析器（ViewResolver）：用到IoC
7. 视图（View）
8. 返回结果


#### 收到的数据是JSON时使用什么注解

handler method 参数绑定常用的注解，根据他们处理的Request的内容分为四类：

1. 处理requet uri部分（这里指uri template中variable，不含queryString部分）的注解： @PathVariable；
2. 处理request header部分的注解： @RequestHeader，@CookieValue；
3. 处理request body部分的注解： @RequestParam，@RequestBody；
4. 处理attribute类型是注解： @SessionAttributes，@ModelAttribute。

下面分别介绍各个注解作用：

1. @PathVariable：当使用@RequestMapping URI template 样式映射时，即 someUrl/{paramId}, 这时的paramId可通过 @Pathvariable注解绑定它传过来的值到方法的参数上；
2. @RequestHeader：把Request请求header部分的值绑定到方法的参数上；
3. @CookieValue：把Request header中关于cookie的值绑定到方法的参数上；
4. @RequestParam：一般用来处理Content-Type为 application/x-www-form-urlencoded编码的内容，提交方式GET、POST；
5. @RequestBody：该注解常用来处理Content-Type不是 application/x-www-form-urlencoded编码的内容，例如application/json， application/xml等；它是通过使用HandlerAdapter配置的HttpMessageConverters来解析post data body，然后绑定到相应的bean上的。
6. @SessionAttributes：用来绑定HttpSession中的attribute对象的值，便于在方法中的参数里使用；
7. @ModelAttribute：该注解有两个用法，一个是用于方法上，一个是用于参数上。用于方法上时，通常用来在处理@RequestMapping之前，为请求绑定需要从后台查询的model；用于参数上时，用来通过名称对应，把相应名称的值绑定到注解的参数bean上。

#### Mysql 数据库的基本命令，登录，查找表，查找数据


#### Mysql SQL语句优化方法


#### Mysql 引擎比较

1. InnoDB：支持事务处理，支持外键，支持崩溃修复能力和并发控制。如果需要对事务的完整性要求比较高（比如银行），要求实现并发控制（比如售票），那选择InnoDB有很大的优势。如果需要频繁的更新、删除操作的数据库，也可以选择InnoDB，因为支持事务的提交（commit）和回滚（rollback）。 
2. MyISAM：不支持事务处理，不支持外键。插入数据快，空间和内存使用比较低。如果表主要是用于插入新记录和读出记录，那么选择MyISAM能实现处理高效率。如果应用的完整性、并发性要求比较低，也可以使用。
3. MEMORY：所有的数据都在内存中，数据的处理速度快，但是安全性不高。如果需要很快的读写速度，对数据的安全性要求较低，可以选择MEMOEY。它对表的大小有要求，不能建立太大的表。所以，这类数据库只使用在相对较小的数据库表。


#### 系统架构

需要考虑：
- 分布式
- 高可用性
- 高并发
