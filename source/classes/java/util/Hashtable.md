# Hashtable

## 一、定义

```java
public class Hashtable<K,V>
    extends Dictionary<K,V>
    implements Map<K,V>, Cloneable, java.io.Serializable {
}
```

`Hashtable` 继承了旧的父类 `Dictionary`，和新的接口 `Map`。

## 二、实现

### 2.1 数据结构

`HashTable` 底层是采用“数组 + 链表”的结构实现的：

```java
private transient Entry<?,?>[] table;

private transient int count;

private static class Entry<K,V> implements Map.Entry<K,V> {
    final int hash;
    final K key;
    V value;
    Entry<K,V> next;
}
```

通过 `key` 的 `hash` 映射到数组table上，找到位置后，再将不同的 `key` 链接到链表上：

```
数组（4）
 ___      ___      ___ 
| 0 | -> | 4 | -> | 8 |      --链表
 ___      ___ 
| 1 | -> | 5 |               --链表
 ___ 
| 2 |                        --链表
 ___      ___      ____ 
| 3 | -> | 7 | -> | 11 |     --链表
```

### 2.2 集合操作

**1）添加元素**

知道了数据结构，实际上就很容易理解怎么添加一个元素了：

```java
private void addEntry(int hash, K key, V value, int index) {
    modCount++;

    Entry<?,?> tab[] = table;
    if (count >= threshold) {
        // 扩容
        rehash();

        tab = table;
        hash = key.hashCode();
        index = (hash & 0x7FFFFFFF) % tab.length;
    }

    // 添加新元素
    @SuppressWarnings("unchecked")
    Entry<K,V> e = (Entry<K,V>) tab[index];
    tab[index] = new Entry<>(hash, key, value, e);
    count++;
}
```

扩容代码那里暂时忽略，后面再解释，直接看后面几行代码：

```java
Entry<K,V> e = (Entry<K,V>) tab[index];
tab[index] = new Entry<>(hash, key, value, e);
```

举个例子说明这2行代码的意义，假设当前数据结构如下：

```
数组（4）
 ___ 
| 4 |
 ___ 
|   |
 ___ 
|   |
 ___
| 3 |
```

这个时候添加 0，此时 0 还不在集合里，`0 % 4 = 0`，所以应该要放入 `table[0]` 中，但是此时 `table[0]` 已经有 4 了，所以就需要追加到链表上。

那么按照上面的2行代码，最终结果如下：

```
数组（4）
 ___      ___ 
| 0 | -> | 4 |
 ___ 
|   |
 ___ 
|   |
 ___
| 3 |
```

注意，添加元素时，是插入到链表头部的，而不是链表尾部。

至于为什么倒着插入，我想应该是为了提高效率，毕竟如果每次都插到尾部的话，就得遍历一次链表，这样的代价有点高~~

**2）删除元素**

```java
public synchronized V remove(Object key) {
    Entry<?,?> tab[] = table;
    int hash = key.hashCode();
    int index = (hash & 0x7FFFFFFF) % tab.length;
    @SuppressWarnings("unchecked")
    Entry<K,V> e = (Entry<K,V>)tab[index];
    for(Entry<K,V> prev = null ; e != null ; prev = e, e = e.next) {
        if ((e.hash == hash) && e.key.equals(key)) {
            modCount++;
            if (prev != null) {
                prev.next = e.next;
            } else {
                // 删除数组元素
                tab[index] = e.next;
            }
            count--;
            V oldValue = e.value;
            e.value = null;
            return oldValue;
        }
    }
    return null;
}
```

删除没什么好说的，直接删就是了，唯一需要注意的一点就是，如果删除的是数组上的节点，就需要更新一下它的值：

举个栗子：

```
数组（4）
 ___      ___ 
| 0 | -> | 4 |
 ___ 
|   |
 ___ 
|   |
 ___
| 3 |
```

比如删除 0，就需要把 4 设置到数组里：

```
数组（4）
 ___ 
| 4 |
 ___ 
|   |
 ___ 
|   |
 ___
| 3 |
```

### 2.2 扩容机制

`HashTable` 是采用“数组 + 链表”的结构实现的，理论上链表可以无限长。

