## 顺风面试

#### 设计模式有哪些
策略模式，代理模式，单例模式，多例模式，工厂方法模式，抽象工厂模式，门面模式，适配器模式，模板方法模式，建造者模式，桥梁模式，命令模式，装饰模式，迭代器模式，组合模式，观察者模式，责任链模式，访问者模式，状态模式，原型模式，中介者模式，解释器模式，亨元模式，备忘录模式

1）适配器模式

将一个类的接口转换成客户希望的另外一个接口。Adapter模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。分为类的适配器模式和对象的适配器模式；

2）单例模式

因程序需要，有时我们只需要某个类同时保留一个对象，不希望有更多对象。

3）工厂模式

（1）简单工厂模式是由一个具体的类去创建其他类的实例，父类是相同的，父类是具体的。

（2）工厂方法模式是有一个抽象的父类定义公共接口，子类负责生成具体的对象，这样做的目的是将类的实例化操作延迟到子类中完成。

（3）抽象工厂模式提供一个创建一系列相关或相互依赖对象的接口，而无须指定他们具体的类。

4）装饰模式

又名包装(Wrapper)模式，装饰模式以对客户端透明的方式扩展对象的功能，是继承关系的一个替代方案。装饰模式可以在不创造更多的子类的模式下，将对象的功能加以扩展。

#### 单例模式有几种实现方式

1) 饿汉方法：类加载时就实例化单例，但是缺点是一旦类被加载，单例就会初始化，没有实现懒加载；

```
public class Singleton {
    private static final Singleton INSTANCE = new Singleton();  

    // 私有化构造函数  
    private Singleton(){}  

    public static Singleton getInstance(){  
        return INSTANCE;  
    }  
}
```

2) 懒汉方法：第一次获取单例时实例化，多线程时需要加锁；

```
public class Singleton {
    private static Singleton INSTANCE;
    private Singleton (){}

    // 加锁
    public synchronized static Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }
}
```

3) 双重校验锁方法：同时使用volatile和synchronized，避免懒汉模式对整个方法进行加锁的问题；

```
public class Singleton {
    private volatile static Singleton INSTANCE;
    private Singleton (){}

    public static Singleton getInstance() {
      if (INSTANCE == null) { // 第一次检查  
          synchronized (Singleton.class) {  
              if (INSTANCE == null) { // 第二次检查  
                  INSTANCE = new Singleton();  
              }  
          }  
      }
      return INSTANCE;
    }
}
```

4) 静态内部类方法：使用静态内部类加载时来实例化单例，既可以实现懒加载，也能保证线程安全；

```
public class Singleton {
    /**
     * 类级的内部类，也就是静态的成员式内部类，
     * 该内部类的实例与外部类的实例没有绑定关系，
     * 而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder{
        private static final Singleton instance = new Singleton();
    }

    private Singleton(){}

    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }
}
```

5) 枚举方法：使用枚举类型的机制来保证单例的安全性。

```
public enum Singleton{
    INSTANCE;
}
```
