# Map

## 一、定义

```java
public interface Map<K,V> {
}
```

`Map` 就是键-值对集合的接口定义。

## 二、方法

### 2.1 基本方法

```java
/**
 * 大小
 */
int size();

/**
 * 是否为空
 */
boolean isEmpty();

/**
 * 包含某个key值
 */
boolean containsKey(Object key);

/**
 * 包含某个value值
 */
boolean containsValue(Object value);

/**
 * 获取值
 */
V get(Object key);

/**
 * 设置值
 */
V put(K key, V value);

/**
 * 删除
 */
V remove(Object key);

/**
 * 添加集合
 */
void putAll(Map<? extends K, ? extends V> m);

/**
 * 清除集合
 */
void clear();
```

### 2.2 `Map` 特有方法：

```java
/**
 * 所有key集合
 */
Set<K> keySet();

/**
 * 所有value集合
 */
Collection<V> values();

/**
 * 所有key-value集合
 */
Set<Map.Entry<K, V>> entrySet();
```

注意 `values` 是返回 `Collection`，而不是 `Set`，这是为了处理值重复的情况。

### 2.3 `Entry` 接口

`Map.Entry` 是 `Map` 内定义的一个接口，用于表示一个 `key-value` 节点。

也就是说，`Map` 实际上就是保存了多个 `Map.Entry` 的集合，只是保证了 `key` 之间不会重复。

```java
interface Entry<K,V> {
    
    K getKey();

    V getValue();

    V setValue(V value);

    boolean equals(Object o);

    int hashCode();
}
```

注意接口内是没有 `setKey()` 方法的，说明不允许修改 `Entry` 的 `key`。 