但是如果链表太长了，就会导致查询变得很慢，这个要怎么解决呢？这个时候就需要扩容了，把过长的链表拆分，变成短链表。那要怎么拆分？

很简单，数据是通过“数组 + 链表”保存的，在维持元素数量不变的情况下，想要链表变短，那就只能增长数组长度。

其实，`HashTable` 的扩容规则就是这样的，通过增长数组容量，以重新调整链表的结构，缩短链表的长度。

**1）负载因子**

那还有一个问题，就是，多长的链表才算是太长？或者说，什么时候才需要重新调整数组容量？

在 `HashTable` 中，使用了负载因子来表示需要重构数组的时机：

```java
// 负载因子
private float loadFactor;
```

为了了解负载因子的作用，先解释一下负载的意义。

举个例子说明，比如当前结构如下：

```
数组（4）
 ___      ___ 
| 0 | -> | 4 |
 ___ 
| 1 |
 ___ 
|   |
 ___      ___      ____ 
| 3 | -> | 7 | -> | 11 |
```

那么，此时的数组容量是 4，元素数量是 6，因此实际负载就是 `6 / 4 = 1.5`。

也就是说，负载其实表示的是当前数据结构的负载情况，数值越高，表示保存的数据越多。

因此，负载因子相当于一个阈值，当负载超过这个阈值时，就表示当前数据已经很满了，需要对数组进行扩容了。

再举个例子，假设当前 `HashTable` 的负载因子是 0.8，此时的数据结构如下：

```
数组（4）
 ___      ___ 
| 0 | -> | 4 |
 ___ 
|   |
 ___ 
|   |
 ___
| 3 |
```

此时的负载是 `4 / 3 = 0.75 < 0.8`，暂时不需要扩容。

如果我再添加一个元素 1 之后：

```
数组（4）
 ___      ___ 
| 0 | -> | 4 |
 ___ 
| 1 |
 ___ 
|   |
 ___
| 3 |
```

此时的负载是 `4 / 4 = 1 > 0.8`，这个时候就需要扩容了。

还有，`HashTable` 中为了避免经常计算负载，就添加了另一个变量阈值，来维护需要扩容的数量阈值：

```
// 阈值
private int threshold;
```

它实际就是由数组容量和负载因子算出来的：

```java
threshold = table.length * loadFactor
```

当元素数量大于这个阈值时，就需要扩容：

```java
if (count >= threshold) {
    // 扩容
}
```

但实际上，阈值 `threshold` 并不是一个必要的变量，只是为了提高性能，减少负载的计算（除法计算），而添加的。

**2）扩容规则**

好了，既然知道了扩容的时机，那就说一下扩容的规则吧，究竟要扩容多少才好呢？

`HashTable` 和其他集合类差不多，具体如下：

```java
int newCapacity = (oldCapacity << 1) + 1;
if (newCapacity - MAX_ARRAY_SIZE > 0) {
    if (oldCapacity == MAX_ARRAY_SIZE)
        // Keep running with MAX_ARRAY_SIZE buckets
        return;
    newCapacity = MAX_ARRAY_SIZE;
}
```

除去溢出判断，一般情况下其实就是 `2倍 + 1`：

```
新数组容量 = 旧数组容量 * 2 + 1
```

**3）扩容实现**

先上代码，后面再解释：

```java
protected void rehash() {
    int oldCapacity = table.length;
    Entry<?,?>[] oldMap = table;

    // 计算扩容后新的数组容量
    int newCapacity = (oldCapacity << 1) + 1;
    if (newCapacity - MAX_ARRAY_SIZE > 0) {
        if (oldCapacity == MAX_ARRAY_SIZE)
            // Keep running with MAX_ARRAY_SIZE buckets
            return;
        newCapacity = MAX_ARRAY_SIZE;
    }
    Entry<?,?>[] newMap = new Entry<?,?>[newCapacity];

    modCount++;
    // 计算新的阈值
    threshold = (int)Math.min(newCapacity * loadFactor, MAX_ARRAY_SIZE + 1);
    table = newMap;

    for (int i = oldCapacity ; i-- > 0 ;) {
        for (Entry<K,V> old = (Entry<K,V>)oldMap[i] ; old != null ; ) {
            Entry<K,V> e = old;
            old = old.next;
            
            // 重新分配数组的链表
            int index = (e.hash & 0x7FFFFFFF) % newCapacity;
            e.next = (Entry<K,V>)newMap[index];
            newMap[index] = e;
        }
    }
}
```

