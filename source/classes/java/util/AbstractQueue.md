# AbstractQueue

## 一、定义

```java
public abstract class AbstractQueue<E>
    extends AbstractCollection<E>
    implements Queue<E> {
        
    }
```

`AbstractQueue` 只是一个抽取类，其目的主要是为接口 `Queue` 的一些方法提供了基础的实现模板。

## 二、实现

`AbstractQueue` 只是一个模板类，可以为子类在实现接口 `Queue` 时提供一些通用代码，减少子类实现时的代码量。

比如：

```java
public boolean add(E e) {
    if (offer(e))
        return true;
    else
        throw new IllegalStateException("Queue full");
}

public E remove() {
    E x = poll();
    if (x != null)
        return x;
    else
        throw new NoSuchElementException();
}
```

类似这种常用方法，`AbstractQueue` 直接就实现了，避免子类每次都要自己实现的麻烦。

从代码上来说，`AbstractQueue` 没有什么特殊的代码，都是一些通用的基础代码。

总之，`AbstractQueue` 就是提供了一个简易实现模板，减少子类在实现接口 `Queue` 时的代码，提高效率。