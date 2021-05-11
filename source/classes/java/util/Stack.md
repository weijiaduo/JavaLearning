# Stack

## 一、定义

```java
public class Stack<E> extends Vector<E> {
}
```

`Stack` 继承自 `Vector` 类，因此拥有 `Vector` 的所有特性。

## 二、原理

`Stack` 除了增加几个栈特用的方法以外，其他的和 `Vector` 都一样，而增加的这几个方法，内部实际上也是调用的 `Vector` 的方法。

```java
public E push(E item) {
    addElement(item);
    return item;
}

public synchronized E pop() {
    E obj;
    int len = size();
    obj = peek();
    removeElementAt(len - 1);
    return obj;
}

public synchronized E peek() {
    int len = size();
    if (len == 0)
        throw new EmptyStackException();
    return elementAt(len - 1);
}
```

和 `Vector` 的保持一致，`Stack` 添加的方法也加上了同步锁 `synchronized`，避免并发问题。

不过 `push` 方法并没有加上同步锁，这个倒是有点出乎意料，或许是因为它内部调用的方法 `addElement` 已经加锁的原因吧。