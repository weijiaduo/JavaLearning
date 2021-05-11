# LinkedList

## 一、定义

```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
{
}
```

`LinkedList` 和 `ArrayList` 的定义有一些区别，`LinkedList` 是继承于 `AbstractSequentialList` 的，而 `ArrayList` 是直接继承于 `AbstractList`。

`LinkedList` 的继承链多了一层 `AbstractSequentialList`。

另外，`LinkedList` 还实现了另外一个双向队列的接口 `Deque`，表示 `LinkedList` 能够双向访问元素。

## 二、原理

### 2.1 数据结构

底层的数据存储是采用双向链表来实现的，至于链表节点类型，是一个静态内部类 `Node`，有2个引用分别指向前一个和后一个节点：

```java
private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

### 2.2 集合操作

`LinkedList` 的集合操作不外乎增删查改，底层数据结构是链表，实际上这些操作就是链表的操作。

首先看一下查询，既然是链表，那么就有链头和链尾，在 `LinkedList` 内分别用2个成员变量表示，获取表头和表尾还是比较简单的：

```java
public E getFirst() {
    final Node<E> f = first;
    if (f == null)
        throw new NoSuchElementException();
    return f.item;
}

public E getLast() {
    final Node<E> l = last;
    if (l == null)
        throw new NoSuchElementException();
    return l.item;
}
```

至于查找链表中间的节点，没有别的办法，必须需要遍历链表才行：

```java
public E get(int index) {
    checkElementIndex(index);
    return node(index).item;
}

Node<E> node(int index) {
    // assert isElementIndex(index);

    if (index < (size >> 1)) {
        // 链表前半段
        Node<E> x = first;
        for (int i = 0; i < index; i++)
            x = x.next;
        return x;
    } else {
        // 链表后半段
        Node<E> x = last;
        for (int i = size - 1; i > index; i--)
            x = x.prev;
        return x;
    }
}
```

不过在查找时进行了一些优化，因为 `LinkedList` 是一个双向链表，所以可以从两边任意一边开始搜索。

搜索指定索引的元素时，如果索引小于长度的一半，就从表头正向查找；如果所以大于长度的一半，就从表尾逆向查找。

接下来时添加和删除，其实都是链表的基本操作：

```java
public void add(int index, E element) {
    checkPositionIndex(index);

    if (index == size)
        linkLast(element);
    else
        linkBefore(element, node(index));
}

void linkLast(E e) {
    final Node<E> l = last;
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;
    else
        l.next = newNode;
    size++;
    modCount++;
}

void linkBefore(E e, Node<E> succ) {
    // assert succ != null;
    final Node<E> pred = succ.prev;
    final Node<E> newNode = new Node<>(pred, e, succ);

    // 建立后链
    succ.prev = newNode;
    // 建立前链
    if (pred == null)
        first = newNode;
    else
        pred.next = newNode;

    size++;
    modCount++;
}
```

添加元素也没什么特别的，一般就是追加和插入指定位置。追加的话，直接链接到表尾就行；至于插入，则先需要需要前面的搜索，找到插入的位置，再执行插入，会多耗费一些。所以专门针对这2种情况分别进行了处理。

```java
public boolean remove(Object o) {
    if (o == null) {
        for (Node<E> x = first; x != null; x = x.next) {
            if (x.item == null) {
                unlink(x);
                return true;
            }
        }
    } else {
        for (Node<E> x = first; x != null; x = x.next) {
            if (o.equals(x.item)) {
                unlink(x);
                return true;
            }
        }
    }
    return false;
}