直接举例子说明，假设当前 `HashTable` 的负载因子是 0.75，阈值 `threshold = 4 * 0.75 = 3`，此时的数据结构如下：

```
数组（4）
 ___      ___ 
| 0 | -> | 4 |
 ___ 
|   |
 ___ 
|   |
 ___
| 3 |
```

此时元素数量是 `3 >= threshold`，表示要扩容了，但是暂时还不需要扩容，因为实际上的扩容，是在下一次添加元素的时候。

然后这个时候再添加一个元素 1，添加前就需要扩容：

```
新数组容量 = 4 * 2 + 1 = 9
```

好了，现在就开始重新分配数据了：

```
旧数组（4）              新数组（9）
 ___      ___             ___ 
| 0 | -> | 4 |           |   |
 ___                      ___ 
|   |                    |   |
 ___                      ___ 
|   |                    |   |
 ___                      ___ 
| 3 |                    |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
```

首先是 3（为什么是3？仔细看看上面的代码，遍历是从 `table.length - 1` 倒着开始的），`3 % 9 = 3`，所以它的新位置就在新数组索引 3 那里：

```
旧数组（4）              新数组（9）
 ___      ___             ___ 
| 0 | -> | 4 |           |   |
 ___                      ___ 
|   |                    |   |
 ___                      ___ 
|   |                    |   |
 ___                      ___ 
| 3 |                    | 3 |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
```

接着是 0，`0 % 9 = 0`，所以它的新位置就在新数组索引 0 那里：

```
旧数组（4）              新数组（9）
 ___      ___             ___ 
| 0 | -> | 4 |           | 0 |
 ___                      ___ 
|   |                    |   |
 ___                      ___ 
|   |                    |   |
 ___                      ___ 
| 3 |                    | 3 |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
```

最后是 4，`4 % 9 = 4`，所以它的新位置就在新数组索引 4 那里：

```
旧数组（4）              新数组（9）
 ___      ___             ___ 
| 0 | -> | 4 |           | 0 |
 ___                      ___ 
|   |                    |   |
 ___                      ___ 
|   |                    |   |
 ___                      ___ 
| 3 |                    | 3 |
                          ___ 
                         | 4 |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
                          ___ 
                         |   |
```

至此，扩容重新分配就完成了。其实可以看到，链表 0 被拆分了，就是通过这种扩容方式，来减短链表的长度，提高查询效率。

哎呀，差点忘了，还要把新增元素1加进去：

```
数组（9）
 ___ 
| 0 |
 ___ 
| 1 |
 ___ 
|   |
 ___ 
| 3 |
 ___ 
| 4 |
 ___ 
|   |
 ___ 
|   |
 ___ 
|   |
 ___ 
|   |
```

这样才算是圆满结束了~~

等等！！其实还有个比较有趣的地方，那就是分配后的链表元素顺序，怎么说？

还是直接举例子吧，下面这种结构，重新分配后是怎么样的呢：

```
数组（4）
 ___      ___      ____ 
| 0 | -> | 4 | -> | 12 |
 ___ 
|   |
 ___ 
|   |
 ___
|   |
```

直接给结果吧：

```
数组（9）
 ____      ___ 
| 12 | -> | 0 |
 ___ 
|   |
 ___ 
|   |
 ___ 
|   |
 ___ 
| 4 |
 ___ 
|   |
 ___ 
|   |
 ___ 
|   |
 ___ 
|   |
```

咦？0 和 12 的顺序变了！是的，没错，实际上确实是这样，仔细看看重新分配的代码：

```java
int index = (e.hash & 0x7FFFFFFF) % newCapacity;
e.next = (Entry<K,V>)newMap[index];
newMap[index] = e;
```

实际上重新分配时，和添加元素时一样，元素是倒着插入的，都是为了提高效率。