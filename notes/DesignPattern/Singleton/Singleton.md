## 单例模式

单例（Singleton）模式的定义：指一个类只有一个实例，且该类能自行创建这个实例的一种模式。单例模式有 3 个特点：

1. 单例类只有一个实例对象；
2. 该单例对象必须由单例类自行创建；
3. 单例类对外提供一个访问该单例的全局访问点；

---

### 一、实现原理

通常，普通类的构造函数是公有的，外部类可以通过“new 构造函数()”来生成多个实例。但是，如果将类的构造函数设为私有的，外部类就无法调用该构造函数，也就无法生成多个实例。这时该类自身必须定义一个静态私有实例，并向外提供一个静态的公有函数用于创建或获取该静态私有实例。

---

### 二、实现方式

#### 2.1 饿汉模式

第一次获取单例时实例化，多线程时需要加锁。

> 注意：如果编写的是多线程程序，则不要删除代码中的关键字 volatile 和 synchronized，否则将存在线程非安全的问题。这样做虽然能够保证线程安全，但是每次访问时都要同步，会影响性能，且消耗更多的资源，这是懒汉式单例的缺点。

```
/**
 * 懒汉单例模式
 */
public class LazySingleton {

    // 保证在所有线程中保持同步
    private static volatile LazySingleton instance = null;

    // 避免在外部被实例化
    private LazySingleton(){}

    /**
     * 同步方法获取单例
     * @return 单例对象
     */
    public static synchronized LazySingleton getInstance() {
        // 需要在判断之前同步
        if (instance == null) {
            instance = new LazySingleton();
        }

        return instance;
    }

}
```

#### 2.2 饿汉模式

类加载时就实例化单例，但是缺点是一旦类被加载，单例就会初始化，没有实现懒加载。

```
/**
 * 饿汉单例模式
 */
public class HungrySingleton {

    // 类加载的时候初始化，因此不需要同步
    private static final HungrySingleton instance = new HungrySingleton();

    // 避免在外部被实例化
    private HungrySingleton(){}

    /**
     * 直接获取单例
     * @return 单例对象
     */
    public static HungrySingleton getInstace() {
        return instance;
    }

}
```

#### 2.3 双重校验锁方法

同时使用volatile和synchronized，避免懒汉模式对整个方法进行加锁的问题。

```
/**
 * 双重校验锁单例模式
 */
public class DoubleCheckLockSingleton {

    // 保证在所有线程中保持同步
    private static volatile DoubleCheckLockSingleton instance = null;

    // 避免在外部被实例化
    private DoubleCheckLockSingleton(){}

    /**
     * 检查两次，一次不加锁，一次加锁
     * @return 单例对象
     */
    public static DoubleCheckLockSingleton getInstance(){
        // 第一次检查，不加锁
        if (instance == null) {
            synchronized (DoubleCheckLockSingleton.class) {
                // 第二次检查，加锁
                if (instance == null) {
                    instance = new DoubleCheckLockSingleton();
                }
            }
        }
        return instance;
    }

}
```

#### 2.4 静态内部类模式

使用静态内部类加载时来实例化单例，既可以实现懒加载，也能保证线程安全。

```
/**
 * 静态内部类单例模式
 */
public class StaticInnerSingleton {

    // 避免在外部被实例化
    private StaticInnerSingleton(){}

    /**
     * 类级的内部类，也就是静态的成员式内部类，
     * 该内部类的实例与外部类的实例没有绑定关系，
     * 而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        private static StaticInnerSingleton instance = new StaticInnerSingleton();
    }

    /**
     * 直接获取单例
     * @return 单例对象
     */
    public static StaticInnerSingleton getInstance() {
        return SingletonHolder.instance;
    }

}
```

#### 2.5 枚举模式

使用枚举类型的机制来保证单例的安全性。

```
/**
 * 枚举模式单例
 */
public enum EnumSingleton {
    INSTANCE;
}
```
