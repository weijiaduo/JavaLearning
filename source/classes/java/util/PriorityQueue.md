# PriorityQueue

## 一、定义

```java
public class PriorityQueue<E> extends AbstractQueue<E>
    implements java.io.Serializable {
    
    public PriorityQueue(int initialCapacity,
                         Comparator<? super E> comparator) {
        // Note: This restriction of at least one is not actually needed,
        // but continues for 1.5 compatibility
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.queue = new Object[initialCapacity];
        this.comparator = comparator;
    }
}
```

从名称就可以看出来，这是个优先队列，也就是按照从小到大（或从大到小）排序的队列。

继承的类就是队列 `Queue` 的模板实现类 `AbstractQueue`。

## 二、实现

### 2.1 数据结构

既然是有顺序的队列，那么它的底层数据结构是什么样的呢？

其实还是数组~~

```java
transient Object[] queue; // non-private to simplify nested class access

private int size = 0;
```

学过数据结构的话，应该知道，数组也可以用于表示树结构。就比如数组：

```
[1, 2, 3, 4, 5, 6, 7, 8, 9]
```

就可以表示下面的完全二叉树：

```
           1
         /   \
        2     3
       / \   / \
      4   5 6   7
     / \
    8   9
```

假如当前的数组索引是 `k`，那么它的父节点索引就是 `(k - 1) / 2`，2个子节点索引分别是 `(k * 2) + 1` 和 `(k * 2) + 2`。

用例子来说，假如当前索引是 1，那么父节点索引就是 `(1 - 1) / 2 = 0`，子节点索引是 `(1 * 2) + 1 = 3` 和 `(1 * 2) + 2 = 4`。

完全二叉树在数据结构中，常用于表示堆（当然，完全多叉树也可以）。而且堆也是可以有序的，一般可以分为最大值堆和最小值堆：

1. 最小值堆，父节点都不比子节点的值大；
2. 最大值堆，父节点都不比子节点的值小。

例如上面的例子 `[1, 2, 3, 4, 5, 6, 7, 8, 9]`，就是最小值堆。

优先队列 `PriorityQueue` 实际上也是用有序堆来实现的。

不过具体是最小值堆，还是最大值堆，就得看优先队列的比较器函数是什么了。

### 2.2 扩容机制

底层结构是数组，那也就是和 `ArrayList` 一样，当元素增多时，就需要进行扩容。

优先队列 `PriorityQueue` 的扩容机制，基本和 `ArrayList` 差不多，只有些许区别：

```java
private void grow(int minCapacity) {
    int oldCapacity = queue.length;
    // Double size if small; else grow by 50%
    int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                                     (oldCapacity + 2) :
                                     (oldCapacity >> 1));
    // overflow-conscious code
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    queue = Arrays.copyOf(queue, newCapacity);
}
```

优先队列 `PriorityQueue` 对容量比较小的情况进行了特别处理，其他则和 `ArrayList` 一样：

1. 当队列容量还比较小（< 64）时，就直接扩容 1 倍（oldCapacity + 2）；
2. 否则就扩容 0.5 倍（oldCapacity >> 1）。

举2个列子说明：

当 `queue.length` 是 `9` 时，因为 `9 < 64`，所以扩容 1 倍，新容量就是 `9 + (9 + 2) = 20`。

当 `queue.length` 是 `70` 时，因为 `70 > 64`，所以扩容 0.5 倍，新容量就是 `70 + (70 / 2) = 105`。


### 2.3 堆操作

从数组变成堆的实现，和数据结构里的方法差不多。

堆的构造，一般是从数组的最后一个非叶子节点开始，到根节点为止，一直执行下沉操作，就能够完成：

```java
private void heapify() {
    for (int i = (size >>> 1) - 1; i >= 0; i--)
        siftDown(i, (E) queue[i]);
}
```

简单说一下下沉函数 `siftDown` 的实现吧：

