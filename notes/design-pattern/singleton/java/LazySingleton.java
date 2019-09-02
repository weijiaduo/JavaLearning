package com.pattern.singleton;

/**
 * 懒汉单例模式
 */
public class LazySingleton {

    // 保证在所有线程中保持同步
    private static volatile LazySingleton instance = null;

    // 避免在外部被实例化
    private LazySingleton(){}

    /**
     * 同步获取单例
     * @return
     */
    public static synchronized LazySingleton getInstance() {
        // 需要在判断之前同步
        if (instance == null) {
            instance = new LazySingleton();
        }

        return instance;
    }

}
