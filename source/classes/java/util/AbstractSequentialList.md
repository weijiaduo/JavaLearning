# AbstractSequentialList

## 一、定义

```java
public abstract class AbstractSequentialList<E> extends AbstractList<E> {
    ...
}
```

## 二、方法

虽然 `AbstractSequentialList` 继承自 `AbstractList`，但是实际上只是做了一个模板，用于实现链表型列表而已。

```java
public E get(int index) {
    try {
        return listIterator(index).next();
    } catch (NoSuchElementException exc) {
        throw new IndexOutOfBoundsException("Index: "+index);
    }
}

public E set(int index, E element) {
    try {
        ListIterator<E> e = listIterator(index);
        E oldVal = e.next();
        e.set(element);
        return oldVal;
    } catch (NoSuchElementException exc) {
        throw new IndexOutOfBoundsException("Index: "+index);
    }
}

public void add(int index, E element) {
    try {
        listIterator(index).add(element);
    } catch (NoSuchElementException exc) {
        throw new IndexOutOfBoundsException("Index: "+index);
    }
}

public E remove(int index) {
    try {
        ListIterator<E> e = listIterator(index);
        E outCast = e.next();
        e.remove();
        return outCast;
    } catch (NoSuchElementException exc) {
        throw new IndexOutOfBoundsException("Index: "+index);
    }
}
```

可以看到，实际上只是实现了模板代码，并没有实现具体的代码，这样也只是为了实现 `LinkedList` 这些链表型列表做的准备而已。

对于实现随机访问元素而言，使用 `AbstractList` 会更好一些；

只有当需要实现链表型列表时，才需要使用 `AbstractSequentialList` 来作为模板。