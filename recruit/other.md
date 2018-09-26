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
