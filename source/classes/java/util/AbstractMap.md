# AbstractMap

## 一、定义

```java
public abstract class AbstractMap<K,V> implements Map<K,V> {
}
```

`AbstractMap` 就是接口 `Map` 的模板实现类，提供一些已经实现好的方法，后续子类继承时就不用重复写了。

## 二、实现

### 2.1 entrySet

实际上，`AbstractMap` 里面的方法实现都是依赖 `entrySet()` 方法来实现的，具体看看代码吧：

```java
public int size() {
    return entrySet().size();
}

public V get(Object key) {
    Iterator<Entry<K,V>> i = entrySet().iterator();
    if (key==null) {
        while (i.hasNext()) {
            Entry<K,V> e = i.next();
            if (e.getKey()==null)
                return e.getValue();
        }
    } else {
        while (i.hasNext()) {
            Entry<K,V> e = i.next();
            if (key.equals(e.getKey()))
                return e.getValue();
        }
    }
    return null;
}

public V remove(Object key) {
    Iterator<Entry<K,V>> i = entrySet().iterator();
    Entry<K,V> correctEntry = null;
    if (key==null) {
        while (correctEntry==null && i.hasNext()) {
            Entry<K,V> e = i.next();
            if (e.getKey()==null)
                correctEntry = e;
        }
    } else {
        while (correctEntry==null && i.hasNext()) {
            Entry<K,V> e = i.next();
            if (key.equals(e.getKey()))
                correctEntry = e;
        }
    }

    V oldValue = null;
    if (correctEntry !=null) {
        oldValue = correctEntry.getValue();
        i.remove();
    }
    return oldValue;
}

public void clear() {
    entrySet().clear();
}
```

其余实现方法也差不多是这样的，都是通过 `entrySet()` 来实现，这里就不多贴代码了。

所以，子类只要再实现 `entrySet()`，基本上就可以正常使用了。

当然，如果需要其他代码优化之类的，再重写就好。

### 2.2 Entry

在 `AbstractMap` 中，还提供了2个接口 `Entry` 的实现类 `SimpleEntry` 和 `SimpleImmutableEntry`。

一个是可修改的 `SimpleEntry`；

一个是不可修改的 `SimpleImmutableEntry`。

这2个 `Entry` 实现类，代码其实都差不多，下面分别看一下这2个实现类的代码：

```java
public static class SimpleEntry<K,V>
        implements Entry<K,V>, java.io.Serializable {

    private final K key;
    private V value;

    public K getKey() {
        return key;
    }
    
    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry<?,?> e = (Map.Entry<?,?>)o;
        return eq(key, e.getKey()) && eq(value, e.getValue());
    }

    public int hashCode() {
        return (key   == null ? 0 :   key.hashCode()) ^
               (value == null ? 0 : value.hashCode());
    }

}
```

而 `SimpleImmutableEntry` 和 `SimpleEntry` 唯一的区别就是 `setValue` 方法。`SimpleEntry` 可以用 `setValue` 方法，而 `SimpleImmutableEntry` 使用 `setValue` 方法则会抛异常。：

```java
public static class SimpleEntry<K,V>
        implements Entry<K,V>, java.io.Serializable {

    private final K key;
    private V value;

    public K getKey() {
        return key;
    }
    
    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry<?,?> e = (Map.Entry<?,?>)o;
        return eq(key, e.getKey()) && eq(value, e.getValue());
    }

    public int hashCode() {
        return (key   == null ? 0 :   key.hashCode()) ^
               (value == null ? 0 : value.hashCode());
    }

}
```

其中这里面的 `eq` 就是一个内部私有静态方法：

```java
private static boolean eq(Object o1, Object o2) {
    return o1 == null ? o2 == null : o1.equals(o2);
}
```