```java
private void siftDown(int k, E x) {
    if (comparator != null)
        siftDownUsingComparator(k, x);
    else
        siftDownComparable(k, x);
}

@SuppressWarnings("unchecked")
private void siftDownComparable(int k, E x) {
    Comparable<? super E> key = (Comparable<? super E>)x;
    int half = size >>> 1;        // loop while a non-leaf
    while (k < half) {
        int child = (k << 1) + 1; // assume left child is least
        Object c = queue[child];
        int right = child + 1;
        if (right < size &&
            ((Comparable<? super E>) c).compareTo((E) queue[right]) > 0)
            c = queue[child = right];
        if (key.compareTo((E) c) <= 0)
            break;
        queue[k] = c;
        k = child;
    }
    queue[k] = key;
}

@SuppressWarnings("unchecked")
private void siftDownUsingComparator(int k, E x) {
    int half = size >>> 1;
    while (k < half) {
        int child = (k << 1) + 1;
        Object c = queue[child];
        int right = child + 1;
        if (right < size &&
            comparator.compare((E) c, (E) queue[right]) > 0)
            c = queue[child = right];
        if (comparator.compare(x, (E) c) <= 0)
            break;
        queue[k] = c;
        k = child;
    }
    queue[k] = x;
}
```

下沉 `siftDown` 可以分为2种情况，一种是有自定义的比较器，一种是用默认的比较。虽然分为2种情况，但是代码上基本相差无几。

下沉 `siftDown` 原理很简单，就是一直往下替换当前节点，直到成为叶子节点，或子节点都比节点小（或大）时就结束。

比如，当前需要下沉的节点是 9：

```
           1
         /   \
        9     2
       / \   / \
      3   4 5   6
     / \
    7   8

           1
         /   \
        3     2
       / \   / \
      9   4 5   6       下沉，交换 9 <==> 3
     / \
    7   8

           1
         /   \
        3     2
       / \   / \
      7   4 5   6       下沉，交换 9 <==> 7
     / \
    9   8
```

至于上浮 `siftUp` 和下沉的 `siftDown` 代码差不多：

```java
private void siftUp(int k, E x) {
    if (comparator != null)
        siftUpUsingComparator(k, x);
    else
        siftUpComparable(k, x);
}
```

只是逻辑反过来而已，比如需要上浮的节点是 2：

```
           1
         /   \
        3     4
       / \   / \
      5   6 7   8
     / \
    9   2

           1
         /   \
        3     4
       / \   / \
      2   6 7   8       上浮，交换 2 <==> 5
     / \
    9   5

           1
         /   \
        2     4
       / \   / \
      3   6 7   8       上浮，交换 2 <==> 3
     / \
    9   5
```

### 2.4 集合操作

优先队列的增删查改稍微麻烦一些，因为它是一个有序的堆，因此在增删元素时，会引起堆结构发生变化。

**1）查询**

查找没什么好说的，因为底层结构是数组，所以直接遍历数组查询就行：

```java
public E peek() {
    return (size == 0) ? null : (E) queue[0];
}

public boolean contains(Object o) {
    return indexOf(o) != -1;
}

private int indexOf(Object o) {
    if (o != null) {
        for (int i = 0; i < size; i++)
            if (o.equals(queue[i]))
                return i;
    }
    return -1;
}
```

**2）添加**

不过，添加元素时，总是先添加到队列数组的末尾，再利用上浮 `siftUp` 来调整堆结构，算是有点小麻烦：

```java
public boolean add(E e) {
    return offer(e);
}

public boolean offer(E e) {
    if (e == null)
        throw new NullPointerException();
    modCount++;
    int i = size;
    if (i >= queue.length)
        grow(i + 1);
    size = i + 1;
    if (i == 0)
        queue[0] = e;
    else
        siftUp(i, e);
    return true;
}
```

**3）删除**

而删除则是更麻烦一些，删除时，会把删除元素和数组末尾的元素交换，再执行下沉 `siftDown` 或上浮 `siftUp` 操作，也就是说，删除时是不确定是要下沉，还是上浮的。而添加是明确的只会上浮（因为添加总是在末尾）：

