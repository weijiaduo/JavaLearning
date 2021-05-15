# NavigableMap

## 一、定义

```java
public interface NavigableMap<K,V> extends SortedMap<K,V> {
}
```

和 `NavigableSet` 一样，`NavigableMap` 也是提供获取近似值的方法的接口。

## 二、方法

`NavigableMap` 主要是提供了一些近似的方法。

就比如小于、小于等于、大于、大于等于等，这些方法都是返回与查找元素最相近的元素：

```java
/**
 * 返回小于key的最近Entry
 */
Map.Entry<K,V> lowerEntry(K key);

/**
 * 返回小于key的最近key
 */
K lowerKey(K key);

/**
 * 返回小于等于e的最近Entry
 */
Map.Entry<K,V> floorEntry(K key);

/**
 * 返回小于等于e的最近key
 */
K floorKey(K key);

/**
 * 返回大于等于e的最近Entry
 */
Map.Entry<K,V> ceilingEntry(K key);

/**
 * 返回大于等于e的最近key
 */
K ceilingKey(K key);

/**
 * 返回大于e的最近Entry
 */
Map.Entry<K,V> higherEntry(K key);

/**
 * 返回大于e的最近key
 */
K higherKey(K key);
```

其他则是普通的集合方法：

```java
/**
 * 返回第一个元素
 */
Map.Entry<K,V> firstEntry();

/**
 * 返回最后一个元素
 */
Map.Entry<K,V> lastEntry();

/**
 * 返回并移除第一个元素
 */
Map.Entry<K,V> pollFirstEntry();

/**
 * 返回并移除最后一个元素
 */
Map.Entry<K,V> pollLastEntry();

/**
 * 返回倒序集合
 */
NavigableMap<K,V> descendingMap();

/**
 * 返回key有序集合
 */
NavigableSet<K> navigableKeySet();

/**
 * 返回key倒序集合
 */
NavigableSet<K> descendingKeySet();
```

重载和重写的方法：

```java
/**
 * 返回子集合，指定开始元素和结束元素
 */
NavigableMap<K,V> subMap(K fromKey, boolean fromInclusive,
                             K toKey,   boolean toInclusive);

/**
 * 返回子集合，指定结束元素
 */                          
NavigableMap<K,V> headMap(K toKey, boolean inclusive);

/**
 * 返回子集合，指定开始元素
 */                          
NavigableMap<K,V> tailMap(K fromKey, boolean inclusive);

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
```