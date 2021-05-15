# NavigableSet

## 一、定义

```java
public interface NavigableSet<E> extends SortedSet<E> {
}
```

`NavigableSet` 继承自有序集合接口 `SortedSet`，所以它也是有序的。

## 二、方法

`NavigableSet` 主要是提供了一些近似的方法。

就比如小于、小于等于、大于、大于等于等，这些方法都是返回与查找元素最相近的元素：

```java
/**
 * 返回小于e的最近元素
 */
E lower(E e);

/**
 * 返回小于等于e的最近元素
 */
E floor(E e);

/**
 * 返回大于等于e的最近元素
 */
E ceiling(E e);

/**
 * 返回大于e的最近元素
 */
E higher(E e);
```

其他的方法和 `SortedSet` 差不多：

```java
/**
 * 返回并移除第一个元素
 */
E pollFirst();

/**
 * 返回并移除最后一个元素
 */
E pollLast();

/**
 * 返回倒序集合
 */
NavigableSet<E> descendingSet();

/**
 * 返回倒序迭代器
 */
Iterator<E> descendingIterator();
```

另外还重载和重写了 `SortedSet` 的几个方法接口，重写的几个方法说是为了兼容（我没搞懂这个重写，大家都是接口，重写就能兼容了吗~）：

```java
/**
 * 返回子集合，指定开始元素和结束元素
 */
NavigableSet<E> subSet(E fromElement, boolean fromInclusive,
                           E toElement,   boolean toInclusive);

/**
 * 返回子集合，指定结束元素
 */                          
NavigableSet<E> headSet(E toElement, boolean inclusive);

/**
 * 返回子集合，指定开始元素
 */                          
NavigableSet<E> tailSet(E fromElement, boolean inclusive);

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
```
