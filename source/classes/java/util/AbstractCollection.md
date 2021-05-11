# AbstractCollection

## 一、定义

```java
public abstract class AbstractCollection<E> implements Collection<E> {
    ...
}
```

## 二、实现

`AbstractCollection` 抽象类实现了接口 `Collection` 的部分方法，一般的方法没什么特别的，而比较有意思的实现有两个：`contains` 和 `toArray`。

### 2.1 contains

首先是 `contains` 方法，它的实现代码如下：

```java
public boolean contains(Object o) {
    Iterator<E> it = iterator();
    if (o==null) {
        while (it.hasNext())
            if (it.next()==null)
                return true;
    } else {
        while (it.hasNext())
            if (o.equals(it.next()))
                return true;
    }
    return false;
}
```

可以看出，集合中的对象可以是 `null`，在判断 `null` 在集合中的位置时，会返回 `null` 第一次出现的位置。

### 2.2 toArray

另一个比较有意思的是 `toArray` 方法，它有两个实现：`toArray()` 和 `toArray(T[] a)`。

先看 `toArray()` 的实现代码：

```java
public Object[] toArray() {
    // 评估数组的大小，但是集合元素有可能变多或者变少
    Object[] r = new Object[size()];
    Iterator<E> it = iterator();
    for (int i = 0; i < r.length; i++) {
        if (! it.hasNext()) // 集合元素比估计的要少
            return Arrays.copyOf(r, i);
        r[i] = it.next();
    }
    // 集合元素可能比估计的要多
    return it.hasNext() ? finishToArray(r, it) : r;
}
```

`toArray()` 在将集合转成数组时，考虑到了集合的数量有可能发生变化。比如说，在 `toArray()` 转成数组的期间，有可能有其他线程对这个集合进行增加元素或者删除元素，导致集合数量发生改变。

`toArray()` 的集合转数组包括了几个步骤：

1. 创建一个和当前集合大小一样大的数组
2. 迭代遍历集合元素，将元素保存到数组中
3. 如果迭代得到的元素小于数组的大小，就截断数组，使得元素刚好填满数组
4. 如果迭代得到的元素大于数组的大小，则扩充数组，以放下所有集合元素

接下来再看一下 `toArray(T[] a)` 的实现代码，实际上和 `toArray()` 差不多，只不过加上了泛型：

```java
public <T> T[] toArray(T[] a) {
    // 评估数组的大小，但是集合元素有可能变多或者变少
    int size = size();
    // 泛型数组
    T[] r = a.length >= size ? a :
              (T[])java.lang.reflect.Array
              .newInstance(a.getClass().getComponentType(), size);
    
    Iterator<E> it = iterator();
    for (int i = 0; i < r.length; i++) {
        if (! it.hasNext()) { // 集合元素比估计的要少
            if (a == r) {
                r[i] = null;  // 使用参数传入的数组时，null 作为数组结尾
            } else if (a.length < i) {
                return Arrays.copyOf(r, i);
            } else {
                System.arraycopy(r, 0, a, 0, i);
                if (a.length > i) {
                    a[i] = null;
                }
            }
            return a;
        }
        r[i] = (T)it.next();
    }
    // 集合元素可能比估计的要多
    return it.hasNext() ? finishToArray(r, it) : r;
}
```

总体思路上，`toArray()` 和 `toArray(T[] a)` 的实现差不多，只是 `toArray(T[] a)` 会根据数组参数进行泛型化，返回的结果不再需要进行类型转换：

```java
// 需要强制类型转换
String[] arr1 = (String[]) collection.toArray();

// 不需要类型转换，但是需要传入具体类型数组
String[] arr2 = collection.toArray(new String[0]);
```