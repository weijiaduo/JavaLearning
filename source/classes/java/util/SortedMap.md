# SortedMap

## 一、定义

```java
public interface SortedMap<K,V> extends Map<K,V> {
}
```

`SortedMap` 提供了 `Map` 的有序形态，也就是集合内部的元素是有序的。

## 二、构造函数

和 `SortedSet` 一样，代码注释里也建议，实现了接口 `SortedMap` 应该提供4中构造函数：

1. 无参默认构造器
2. 带有比较器 `Comparator` 的构造器
3. 带有集合 `Map` 的构造器
4. 带有有序集合 `SortedMap` 的构造器

```java
public class SortedMapImpl implements SortedMap<String, String> {
    public SortedMapImpl() {}
    
    public SortedMapImpl(Comparator<String> comparator) {}
    
    public SortedMapImpl(Map<String, String> map) {}
    
    public SortedMapImpl(SortedMap<String, String> sortedMap) {}
}
```

当然，这几个构造函数不是强制性的，只是建议。

## 三、方法

增加的方法主要用于有序集合：

```java
/**
 * 排序比较器
 */
Comparator<? super K> comparator();

/**
 * 子集合，指定开始元素和结束元素
 */
SortedMap<K,V> subMap(K fromKey, K toKey);

/**
 * 子集合，指定结束元素，从头开始
 */
SortedMap<K,V> headMap(K toKey);

/**
 * 子集合，指定开始元素，到尾结束
 */
SortedMap<K,V> tailMap(K fromKey);

/**
 * 第一个key
 */
K firstKey();

/**
 * 最后一个key
 */
K lastKey();
```