```java
public E poll() {
    if (size == 0)
        return null;
    int s = --size;
    modCount++;
    E result = (E) queue[0];
    E x = (E) queue[s];
    queue[s] = null;
    if (s != 0)
        siftDown(0, x);
    return result;
}

private E  `removeAt` (int i) {
    // assert i >= 0 && i < size;
    modCount++;
    int s = --size;
    if (s == i) // removed last element
        queue[i] = null;
    else {
        E moved = (E) queue[s];
        queue[s] = null;
        siftDown(i, moved);
        if (queue[i] == moved) {
            siftUp(i, moved);
            if (queue[i] != moved)
                return moved;
        }
    }
    return null;
}
```

删除元素的话，也可以分为几种情况：

1. 删除队列头，移除后，这个只要执行下沉操作；
2. 删除末尾元素，直接移除，不需要额外的操作；
3. 删除中间的元素，可能下沉，也可能上浮。

删除比添加会稍微复杂一点，不过也还行，毕竟都是堆的正常操作。

举2个栗子简单说明一下什么时候需要下沉，什么时候需要上浮：

栗1，删除节点 2，这种情况就会下沉：

```
           1
         /   \
        2     4
       / \   / \
      3   6 7   8       需要删除2
     / \
    9   5
    
           1
         /   \
        5     4
       / \   / \
      3   6 7   8       和末尾元素交换，2 <==> 5
     / \
    9   2
    
           1
         /   \
        5     4
       / \   / \
      3   6 7   8       删除末尾元素
     /
    9

           1
         /   \
        3     4
       / \   / \
      5   6 7   8       下沉，交换 5 <==> 3
     /
    9
```

栗2，删除节点 8，这种情况就会上浮：

```
           1
         /   \
        2     7
       / \   / \
      3   4 8   9       需要删除8
     / \
    5   6
    
           1
         /   \
        2     7
       / \   / \
      3   4 6   9       和末尾元素交换，8 <==> 6
     / \
    5   8
    
           1
         /   \
        2     7
       / \   / \
      3   4 6   9       删除末尾元素
     /
    5

           1
         /   \
        2     6
       / \   / \
      3   4 7   9       上浮，交换 6 <==> 7
     /
    5
```

一般情况下，删除元素时，

1. 删除元素的是和末尾元素位于同一侧的子树，肯定会执行下沉操作；
2. 删除元素的是和末尾元素位于不同侧的子树，有可能会执行上浮操作，也可能是执行下沉操作；

因为同侧子树，祖先节点肯定比末尾节点要小，所以交换后肯定是执行下沉操作的。而不同侧的子树，交换后是有可能祖先节点比末尾节点大的，所以有可能执行上浮操作（注意是可能）。

另外，为什么这里的 `removeAt` 需要返回值？而且还是只返回执行了上浮 `siftUp` 的值？有什么特殊的意义嘛？这个原因到后面迭代器那里再说~~

### 2.5 迭代器

首先明确一点，优先队列的迭代是按照底层数组顺序遍历的，也就是说，迭代遍历出来的顺序，并不是有序的，这点需要明确。

```java
private final class Itr implements Iterator<E> {

    public E next() {
        if (expectedModCount != modCount)
            throw new ConcurrentModificationException();
        if (cursor < size)
            return (E) queue[lastRet = cursor++]; // 按照数组顺序遍历所有元素
        if (forgetMeNot != null) {
            lastRet = -1;
            lastRetElt = forgetMeNot.poll();
            if (lastRetElt != null)
                return lastRetElt;
        }
        throw new NoSuchElementException();
    }
}
```

其实优先队列的迭代器，如果只是简单遍历访问的话，倒也没什么问题。

关键是迭代时，是可以删除元素的，而优先队列的元素是有序的，如果在迭代期间移除元素，就会引起堆的数据结构发生变化，那后面还能正常迭代下去嘛？或者说是怎么实现，数据结构变化后依旧可以正常遍历？