E unlink(Node<E> x) {
    // assert x != null;
    final E element = x.item;
    final Node<E> next = x.next;
    final Node<E> prev = x.prev;
    
    // 解开前链
    if (prev == null) {
        first = next;
    } else {
        prev.next = next;
        x.prev = null;
    }
    
    // 解开后链
    if (next == null) {
        last = prev;
    } else {
        next.prev = prev;
        x.next = null;
    }

    x.item = null;
    size--;
    modCount++;
    return element;
}
```

删除元素也一样，添加元素是插入时建立节点链接，删除实际就是解除某个节点的链接。所以操作上都差不多。


最后是更新，这个更简单了,先查找到需要更新的元素，然后直接就可以更新：

```java
public E set(int index, E element) {
    checkElementIndex(index);
    Node<E> x = node(index);
    E oldVal = x.item;
    x.item = element;
    return oldVal;
}
```

总的来说，增删查改这几个操作，也就查找有点看头，其他的都是寻常的链表操作，只要仔细点，基本都可以写出来。

### 2.3 迭代器

`LinkedList` 是一个双向链表，所以它的迭代器也一样，可以双向遍历，然后我就发现了一个有意思的地方。

```java
private class ListItr implements ListIterator<E> {
    private Node<E> lastReturned;
    private Node<E> next;
    private int nextIndex;
    private int expectedModCount = modCount;

    ListItr(int index) {
        // assert isPositionIndex(index);
        next = (index == size) ? null : node(index);
        nextIndex = index;
    }

    public E next() {
        checkForComodification();
        if (!hasNext())
            throw new NoSuchElementException();

        lastReturned = next;
        next = next.next;
        nextIndex++;
        return lastReturned.item;
    }

    public E previous() {
        checkForComodification();
        if (!hasPrevious())
            throw new NoSuchElementException();

        lastReturned = next = (next == null) ? last : next.prev;
        nextIndex--;
        return lastReturned.item;
    }

    public void remove() {
        checkForComodification();
        if (lastReturned == null)
            throw new IllegalStateException();

        Node<E> lastNext = lastReturned.next;
        unlink(lastReturned);
        if (next == lastReturned)
            next = lastNext;
        else
            nextIndex--;
        lastReturned = null;
        expectedModCount++;
    }
}
```

有意思的是迭代器中的2个成员变量 `next` 和 `lastReturned`。

在 `next()` 方法中可以看到，一般情况下， `next` 和 `lastReturned` 是指向不同的元素的，`next` 正常是 `lastReturned` 的下一个元素：

```java
lastReturned = next;
next = next.next;
```

但是如果执行过 `previous()` 方法，它们的指向就发生变化了：

```java
lastReturned = next = (next == null) ? last : next.prev;
```

这个时候，`next` 和 `lastReturned` 都指向同一个元素，也就是当前 `next` 的前一个元素。

由于 `next()` 和 `previous()` 的不同情况，其他方法在处理时，也需要考虑这两种情况，就比如上面的 `remove()` 方法：

```java
if (next == lastReturned)
    // previous()的情况
    next = lastNext;
else
    // next()的情况
    nextIndex--;
```

一开始看到这里，貌似是有点别扭的，因为感觉代码写起来有点不太统一。

后来仔细相想想，可能是因为只用了 `next` 变量来遍历的原因，如果再加上一个 `previous` 变量可能会更好理解一些：

```java
private class ListItr implements ListIterator<E> {

    public E next() {
        checkForComodification();
        if (!hasNext())
            throw new NoSuchElementException();
            
        // 添加previous指向前一个元素
        lastReturned = previous = next;
        next = next.next;

        nextIndex++;
        return lastReturned.item;
    }

    public E previous() {
        checkForComodification();
        if (!hasPrevious())
            throw new NoSuchElementException();
            
        // 添加previous指向前一个元素
        lastReturned = next = previous;
        previous = previous.prev;
        
        nextIndex--;
        return lastReturned.item;
    }
}
```

这样看起来，貌似好理解一些，不够毕竟多了一个变量 `previous`，维护起来可能麻烦一些吧？所以具体实现并没有添加。

由于 `LinkedList` 是双向的，所以也还有另一个迭代器，逆向迭代器：

```java
private class DescendingIterator implements Iterator<E> {
    private final ListItr itr = new ListItr(size());
    public boolean hasNext() {
        return itr.hasPrevious();
    }
    public E next() {
        return itr.previous();
    }
    public void remove() {
        itr.remove();
    }
}
```

不过在实现上，实际是直接用的正向迭代器，在它外面包装了一层，真正遍历时就调用正向迭代器中相反的接口。
