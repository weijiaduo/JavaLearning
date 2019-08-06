package com.pattern.singleton;

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
