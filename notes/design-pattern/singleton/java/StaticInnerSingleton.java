package com.pattern.singleton;

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