首先确认一下，迭代删除时，是否会影响后续的遍历？

直接举个栗子吧，比如当前访问的元素是 8，下一个迭代元素是 9（`next()`）：

```
           1
         /   \
        2     7
       / \   / \
      3   4 8   9       要删除8
     / \
    5   6
```

假如此时删除当前元素 8，那么堆结构就会变成：

```
           1
         /   \
        2     6
       / \   / \
      3   4 7   9       6还没遍历到，但是已经被移到前面遍历过的位置了
     /
    5
```

那么问题就来了，6 还能不能遍历到呢？毕竟下一个待遍历的元素是 7，迭代器是不可能回到前面再遍历 6 的，所以 6 已经无法被遍历到了，那要怎么处理这种情况呢？

为了解决这个问题，优先队列 `PriorityQueue` 的迭代器里就加上一个成员变量 `forgetMeNot`，用于保存这种发生移动后，正常迭代遍历无法访问到的元素：

```java
private final class Itr implements Iterator<E> {

    /**
     * A queue of elements that were moved from the unvisited portion of
     * the heap into the visited portion as a result of "unlucky" element
     * removals during the iteration.  (Unlucky element removals are those
     * that require a siftup instead of a siftdown.)  We must visit all of
     * the elements in this list to complete the iteration.  We do this
     * after we've completed the "normal" iteration.
     *
     * We expect that most iterations, even those involving removals,
     * will not need to store elements in this field.
     */
    private ArrayDeque<E> forgetMeNot = null;

    public void remove() {
        if (expectedModCount != modCount)
            throw new ConcurrentModificationException();
        if (lastRet != -1) {
            E moved = PriorityQueue.this.removeAt(lastRet);
            lastRet = -1;
            if (moved == null)
                cursor--;
            else {
                if (forgetMeNot == null)
                    forgetMeNot = new ArrayDeque<>();
                forgetMeNot.add(moved);
            }
        } else if (lastRetElt != null) {
            PriorityQueue.this.removeEq(lastRetElt);
            lastRetElt = null;
        } else {
            throw new IllegalStateException();
        }
        expectedModCount = modCount;
    }
}
```

实际上，只有发生上浮 `siftUp` 时，才会导致元素无法被遍历到，因此前面的 `removeAt` 方法只返回了上浮 `siftUp` 情况下的移动节点，其实就是专门为这个迭代器准备的。

如果 `removeAt` 的返回值不为 `null`，就说明发生了上浮 `siftUp`，那么就需要添加到 `forgetMeNot`。

另外，`forgetMeNot` 中的元素，需要等到正常的迭代遍历走完以后，才会遍历到素（看上面`next()`的代码）。

也就是说，删除元素后，实际上会影响到遍历的顺序。

举个栗子：

```
           1
         /   \
        2     7
       / \   / \
      3   4 8   9       删除8
     / \
    5   6

           1
         /   \
        2     6
       / \   / \
      3   4 7   9       删除8之后，此时再删除9
     /
    5

           1
         /   \
        2     5
       / \   / \
      3   4 7   6       删除9之后的结构
```

删除2次之后，`forgetMeNot` 里面应该包含2个元素 `[6, 5]`，这个时候迭代顺序就发生变化了：

```
// 正常的遍历顺序
1 2 7 3 4 8 9 5 6

// 删除后的遍历顺序
1 2 7 3 4 8 9 6 5
```

可以看到，删除后可能会影响实际遍历的顺序。

其实我有一点不明白的是，删除时删除元素交换的始终都是末尾元素，它本来就是最后遍历的，而 `forgetMeNot` 保存的就是移动的元素，实际上也就是末尾元素，所以 `forgetMeNot` 是倒着放入末尾元素的，为什么不是用 `Stack` ，而是用队列 `ArrayDeque` 呢？

这点还没有搞明白，还是说反正都是遍历，顺序什么的都不是啥大问题？？