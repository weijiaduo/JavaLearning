# Deque

`Deque` 是 “double ended queue” 的缩写，也就是双向队列。

## 一、定义

```java
public interface Deque<E> extends Queue<E> {
    ...
}
```

## 二、方法

`Deque` 继承自 `Queue`，因此也具有队列的所有方法。另外，双向队列还具有一些自己独有的方法，可以分为几类：

- 双向队列方法
- 栈方法
- 集合方法

### 2.1 双向队列方法

由于 `Deque` 是双向的，因此它的方法都包含了两边的操作，也就是队列相关的操作方法是 `Queue` 的两倍。

#### 2.1.1 添加元素

添加元素的方法有4个（分别包括了在队列头和队列尾添加元素）：

```java
// 添加到队列头
void addFirst(E e);

// 添加到队列尾
void addLast(E e);

// 添加到队列头
boolean offerFirst(E e);

// 添加到队列尾
boolean offerLast(E e);
```

这些方法的区别和 `Queue` 的类似：

- `addFirst()`、`addLast()` 在队列已满的情况下调用，会抛出异常
- `offerFirst()`、`offerLast()` 在队列已满的情况下调用，会添加失败，返回 `false`


#### 2.1.2 移除元素

移除元素的方法也有4个：

```java
// 移除队列头
E removeFirst();

// 移除队列尾
E removeLast();

// 移除队列头
E pollFirst();

// 移除队列尾
E pollLast();
```

一样地，它们之间的区别只是返回值的不同：

- `removeFirst()`、`removeLast()` 在队列为空的情况下调用，会抛出异常
- `pollFirst()`、`pollLast()` 在队列为空的情况下调用，不会抛出异常，而是返回 `null`

另外，还有2个移除指定元素的方法（其实和普通的集合删除方法没啥区别）：

```java
// 移除队列中第一次出现的指定元素
boolean removeFirstOccurrence(Object o);

// 移除队列中最后一次出现的指定元素
boolean removeLastOccurrence(Object o);
```

#### 2.1.3 获取元素

同样地，获取元素也有4个方法：

```java
// 获取队列头
E getFirst();

// 获取队列尾
E getLast();

// 获取队列头
E peekFirst();

// 获取队列尾
E peekLast();
```

同样也是，它们之间的区别就是返回值不一样：

- `getFirst()`、`getLast()` 在队列为空的情况下调用，会抛出异常
- `peekFirst()`、`peekLast()` 在队列为空的情况下调用，不会抛异常，而是返回 `null`

### 2.2 栈方法

由于 `Deque` 是双向队列，因此也可以用作栈 `Stack` 来使用，而且 `Deque` 也提供了相关的方法：

```java
// 入栈
void push(E e);

// 出栈
E pop();
```

- `push()` 方法等价于 `addFirst()` 方法，栈满时会抛出异常
- `pop()` 方法等价于 `removeFirst()` 方法，栈空时会抛出异常

### 2.3 集合方法

集合方法中很多都是比较普通的方法（和一般集合方法差不多），不过还是增加了一个新的方法：

```java
// 逆向迭代器
Iterator<E> descendingIterator();
```

由于 `Deque` 是双向队列，因此除了有一般集合的正向迭代器 `iterator()` 以外，还能够拥有逆向的迭代器。