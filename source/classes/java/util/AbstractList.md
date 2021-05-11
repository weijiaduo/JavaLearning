# AbstractList

## 一、定义

`AbstractList` 是一个抽象类，是一个 `List`。
```java
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {
    ...
}
```

## 二、属性

在 `AbstractList` 中，有一个特殊的属性 `modCount`，用于统计列表被修改的次数（例如删除、添加等）。

```java
protected transient int modCount = 0;
```

`modCount` 属性的作用是为了在并发修改列表时，能够快速失败（fail-fast）并抛出并发异常。例如多个线程同时修改列表时，就有可能对正在访问列表元素的线程造成影响，`modCount` 属性可用于判断当前列表是否是异常状态。

## 二、方法

一般的实现方法没什么特别的，只要了解其中一些有趣的实现。

### 2.1 indexOf 和 lastIndexOf

从 `indexOf` 的实现代码可以看出，`List` 是允许放置 `null` 元素的。

```java
public int indexOf(Object o) {
    ListIterator<E> it = listIterator();
    if (o==null) {
        // 列表中的元素有可能是null
        while (it.hasNext())
            if (it.next()==null)
                return it.previousIndex();
    } else {
        while (it.hasNext())
            if (o.equals(it.next()))
                return it.previousIndex();
    }
    return -1;
}
```

根据 `indexOf()` 和 `lastIndexOf()` 的实现效果，有不同的返回值：

- 使用 `indexOf()` 方法查询 `null` 时，会返回列表中第一个出现的 `null` 元素
- 使用 `lastIndexOf()` 方法查询 `null` 时，会返回列表中最后一个出现的 `null` 元素

### 2.2 hashCode

对于一个列表集合而言，它的 `hashCode` 是根据列表集合中的所有元素计算得到的：

```java
public int hashCode() {
    int hashCode = 1;
    for (E e : this)
        hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
    return hashCode;
}
```

也就是说，对于每个空列表对象（也就是 `size()` 等于 0），它的 `hashCode` 值始终是 1。

另外，即使两个列表包含的元素一样，但只要元素的排列顺序不同，它的 `hashCode` 也是不同的。

### 2.3 Itr 和 ListItr

#### 2.3.1 定义

`Itr` 和 `ListItr` 是迭代器的两个实现。

`Itr` 是普通的迭代器实现，可以单向访问列表元素;`ListItr` 是列表专门的迭代器实现，可以用于双向访问列表元素。

```java
private class Itr implements Iterator<E> {
    ...
}
```

`ListItr` 本质上是在 `Itr` 的基础上进行了扩展，使得可以双向访问集合元素。

```java
private class ListItr extends Itr implements ListIterator<E> {

    public boolean hasPrevious() {
        ...
    }

    public E previous() {
        ...
    }

    public int nextIndex() {
        ...
    }

    public int previousIndex() {
        ...
    }
}
```

#### 2.3.2 属性 expectedModCount

在 `Itr` 和 `ListItr` 两个迭代器中，有一个特殊的属性 `expectedModCount`，可以用于检测列表并发修改。

在前面说过，`AbstractList` 中有一个属性 `modCount` 用于记录列表的修改次数。而 `expectedModCount` 则是记录列表在正常情况下的修改次数，当 `modCount ！= expectedModCount` 时，就认为列表发生了并发修改。

`expectedModCount` 的值是在迭代器初始化时初始化的，初始值就是列表此时的修改次数 `modCount`。

```java
int expectedModCount = modCount;
```

在使用迭代器访问元素时，都会先检测列表是否发生了并发修改，如果发生了并发修改，迭代器则会抛出异常。

```java
public E next() {
    // 迭代获取元素之前，都会验证列表是否发生了并发修改
    checkForComodification();
    ...
}

final void checkForComodification() {
    if (modCount != expectedModCount)
        throw new ConcurrentModificationException();
}
```

`expectedModCount` 的值并不是一成不变的，因为迭代器可以进行 `remove` 等修改列表的操作，因此在迭代中使用了这些方法后，还需要再次同步列表的 `modCount`。

```java
public void remove() {
    if (lastRet < 0)
        throw new IllegalStateException();
    checkForComodification();

    try {
        AbstractList.this.remove(lastRet);
        if (lastRet < cursor)
            cursor--;
        lastRet = -1;
        // 在迭代中修改后，同步列表中的修改次数，防止抛出并发异常
        expectedModCount = modCount;
    } catch (IndexOutOfBoundsException e) {
        throw new ConcurrentModificationException();
    }
}
```

### 2.4 SubList 和 RandomAccessSubList

#### 2.4.1 SubList

`SubList` 是 `AbstractList` 的内部子类，用于返回 `AbstractList` 的子列表。

```java
class SubList<E> extends AbstractList<E> {
    // 子列表的原始列表
    private final AbstractList<E> l;
    
    // 子列表在原始列表的偏移量
    private final int offset;
    
    // 子列表的长度
    private int size;
    
    SubList(AbstractList<E> list, int fromIndex, int toIndex) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > list.size())
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                                               ") > toIndex(" + toIndex + ")");
        l = list;
        offset = fromIndex;
        size = toIndex - fromIndex;
        this.modCount = l.modCount;
    }
}
```

从代码可以直接看出，子列表 `SubList` 实际上只是一个代理，它实际返回的还是原始列表的元素。

```java
public E get(int index) {
    rangeCheck(index);
    checkForComodification();
    return l.get(index+offset);
}
```
另外，由于子列表是共用原始列表的元素，因此还需要考虑并发的问题，和迭代器同样地，是通过检测列表的修改次数来检测的。因为 `SubList`继承自 `AbstractList`，所以在子列表中，和 `expectedModCount` 相同功能的属性就是 `modCount`。

```java
private void checkForComodification() {
    if (this.modCount != l.modCount)
        throw new ConcurrentModificationException();
}
```

#### 2.4.2 RandomAccessSubList

`RandomAccessSubList` 也是一个子列表，继承自 `SubList`，它的含义是可以随机访问元素的子列表。

```java
class RandomAccessSubList<E> extends SubList<E> implements RandomAccess {
    RandomAccessSubList(AbstractList<E> list, int fromIndex, int toIndex) {
        super(list, fromIndex, toIndex);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return new RandomAccessSubList<>(this, fromIndex, toIndex);
    }
}
```

但是实际上，由于 `SubList` 已经实现了随机访问的功能，因此 `RandomAccessSubList` 并没有再进行任何其他的扩展，只是单纯继承了 `SubList`，并实现了 `RandomAccess` 接口而已。