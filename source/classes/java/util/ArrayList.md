# ArrayList

## 一、定义

```java
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    ...    
}
```
有个地方没搞明白，按理说抽象类 `AbstractList` 已经包含了接口 `List`，为何还需要再次实现这个接口呢？

难道是担心抽象类 `AbstractList` 有可能会修改，不再实现接口 `List`？

## 二、实现原理

`ArrayList` 底层实现是用数组 `elementData` 来保存集合元素的，并且数组的类型是 `Object`：

```java
// 没有设为private的原因是为了让内部类可以访问该属性
transient Object[] elementData;
```

### 2.1 最大容量

`ArrayList` 内置数组的最大容量是：

```java
/**
 * The maximum size of array to allocate. Some VMs reserve some header words in an array. Attempts to allocate larger arrays may result in OutOfMemoryError: Requested array size exceeds VM limit
 */
MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
```

为什么需要 `-8` 呢？按照源码的备注来看，是因为有些 JVM 在数组的实现上，会保留一些字节作为数组头，用于标记数组的相关信息。可能是“长度”“类型”之类的信息（具体我也没了解）。

### 2.2 扩容机制

虽然底层实现是数组，但是集合列表 `ArrayList` 是可扩容的，它的扩容方法是：

```java
private void grow(int minCapacity) {
    // 首先在当前基础上是增长1/2容量
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    
    // 判断增长1/2后是否满足容量要求
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    
    // 新容量-数组最大值 > 0，则扩充为大容量数组
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    
    // 复制数组到更大的内存
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

扩容可以分为3步：

1. 正常情况下，每次扩容增长当前数组容量的 1/2。
2. 扩容1/2后仍小于最小容量，则使用最小容量；
3. 若最小容量值大于数组最大值，则使用大数组容量。

举几个例子说明：

例1：假如当前的数组容量是 `oldCapacity = 16`，`minCapacity = 23`，那么按照扩容机制的第1条规则：

```java
newCapacity = 16 + 16 / 2 = 24
```

满足要求，因此最终扩容后的数组容量是 `24`。

例2：假如当前的数组容量是 `oldCapacity = 16`，`minCapacity = 30`，那么按照扩容机制的第2条规则：

```java
// 第1条规则
newCapacity = 16 + 16 / 2 = 24

// 第2条规则
if (newCapacity - 30 < 0) {
    newCapacity = 30
}
```

那么此时按照第2条规则，最终扩容后的数组容量是 `30`。

例3：假如当前的数组容量是 `oldCapacity = 16`，`minCapacity = Integer.MAX_VALUE - 4`，那么按照扩容机制的第3条规则：

```java
minCapacity = Integer.MAX_VALUE - 4

// 第1条规则
newCapacity = 16 + 16 / 2 = 24

// 第2条规则
if (newCapacity - minCapacity < 0) {
    newCapacity = minCapacity
}

// 第3条规则
if (newCapacity - MAX_ARRAY_SIZE > 0) {
    newCapacity = hugeCapacity(minCapacity);
}
```

此时扩充后的数组容量由方法 `hugeCapacity(minCapacity)` 计算，而这个方法的定义如下：

```java
private static int hugeCapacity(int minCapacity) {
    if (minCapacity < 0) // overflow
        throw new OutOfMemoryError();
    return (minCapacity > MAX_ARRAY_SIZE) ?
        Integer.MAX_VALUE :
        MAX_ARRAY_SIZE;
}
```

也就是说，当 `minCapacity` 值介于 `MAX_ARRAY_SIZE` 和 `Integer.MAX_VALUE` 之间时，返回 `Integer.MAX_VALUE`；否则返回默认的数组最大值 `MAX_ARRAY_SIZE`。

虽然这3条规则的代码看起来比较简单，但是实际上它还考虑到了溢出的问题。

### 2.3 集合操作

集合的操作，不外乎增删查改，由于底层是数组实现，因此对于查询和更新，可以直接根据数组的索引来访问即可，很简单（当然，这中间还会进行索引范围的检查，但也不是难事）。

至于添加和删除，这就涉及到了数组的移动。如果插入到数组中间，就需要把插入位置后面的数组元素都往后移动，才能插入；而删除中间的某个元素，也需要把该元素之后的数组元素往前移动。

实际上操作也不复杂，举个插入的例子。首先是插入函数的定义：

```java
public void add(int index, E element) {
    // 检查索引的范围
    rangeCheckForAdd(index);
    
    // 保证数组空间足够，不够就扩容
    ensureCapacityInternal(size + 1);
    
    // 把插入位置后的元素往后移动
    System.arraycopy(elementData, index, elementData, index + 1, size - index);
                     
    // 插入新元素
    elementData[index] = element;
    size++;
}
```

原理比较简单，和平时自己用把数据插入到数组时的操作差不多，只是多了一些索引范围和扩容的校验。

删除的原理也差不多，不过有一个比较有意思的就是，如果同一个对象保存在集合中不止一个位置，那么在删除的时候，只会删除第一个出现的位置：

```java
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                fastRemove(index);
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);
                return true;
            }
    }
    return false;
}
```

可以看到，删除元素时，只要找到第一个就把它删除，至于后面是否还有这个元素，就不再管了。

### 2.4 子列表

子列表本来觉得没什么特别的东西，后来仔细一看，还真发现了一些华点。

**1）子列表构造函数的参数**

子列表构造函数的参数有啥特别的？先上源代码：

```java
SubList(AbstractList<E> parent,
        int offset, int fromIndex, int toIndex) {
    this.parent = parent;
    this.parentOffset = fromIndex;
    this.offset = offset + fromIndex;
    this.size = toIndex - fromIndex;
    this.modCount = ArrayList.this.modCount;
}
```

`fromIndex` 和 `toIndex` 很容易理解，就是子集合的索引范围；`parent` 也很清楚，就是子集合的父集合；

但是 `offset` 是干啥用的，不是已经有 `fromIndex` 和 `toIndex` 了吗？需要这个参数吗？

看到后面才发现，原来是为了嵌套子列表，也就是子列表再获取子列表的情况：

`ArrayList -> SubList -> SubList -> ...`

就类似这种层层嵌套的结构。

子列表的子列表函数如下：

```java
public List<E> subList(int fromIndex, int toIndex) {
    subListRangeCheck(fromIndex, toIndex, size);
    return new SubList(this, offset, fromIndex, toIndex);
}
```

这个时候 `offset` 的作用就体现出来了。

**2）内部类 `SubList` 与 `ArrayList` 的关系**

在源码里可以看到，`SubList` 里经常会访问 `ArrayList` 的变量，主要包括 `modCount` 和 `elementData`。

但是考虑到嵌套子列表的情况，它里面是怎么访问到相当于祖先级别的 `ArrayList` 呢？

貌似还挺简单的，直接使用 `ArrayList.this` 就可以访问到外部类的成员（但是我以前还真不知道这种操作，所以特地记下来，哈哈哈~~）：

```java
ArrayList.this.modCount

ArrayList.this.elementData

ArrayList.this.set(offset + lastRet, e)
```

由于内部类占用了 `this`，为了和外部类区分开来，就加上了外部类的类名前缀 `ArrayList.this`。