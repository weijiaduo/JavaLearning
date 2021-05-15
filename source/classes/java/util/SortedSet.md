# SortedSet

## 一、定义

```java
public interface SortedSet<E> extends Set<E> {
}
```

`SortedSet` 还是一个接口，定义了 `Set` 的有序形式。

## 二、构造函数

按照代码的备注，在实现接口 `SortedSet` 的子类中，建议应该提供4种不同的构造函数：

1. 无参默认构造器
2. 带有比较器 `Comparator` 的构造器
3. 带有集合 `Collection` 的构造器
4. 带有有序集合 `SortedSet` 的构造器

```java
public class SortedSetImpl implements SortedSet<String> {
    public SortedSetImpl() {}
    
    public SortedSetImpl(Comparator<String> comparator) {}
    
    public SortedSetImpl(Collection<String> collection) {}
    
    public SortedSetImpl(SortedSet<String> sortedSet) {}
}
```

当然，这几个构造函数不是强制性的，只是建议。

## 三、方法

`SortedSet` 为 `Set` 增加了一些有序集合用到的方法接口：

```java
/**
 * 排序比较器
 */
Comparator<? super E> comparator();

/**
 * 子集合，指定开始元素和结束元素
 */
SortedSet<E> subSet(E fromElement, E toElement);

/**
 * 子集合，指定结束元素，从头开始
 */
SortedSet<E> headSet(E toElement);

/**
 * 子集合，指定开始元素，到尾结束
 */
SortedSet<E> tailSet(E fromElement);

/**
 * 第一个元素
 */
E first();

/**
 * 最后一个元素
 */
E last();
```