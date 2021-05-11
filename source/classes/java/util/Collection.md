# java.util.Collection

## 一、定义

```java
public interface Collection<E> extends Iterable<E> {
    ...
}
```

- 所有集合类的基类，是一种泛型类
- 继承 `Iterable` 接口，用于遍历集合所有元素

## 二、方法

### 2.1 基本方法

基本方法主要是 JDK 1.2 以来的集合方法。

```java
/**
 * 返回集合内元素的数量
 * 最大数量为整数的最大值 Integer.MAX_VALUE
 */
int size();

/**
 * 判断集合是否为空
 */
boolean isEmpty();

/**
 * 判断集合中是否包含指定的元素
 * 注意入参是 Object 类型（是由于一开始没有考虑到泛型的原因）
 */
boolean contains(Object o);

/**
 * 返回一个集合的迭代器，用于遍历集合元素
 */
Iterator<E> iterator();

/**
 * 将集合转成数组返回
 * 注意返回的数组是 Object[] 类型（是由于一开始没有考虑到泛型的原因）
 */
Object[] toArray();

/**
 * 将集合转成指定类型的数组返回
 * 注意入参和返回的数组都是 T[] 类型，因此使用这个方法得到的数组，不需要再强制转换类型
 */
<T> T[] toArray(T[] a);

/**
 * 添加元素到集合中
 * 如果添加成功，返回true；否则返回false
 * 添加失败的原因有可能是集合不允许个重复添加，即集合已经包含该对象，或者不允许添加null
 */
boolean add(E e);

/**
 * 从集合中移除指定的元素
 * 注意入参是 Object 类型（是由于一开始没有考虑到泛型的原因）
 */
boolean remove(Object o);

/**
 * 判断集合是否包含另一个集合的所有元素
 * 类似于判断是否是子集的意思
 */
boolean containsAll(Collection<?> c);

/**
 * 将另一个集合的所有元素添加到集合中
 * 类似于并集
 */
boolean addAll(Collection<? extends E> c);

/**
 * 将另一个集合的所有元素从集合中移除
 * 类似于差集
 */
boolean removeAll(Collection<?> c);

/**
 * 保留集合中另一个集合的所有元素
 * 类似于交集
 */
boolean retainAll(Collection<?> c);

/**
 * 清除集合中的所有元素
 */
void clear();
```

### 2.2 新增方法

在 JDK 1.8 版本时，又增加了一些方法：

```java
/**
 * 移除满足指定过滤条件的的元素
 * java.util.function.Predicate 也是 1.8 才加的
 */
default boolean removeIf(Predicate<? super E> filter);

/**
 * 返回一个可分割的迭代器
 * 迭代过程与一般迭代器iterator类似，但是可以对迭代器进行分割，拆分成多个无交集的迭代器
 * 一般用于并行遍历，即多线程遍历同一个集合
 */
default Spliterator<E> spliterator();

/**
 * 返回集合的一个流处理对象
 */
default Stream<E> stream();

/**
 * 返回集合的一个可并行的流处理对象
 * 主要用于多线程的流处理
 */
default Stream<E> parallelStream()
```

流处理对象 `Stream` 的作用类似于遍历集合的所有元素进行连续操作，操作类型可以是过滤、转换、排序、汇总等用操作，并在最后返回连续操作的结果：

```java
int sum = widgets.stream()
                 .filter(w -> w.getColor() == RED)
                 .mapToInt(w -> w.getWeight())
                 .sum();
```