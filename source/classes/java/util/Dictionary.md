# Dictionary

## 一、定义

```java
public abstract class Dictionary<K,V> {
}
```

`Dictionary` 实际和 `Map` 一样，是用于键-值对的集合。

但是 `Dictionary` 实际上已经过时了，属于旧的接口，现在基本上都是使用 `Map` 接口。

## 二、方法

和 `Map` 接口差不多，都是一些操作 `key-value` 的方法：

```java
/**
 * 集合大小
 */
abstract public int size();

/**
 * 集合是否为空
 */
abstract public boolean isEmpty();

/**
 * key迭代器
 */
abstract public Enumeration<K> keys();

/**
 * value迭代器
 */
abstract public Enumeration<V> elements();

/**
 * 获取值
 */
abstract public V get(Object key);

/**
 * 设值
 */
abstract public V put(K key, V value);

/**
 * 删除
 */
abstract public V remove(Object key);
```

总体上和 `Map` 差不多，但是有些过时了，比如迭代遍历 `Enumeration`，现在基本上也都用 `Iterator` 接口了。

`Dictionary` 现在基本没人用了，只是以前的旧代码没办法去除，所以保留了下来，算是旧的接口吧。

现在已经用 `Map` 来替代了。