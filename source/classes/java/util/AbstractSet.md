# AbstractSet

## 一、定义

```java
public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {
}
```

`AbstractSet` 提供了接口 `Set` 的模板实现，也就是提供了 `Set` 中某些方法的实现，减少实现 `Set` 接口时的代码。

## 二、实现

主要实现了几个方法：

**1）`equals`**

```java
public boolean equals(Object o) {
    if (o == this)
        return true;

    if (!(o instanceof Set))
        return false;
    Collection<?> c = (Collection<?>) o;
    if (c.size() != size())
        return false;
    try {
        return containsAll(c);
    } catch (ClassCastException unused)   {
        return false;
    } catch (NullPointerException unused) {
        return false;
    }
}
```

比较两个集合是是否相等：

1. 是否是自己
2. 是否是Set集合
3. 大小size是否相等
4. 2个集合内的元素是否全等

根据第4条规则，只要集合内的元素全等，就表明两个集合是相等的。

但是有些集合是有序的，也就是说，如果一个有序集合和一个无序集合比较，是有可能相等的，因为第4条规则并不会判断集合内元素的顺序。

当然，如果子类重写了 `equals` 方法，那就另外说了。

**2）`hashCode`**

```java
public int hashCode() {
    int h = 0;
    Iterator<E> i = iterator();
    while (i.hasNext()) {
        E obj = i.next();
        if (obj != null)
            h += obj.hashCode();
    }
    return h;
}
```

集合 `Set` 的 `hashCode` 就等于集合内所有元素的 `hashCode` 的总和。

**3）`removeAll`**

```java
public boolean removeAll(Collection<?> c) {
    Objects.requireNonNull(c);
    boolean modified = false;

    if (size() > c.size()) {
        for (Iterator<?> i = c.iterator(); i.hasNext(); )
            modified |= remove(i.next());
    } else {
        for (Iterator<?> i = iterator(); i.hasNext(); ) {
            if (c.contains(i.next())) {
                i.remove();
                modified = true;
            }
        }
    }
    return modified;
}
```

删除时，遍历集合数量较少的那个：

```java
if (size() > c.size()) {
    // 另一个集合元素数量少
} else {
    // 当前集合元素数量少
}
```
