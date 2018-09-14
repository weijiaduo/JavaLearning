## Java 基础知识

#### Java 中的集合分类

Map 接口和 Collection 接口是所有集合框架的父接口。

1. Collection 接口的子接口包括：Set 和 List。
2. Map 接口的子接口包括：HashMap，TreeMap，Hashtable，ConcurrentHashMap 以及 Properties 等；
3. Set 实现类包括：HashSet，TreeSet，LinkedHashSet等；
4. List 实现类包括：Arrayist，LinkedList，Stack，Vector等；


#### HashMap 和 Hashtable 的区别

1. HashMap 是继承自 AbstractMap 类，而 Hashtable 是继承自 Dictionary 类;
2. Hashtable 是线程安全的，它的每个方法中都加入了 Synchronize 方法。HashMap 不是线程安全的，在多线程并发的环境下，可能会产生死锁等问题；
3. HashMap 的 Iterator 是 fail-fast 迭代器。JDK8之前的版本中，Hashtable 是没有 fast-fail 机制的。在 JDK8 及以后的版本中 ，Hashtable 也是使用 fast-fail 的；
4. HashMap 允许 null 值作为 key，Hashtable 不允许 null 作为 key;


#### HashMap 底层实现

在 JDK8 之前，底层实现是数组+链表实现；JDK8 之后使用了数组+链表+红黑树实现。


#### ConcurrentHashMap 概念

ConcurrentHashMap 结合了 HashMap 和 Hashtable 两者的优势。HashMap 没有线程同步，Hashtable 虽然有线程同步，但是同步时会把整个 hash 表结构给锁住。而 ConcurrentHashMap 的锁机制是细粒度的，它只会锁住一部分表数据。例如 ConcurrentHashMap 将 hash 表分为 16 个桶（默认值），诸如 get，put，remove 等常用操作只锁当前需要用到的桶。ConcurrentHashMap 的实现原理：

- ConcurrentHashMap 类中包含两个内部静态类 HashEntry 和 Segment；前者用来保存键值对数据，后者用来充当锁的角色；
- Segment 是一种可重入的锁 ReentrantLock，每个 Segment 守护一个 HashEntry 数组里的元素，当对 HashEntry 数组进行修改时，必须先获得 Segment 锁。


#### List，Set 和 Map 的初始容量和加载因子

1）List
- Arrayist 的初始容量是10，加载因子是0.5。每次扩容为原容量的0.5倍+1；
- Vector 初始容量为10，加载因子是1，每次扩容为原容量的1倍。

2）Set
- HashSet 初始容量为16，加载因子为0.75，每次扩容为原容量的1倍。

3）Map
- HashMap 初始容量16，加载因子0.75，每次扩容为原容量的1倍。
