# List

## 一、定义

```java
public interface List<E> extends Collection<E> {
    ...
}
```

## 二、增加方法

```List``` 接口继承自 ```Collection``` 接口，但相比于 ```Collection``` 接口还增加了几个方法：

```java
/**
 * 获取指定位置的元素
 */
E get(int index);

/**
 * 设置指定位置的元素
 */
E set(int index, E element);

/**
 * 在指定位置添加新的元素
 */
void add(int index, E element);

/**
 * 移除指定位置的元素
 */
E remove(int index);

/**
 * 指定位置的元素在列表集合中第一次出现的索引（从前往后）
 * 注意入参是 Object 类型（是由于一开始没有考虑到泛型的原因）
 */
int indexOf(Object o);

/**
 * 指定位置的元素在列表集合中最后一次出现的索引（从后往前）
 * 注意入参是 Object 类型（是由于一开始没有考虑到泛型的原因）
 */
int lastIndexOf(Object o);

/**
 * 返回列表集合的遍历迭代器，从第一个元素开始遍历
 */
ListIterator<E> listIterator();

/**
 * 返回列表集合的遍历迭代器，从指定位置开始遍历
 */
ListIterator<E> listIterator(int index);

/**
 * 返回列表集合的子列表
 */
List<E> subList(int fromIndex, int toIndex);

```

### 2.2 版本新增

在 JDK 1.8 版本时，又增加了一些方法：

```java
/**
 * 对列表集合进行排序
 */
default void sort(Comparator<? super E> c);
```
