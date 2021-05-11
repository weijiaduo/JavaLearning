# Vector

## 一、定义

```java
public class Vector<E>
    extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    }
```

定义上和 `ArrayList` 一样，继承同一个父类和实现相同的接口。

## 二、实现原理

实际上，`Vector` 和 `ArrayList` 的实现代码基本一致，底层数据结构都是数组，而且接口方法的实现代码都差不多。

目前来说，也就发现了 `Vector` 和 `ArrayList` 的几点区别。

### 2.1 同步锁

第1点是同步锁的区别，`Vector` 的大部分方法都使用了 `synchronized` 来加锁，用于避免并发访问和修改：

```java
public synchronized E get(int index) {
    if (index >= elementCount)
        throw new ArrayIndexOutOfBoundsException(index);

    return elementData(index);
}
```

而 `ArrayList` 的方法都是没有 `synchronized` 加持的，也就是完全不考虑并发同步的问题。所以一般情况下，`ArrayList` 相比于 `Vector` 在取数存数上的效率会高一些。

### 2.2 迭代器

`ArrayList` 使用的当前最常用的迭代器接口 `Iterator`。

而 `Vector` 则是以前的旧枚举迭代器 `Enumeration`：

```java
public Enumeration<E> elements() {
    return new Enumeration<E>() {
        int count = 0;

        public boolean hasMoreElements() {
            return count < elementCount;
        }

        public E nextElement() {
            synchronized (Vector.this) {
                if (count < elementCount) {
                    return elementData(count++);
                }
            }
            throw new NoSuchElementException("Vector Enumeration");
        }
    };
}
```

也不是说 `Iterator` 就比 `Enumeration` 好。只是 `Iterator` 相比于 `Enumeration`，不仅增加了 `remove` 方法，而且 接口方法名称也更精简。

在使用体验上，`Iterator` 确实会更好用一些。 