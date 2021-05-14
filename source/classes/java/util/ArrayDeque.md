# ArrayDeque

## 一、定义

```java
public class ArrayDeque<E> extends AbstractCollection<E> 
    implements Deque<E>, Cloneable, Serializable {
    }
```

`ArrayDeque` 实现了 `Deque` 接口，是一个数组实现的双向队列。

## 二、原理

### 2.1 数据结构

`ArrayDeque` 底层数据存储结构是数组：

```java
// 队列存储数组
transient Object[] elements;

// 队列头
transient int head;

// 队列尾
transient int tail;
```

在用数组实现队列时，为充分利用起数组的空间，是采用的环形存放来实现的，例如：

```
 ___ ___ ___ ___ ___ ___ ___ ___
| 5 | 6 |   |   | 1 | 2 | 3 | 4 |
```

就类似这样，数组是循环存放队列数据的，队头 `head` 指向 `1` 的位置，而队尾则指向 `6` 之后的位置（注意不是指向 `6`，而是下一个）。

### 2.2 扩容机制

虽然是数组实现，但是实际上和 `ArrayList` 这种一样，会随着队列元素的增多而自动扩容。

`ArrayDeque` 的底层数组 `elements` 的长度始终是 `2的倍数`，就是说，`elements` 的扩容增长也必须遵守这个规则。

首先，在初始化队列时，如果提供了初始容量，但是这个初试容量不满足 `2的倍数`，那就会对它进行修正：

```java
private void allocateElements(int numElements) {
    int initialCapacity = MIN_INITIAL_CAPACITY;
    if (numElements >= initialCapacity) {
        initialCapacity = numElements;
        initialCapacity |= (initialCapacity >>>  1);
        initialCapacity |= (initialCapacity >>>  2);
        initialCapacity |= (initialCapacity >>>  4);
        initialCapacity |= (initialCapacity >>>  8);
        initialCapacity |= (initialCapacity >>> 16);
        initialCapacity++;

        if (initialCapacity < 0)   // Too many elements, must back off
            initialCapacity >>>= 1;// Good luck allocating 2 ^ 30 elements
    }
    elements = new Object[initialCapacity];
}
```

比如说，在实例化队列时，传入参数 `numElements = 9`，但是因为9不是 `2的倍数`，所以会对它进行修正，这个时候满足条件的最小值是16，因此初始容量实际上是16。

注意一点，如果 `numElements < MIN_INITIAL_CAPACITY`，也就是小于最小容量（实际上就是8）时，就直接取最小容量。

实际扩容时，也要保持数组容量是 `2的倍数`，所以扩容时是直接是 `以2倍增长` 的。

```java
private void doubleCapacity() {
    assert head == tail;
    int p = head;
    int n = elements.length;
    int r = n - p; // number of elements to the right of p
    int newCapacity = n << 1;
    if (newCapacity < 0)
        throw new IllegalStateException("Sorry, deque too big");
    Object[] a = new Object[newCapacity];
    System.arraycopy(elements, p, a, 0, r);
    System.arraycopy(elements, 0, a, r, p);
    elements = a;
    head = 0;
    tail = n;
}
```

也就是说，如果当前容量是16，那么下一次扩容后，容量就应该是32了。

至于为什么数组容量一定要是 `2的倍数`，我估计可能和性能有关：

1） 计算机对于2倍数计算是很快的，直接通过移位操作（`n << 1`）就可以实现；

2）采用数组循环时，经常需要执行取模，如果容量是2的倍数，那么取模就可以用掩码（`head = (head - 1) & (elements.length - 1)`）来实现，相对而言会更高效一些。


### 2.3 集合操作

由于底层是数组，实际上增删查改都很简单。

**1）添加元素**

```java
public void addFirst(E e) {
    if (e == null)
        throw new NullPointerException();
    elements[head = (head - 1) & (elements.length - 1)] = e;
    if (head == tail)
        doubleCapacity();
}

public void addLast(E e) {
    if (e == null)
        throw new NullPointerException();
    elements[tail] = e;
    if ( (tail = (tail + 1) & (elements.length - 1)) == head)
        doubleCapacity();
}
```

由于是双向队列，添加元素可以分为追加队头，或者追加队尾。两种实现代码都差不多，添加元素之后还需要验证是否数组已满，满了的话，就要进行扩容。

**2）删除元素**

删除元素会稍微麻烦一些，毕竟是数组，当删除中间的元素时，需要移动一部分数组元素。

由于数组是循环存放的，所以实际上会分为2种情况：

1、`head < tail` 正常型

```
 ___ ___ ___ ___ ___ ___ ___ ___
|   | 1 | 2 | 3 | 4 | 5 | 6 |   |
```

2、`tail < head` 环绕型

```
 ___ ___ ___ ___ ___ ___ ___ ___
| 5 | 6 |   |   | 1 | 2 | 3 | 4 |
```

根据删除的位置，处理不同的情况就可以了。

```java
private boolean delete(int i) {
    checkInvariants();
    final Object[] elements = this.elements;
    final int mask = elements.length - 1;
    final int h = head;
    final int t = tail;
    final int front = (i - h) & mask;
    final int back  = (t - i) & mask;

    // Invariant: head <= i < tail mod circularity
    if (front >= ((t - h) & mask))
        throw new ConcurrentModificationException();

    // 最少元素移动优化
    if (front < back) {
        // 左边元素少
        if (h <= i) {
            // 正常型
            System.arraycopy(elements, h, elements, h + 1, front);
        } else {
            // 环绕型
            System.arraycopy(elements, 0, elements, 1, i);
            elements[0] = elements[mask];
            System.arraycopy(elements, h, elements, h + 1, mask - h);
        }
        elements[h] = null;
        head = (h + 1) & mask;
        return false;
    } else {
        // 右边元素少
        if (i < t) {
            // 正常型
            System.arraycopy(elements, i + 1, elements, i, back);
            tail = t - 1;
        } else {
            // 环绕型
            System.arraycopy(elements, i + 1, elements, i, mask - i);
            elements[mask] = elements[0];
            System.arraycopy(elements, 1, elements, 0, t);
            tail = (t - 1) & mask;
        }
        return true;
    }
}
```

代码实现虽然有点长，不过原理其实也很简单。

代码实现中，为了减少数组元素的移动，对移动进行了优化，移动时采用最少元素移动原则。

```
final int h = head;
final int t = tail;
final int front = (i - h) & mask;
final int back  = (t - i) & mask;
if (front < back) {
    // 左边元素少
    // 移动左边的元素
} else {
    // 右边元素少
    // 移动右边的元素
}
```

比如，要删除元素 3：

```
 ___ ___ ___ ___ ___ ___ ___ ___
|   | 1 | 2 | 3 | 4 | 5 | 6 |   |
```

这个时候，3 左边的元素数量是 2，右边元素数量是 3，那么在删除 3 之后，就会移动左边的元素：

```
 ___ ___ ___ ___ ___ ___ ___ ___
|   |   | 1 | 2 | 4 | 5 | 6 |   |
```

当然，对于环绕型数组的删除，原理是一样的，只是代码处理上会稍微麻烦一些。

比如，要删除元素 4：

```
 ___ ___ ___ ___ ___ ___ ___ ___
| 5 | 6 |   |   | 1 | 2 | 3 | 4 |
```

这个时候右边元素比较少，所以移动右边的：

```
 ___ ___ ___ ___ ___ ___ ___ ___
| 6 |   |   |   | 1 | 2 | 3 | 5 |
```

反正不管怎么样，删除时总是移动元素少的一边。